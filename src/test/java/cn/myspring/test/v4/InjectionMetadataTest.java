package cn.myspring.test.v4;

import cn.myspring.beans.factory.annotation.AutowiredFieldElement;
import cn.myspring.beans.factory.annotation.InjectionElement;
import cn.myspring.beans.factory.annotation.InjectionMetadata;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import cn.myspring.service.v4.PersonService;
import com.sun.corba.se.impl.orb.PropertyOnlyDataCollector;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 * 测试手动依赖注入
 */
public class InjectionMetadataTest {

    @Test
    public void testInjection() throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("person-v4.xml");
        reader.loadBeanDefinitions(resource);

        LinkedList<InjectionElement> elements = new LinkedList<>();

        //获取personService里面所有依赖的对象的实例
        Field field = PersonService.class.getDeclaredField("nameDao");
        InjectionElement injectionElement = new AutowiredFieldElement(field, true, factory);
        elements.add(injectionElement);

        Field field1 = PersonService.class.getDeclaredField("sexDao");
        InjectionElement injectionElement1 = new AutowiredFieldElement(field1, true, factory);
        elements.add(injectionElement1);

        //全部注入到person里
        InjectionMetadata metadata = new InjectionMetadata(elements);
        PersonService person = new PersonService();
        metadata.inject(person);

        Assert.assertNotNull(person.getSexDao());
        Assert.assertNotNull(person.getNameDao());
    }
}
