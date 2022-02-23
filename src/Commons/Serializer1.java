package Commons;

import java.io.*;

public class Serializer1 {
    public static void UnSerializer(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(filename));
        objectInputStream.readObject();
        objectInputStream.close();
    }
    public static void serializer(String filename,Object object) throws IOException {
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(filename));
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }
}
