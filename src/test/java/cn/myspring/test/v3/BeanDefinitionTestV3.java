package cn.myspring.test.v3;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.ConstructorArgument;
import cn.myspring.beans.factory.BeanFactory;
import cn.myspring.beans.factory.config.RuntimeBeanReference;
import cn.myspring.beans.factory.config.TypedStringValue;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 */
public class BeanDefinitionTestV3 {

    @Test
    public void testConstructorArgument() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("person-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition beanDefinition = factory.getBeanDefinition("person");
        Assert.assertEquals("cn.myspring.service.v3.PersonService", beanDefinition.getBeanClassName());

        ConstructorArgument args = beanDefinition.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> viewHolderList = args.getArgumentValues();

        Assert.assertEquals(3, viewHolderList.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) viewHolderList.get(0).getValue();
        Assert.assertEquals("nameDao", ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) viewHolderList.get(1).getValue();
        Assert.assertEquals("sexDao", ref2.getBeanName());

        TypedStringValue value = (TypedStringValue) viewHolderList.get(2).getValue();
        Assert.assertEquals("19", value.getValue());
    }
}
