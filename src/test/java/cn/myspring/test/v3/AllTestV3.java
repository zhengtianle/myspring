package cn.myspring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextV3.class,
        BeanDefinitionTestV3.class,
        ConstructorResolverTest.class
})
public class AllTestV3 {
}
