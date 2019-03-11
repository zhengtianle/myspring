package cn.myspring.test.v2;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.PropertyValue;
import cn.myspring.beans.factory.config.RuntimeBeanReference;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 */
public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("person-v2.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("person");
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues();

        Assert.assertTrue(propertyValueList.size() == 4);

        PropertyValue propertyValue = this.getPropertyValue("nameDao", propertyValueList);
        Assert.assertNotNull(propertyValue);
        Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);

        PropertyValue propertyValue1 = this.getPropertyValue("sexDao", propertyValueList);
        Assert.assertNotNull(propertyValue1);
        Assert.assertTrue(propertyValue1.getValue() instanceof RuntimeBeanReference);
    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> propertyValueList) {
        for(PropertyValue value : propertyValueList) {
            if(value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }


}
