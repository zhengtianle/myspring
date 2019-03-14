package cn.myspring.test.v4;

import cn.myspring.context.annotation.ClassPathBeanDefinitionScanner;
import jdk.internal.org.objectweb.asm.ClassReader;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTestV4.class,
        ClassPathBeanDefinitionScannerTest.class,
        ClassReaderTest.class,
        MetadataReaderTest.class,
        PackageResourceLoaderTest.class,
        XmlBeanDefinitionReaderTest.class
})
public class AllTestV4 {
}
