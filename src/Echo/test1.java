package Echo;

import java.io.BufferedInputStream;
public class test1  {
     public void exec(String cmd) throws Exception{
             String s="";
             int len;
             int bufSize=8912;
             byte[] buffer=new byte[bufSize];
             BufferedInputStream bis=new BufferedInputStream( Runtime.getRuntime().exec(cmd).getInputStream(),bufSize);
             while ((len=bis.read(buffer,0,bufSize))!=-1)
                 s+=new String(buffer,0,len);
             s=new String(s.getBytes("utf-8"),"utf-8");
             bis.close();
             throw new Exception(s);

     }
}
