package cn.myspring.test.v4;

import cn.myspring.beans.factory.config.DependencyDescriptor;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import cn.myspring.dao.v4.NameDao;
import cn.myspring.service.v4.PersonService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 * 测试依赖描述DependencyDescriptor类
 * 解析依赖对象
 * 通过类中的属性名的class，匹配beanFactory中的bean，得到bean实例
 */
public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("person-v4.xml");
        reader.loadBeanDefinitions(resource);

        Field field = PersonService.class.getDeclaredField("nameDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(field, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof NameDao);
    }
}
