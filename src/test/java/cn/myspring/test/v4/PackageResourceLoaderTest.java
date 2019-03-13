package cn.myspring.test.v4;

import cn.myspring.core.io.Resource;
import cn.myspring.core.io.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 */
public class PackageResourceLoaderTest {

    /**
     * 期待扫描dao.v4包获得NameDao和SexDao（@Component）
     */
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("cn.myspring.dao.v4");
        Assert.assertEquals(2, resources.length);

    }
}
