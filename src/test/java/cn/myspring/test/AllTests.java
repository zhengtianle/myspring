package cn.myspring.test;

import cn.myspring.test.v1.AllTest;
import cn.myspring.test.v2.AllTestV2;
import cn.myspring.test.v3.AllTestV3;
import cn.myspring.test.v4.AllTestV4;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-11
 * @Author ZhengTianle
 * Description:
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllTest.class,
        AllTestV2.class,
        AllTestV3.class,
        AllTestV4.class
})
public class AllTests {
}
