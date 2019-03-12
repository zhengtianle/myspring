package cn.myspring.test.v3;

import cn.myspring.context.ApplicationContext;
import cn.myspring.context.support.ClassPathXmlApplicationContext;
import cn.myspring.service.v3.PersonService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 * 测试构造器注入
 */
public class ApplicationContextV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("person-v3.xml");
        PersonService person = (PersonService) context.getBean("person");

        Assert.assertNotNull(person.getNameDao());
        Assert.assertNotNull(person.getSexDao());
        Assert.assertEquals(19, person.getAge());
    }
}
