package cn.myspring.test.v2;

import cn.myspring.beans.factory.config.RuntimeBeanReference;
import cn.myspring.beans.factory.config.TypedStringValue;
import cn.myspring.beans.factory.support.BeanDefinitionValueResolver;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.dao.NameDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 */
public class BeanDefinitionValueResolverTest {
    DefaultBeanFactory factory;
    XmlBeanDefinitionReader reader;
    BeanDefinitionValueResolver resolver;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("person-v2.xml"));

        resolver = new BeanDefinitionValueResolver(factory);
    }

    @Test
    public void testResolveRuntimeBeanReference() {

        RuntimeBeanReference reference = new RuntimeBeanReference("nameDao");
        Object value = resolver.resolveValueIfNessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof NameDao);
    }

    @Test
    public void testREsolveTypedStringValue() {

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);

    }
}
