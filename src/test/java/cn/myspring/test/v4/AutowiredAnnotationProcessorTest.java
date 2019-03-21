package cn.myspring.test.v4;

import cn.myspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import cn.myspring.beans.factory.annotation.AutowiredFieldElement;
import cn.myspring.beans.factory.annotation.InjectionElement;
import cn.myspring.beans.factory.annotation.InjectionMetadata;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;
import cn.myspring.service.v4.PersonService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 * 测试根据@Autowired注解自动注入
 */
public class AutowiredAnnotationProcessorTest {

    @Test
    public void testGetInjectionMetadata() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("person-v4.xml");
        reader.loadBeanDefinitions(resource);

        //准备 Autowired注解处理器
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);

        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PersonService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements,"nameDao");
        assertFieldExists(elements,"sexDao");

        PersonService person = new PersonService();
        injectionMetadata.inject(person);

        Assert.assertNotNull(person.getNameDao());
        Assert.assertNotNull(person.getSexDao());
    }

    private void assertFieldExists(List<InjectionElement> elements ,String fieldName){
        for(InjectionElement ele : elements){
            AutowiredFieldElement fieldElement = (AutowiredFieldElement)ele;
            Field field = fieldElement.getField();
            if(field.getName().equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }
}
