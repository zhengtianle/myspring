package cn.myspring;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.beans.factory.BeanFactory;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import cn.myspring.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    /**
     * 目标：给定文件，获取bean的定义
     */
    @Test
    public void testGetBean(){
        Resource resource = new ClassPathResource("person.xml");
        reader.loadBeanDefinitions(resource);
        BeanDefinition bean = factory.getBeanDefinition("person");

        Assert.assertEquals("cn.myspring.service.PersonService", bean.getBeanClassName());

        PersonService person = (PersonService) factory.getBean("person");
        Assert.assertNotNull(person);
    }

    @Test
    public void testInvalidBean() {
        Resource resource = new ClassPathResource("person.xml");
        reader.loadBeanDefinitions(resource);
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return ;
        }
        Assert.fail("期望抛出 BeanCreationException ");
    }

    @Test
    public void testInvalidXML() {
        try{
            Resource resource = new ClassPathResource("xxx.xml");
            reader.loadBeanDefinitions(resource);
        } catch (BeanDefinitionStoreException e) {
            return ;
        }
        Assert.fail("期望抛出 BeanDefinitionStoreException ");
    }
}
