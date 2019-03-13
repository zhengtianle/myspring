package cn.myspring.test.v4;

import cn.myspring.context.ApplicationContext;
import cn.myspring.context.support.ClassPathXmlApplicationContext;
import cn.myspring.service.v4.PersonService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 */
public class ApplicationContextTest4 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PersonService person = (PersonService)context.getBean("person");

        Assert.assertNotNull(person.getNameDao());
        Assert.assertNotNull(person.getSexDao());

    }
}
