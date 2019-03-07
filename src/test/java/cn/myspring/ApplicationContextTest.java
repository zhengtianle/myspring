package cn.myspring;

import cn.myspring.context.ApplicationContext;
import cn.myspring.context.support.ClassPathXmlApplicationContext;
import cn.myspring.context.support.FileSystemXmlApplicationContext;
import cn.myspring.service.PersonService;
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
        ApplicationContext context = new ClassPathXmlApplicationContext("person.xml");
        PersonService person = (PersonService) context.getBean("person");
        Assert.assertNotNull(person);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext context = new FileSystemXmlApplicationContext("/home/zhengtianle/workspace/idea-workspace/myspring/src/test/resources/person.xml");
        PersonService person = (PersonService) context.getBean("person");
        Assert.assertNotNull(person);
    }
}
