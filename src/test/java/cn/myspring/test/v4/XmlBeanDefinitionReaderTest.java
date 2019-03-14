package cn.myspring.test.v4;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.context.annotation.ScannedGenericBeanDefinition;
import cn.myspring.core.annotation.AnnotationAttributes;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import cn.myspring.core.type.AnnotationMetadata;
import cn.myspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 */
public class XmlBeanDefinitionReaderTest {

    @Test
    public void testParseScanedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("person-v4.xml");
        reader.loadBeanDefinitions(resource);

        String annotation = Component.class.getName();
        {
            BeanDefinition bd = factory.getBeanDefinition("person");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();


            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("person", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("nameDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("sexDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
