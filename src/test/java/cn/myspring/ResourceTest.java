package cn.myspring;

import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.FileSystemResource;
import cn.myspring.core.io.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public class ResourceTest {

    private Resource resource = null;
    private InputStream inputStream = null;

    @After
    public void tireDown() {
        try{
            inputStream = resource.getInputStream();
            Assert.assertNotNull(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testClassPathResource() {
        resource = new ClassPathResource("person.xml");
    }

    @Test
    public void testSystemPathResource() {
        resource = new FileSystemResource("/home/zhengtianle/workspace/idea-workspace/myspring/src/test/resources/person.xml");
    }

}