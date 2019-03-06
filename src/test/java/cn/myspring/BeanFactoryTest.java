package cn.myspring;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.beans.factory.BeanFactory;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.service.PersonService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public class BeanFactoryTest {

    /**
     * 目标：给定文件，获取bean的定义
     */
    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("person.xml");
        BeanDefinition bean = factory.getBeanDefinition("person");

        Assert.assertEquals("cn.myspring.service.PersonService", bean.getBeanClassName());

        PersonService person = (PersonService) factory.getBean("person");
        Assert.assertNotNull(person);
    }

    @Test
    public void testInvalidBean() {
        BeanFactory factory = new DefaultBeanFactory("person.xml");
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
            new DefaultBeanFactory("xxxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return ;
        }
        Assert.fail("期望抛出 BeanDefinitionStoreException ");
    }
}
