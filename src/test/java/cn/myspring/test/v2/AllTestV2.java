package cn.myspring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextV2.class,
        BeanDefinitionTestV2.class,
        BeanDefinitionValueResolverTest.class
})
public class AllTestV2 {
}
