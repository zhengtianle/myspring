package cn.myspring.test.v2;

import cn.myspring.context.ApplicationContext;
import cn.myspring.context.support.ClassPathXmlApplicationContext;
import cn.myspring.dao.NameDao;
import cn.myspring.dao.SexDao;
import cn.myspring.service.v2.PersonService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 */
public class ApplicationContextV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("person-v2.xml");
        PersonService person = (PersonService) context.getBean("person");

        Assert.assertNotNull(person.getNameDao());
        Assert.assertNotNull(person.getSexDao());

        Assert.assertTrue(person.getSexDao() instanceof SexDao);
        Assert.assertTrue(person.getNameDao() instanceof NameDao);
        Assert.assertEquals("sduwh", person.getSchool());
        //TypeConvert
        Assert.assertEquals(19, person.getAge());
    }
}
