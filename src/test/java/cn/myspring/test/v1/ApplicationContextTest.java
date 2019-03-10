package cn.myspring.test.v1;

import cn.myspring.context.ApplicationContext;
import cn.myspring.context.support.ClassPathXmlApplicationContext;
import cn.myspring.context.support.FileSystemXmlApplicationContext;
import cn.myspring.service.v1.PersonService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("person-v1.xml");
        PersonService person = (PersonService) context.getBean("person");
        Assert.assertNotNull(person);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/test/resources/person-v1.xml");
        PersonService person = (PersonService) context.getBean("person");
        Assert.assertNotNull(person);
    }
}
