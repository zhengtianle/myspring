package cn.myspring.test.v3;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.support.ConstructorResolver;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import cn.myspring.dao.NameDao;
import cn.myspring.service.v3.PersonService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 */
public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("person-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition beanDefinition = factory.getBeanDefinition("person");

        ConstructorResolver resolver = new ConstructorResolver(factory);
        PersonService person = (PersonService) resolver.autowireConstructor(beanDefinition);

        Assert.assertEquals(19, person.getAge());
        Assert.assertNotNull(person.getNameDao());
        Assert.assertNotNull(person.getSexDao());
    }
}
