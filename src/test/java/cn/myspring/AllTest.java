package cn.myspring;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanFactoryTest.class,
        ApplicationContextTest.class,
        ResourceTest.class
})
public class AllTest {
}
