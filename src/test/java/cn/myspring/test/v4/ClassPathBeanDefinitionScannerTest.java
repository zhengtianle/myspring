package cn.myspring.test.v4;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.context.annotation.ClassPathBeanDefinitionScanner;
import cn.myspring.context.annotation.ScannedGenericBeanDefinition;
import cn.myspring.core.annotation.AnnotationAttributes;
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
 * 测试扫描包下的带有@Component注解的类，并生成beanDefinition（读取注解的value属性作为beanName，没有的话则根据类名自动生成）
 * 1. ClassPathBeanDefinitionScanner相当于之前的XmlBeanDefinitionReader
 * 2. ScannedGenericBeanDefinition相当于之前的GenericBeanDefinition
 */
public class ClassPathBeanDefinitionScannerTest {
    @Test
    public void testParseScanedBean(){

        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "cn.myspring.dao.v4,cn.myspring.service.v4";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);


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
