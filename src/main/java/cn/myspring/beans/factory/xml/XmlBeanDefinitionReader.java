package cn.myspring.beans.factory.xml;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.PropertyValue;
import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.beans.factory.config.RuntimeBeanReference;
import cn.myspring.beans.factory.config.TypedStringValue;
import cn.myspring.beans.factory.support.BeanDefinitionRegistry;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.support.GenericBeanDefinition;
import cn.myspring.core.io.Resource;
import cn.myspring.util.ClassUtils;
import cn.myspring.util.StringUtils;
import jdk.internal.util.xml.impl.Input;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 * 持有BeanDefinitionRegistry实例
 * 读取.xml文件利用BeanDefinitionRegistry实例进行注册
 */
public class XmlBeanDefinitionReader {

    protected final Log logger = LogFactory.getLog(getClass());

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String SCOPE_ATTRIBUTE = "scope";

    private static final String PROPERTY_ATTRIBUTE = "property";

    private static final String REF_ATTRIBUTE = "ref";

    private static final String VALUE_ATTRIBUTE = "value";

    private static final String NAME_ATTRIBUTE = "name";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        try {
            //通过类加载器拿到指定文件的字节流
            is = resource.getInputStream();

            //利用dom4j将字节流转换成xml进行遍历
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);

            Element root = document.getRootElement();//<beans>
            Iterator<Element> iterator = root.elementIterator();
            while(iterator.hasNext()) {
                Element element = iterator.next();
                //取<bean>中的id属性值
                String id = element.attributeValue(ID_ATTRIBUTE);
                //取<bean>中的class属性值
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
                if(element.attribute(SCOPE_ATTRIBUTE) != null) {
                    beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }
                parsePropertyElement(element, beanDefinition);
                registry.registerBeanDefinition(id, beanDefinition);//用BeanDefinitionRegistry实例进行注册
            }

        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document failed", e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 遍历beanElement下的<property>,
     * 并将其ref属性引用的实体对象创建出来保存到beanDefinition中的List<PropertyValue>中
     *
     * @param beanElement xml中的<bean>元素
     * @param beanDefinition 用于保存遍历得到的ref实体对象
     */
    public void parsePropertyElement(Element beanElement, BeanDefinition beanDefinition) {
        Iterator iterator = beanElement.elementIterator(PROPERTY_ATTRIBUTE);
        while(iterator.hasNext()) {
            Element propertyElement = (Element) iterator.next();
            String propertyName = propertyElement.attributeValue(NAME_ATTRIBUTE);
            if(!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object value = parsePropertyValue(propertyElement, propertyName);
            PropertyValue propertyValue = new PropertyValue(propertyName, value);
            beanDefinition.getPropertyValues().add(propertyValue);
        }
    }

    /**
     * 解析<property>元素中的ref或者value属性，分别创建其对应的封装对象RuntimeBeanReference或者TypedStringValue
     * 若<property>元素中没有ref或者value属性，抛出RuntimeException
     *
     * @param propertyElement xml中的<property>元素
     * @param propertyName <property>元素的name属性值
     * @return RuntimeBeanReference(ref封装)或者TypedStringValue(value封装)对象
     */
    public Object parsePropertyValue(Element propertyElement, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";

        boolean hasRefAttribute = (propertyElement.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (propertyElement.attribute(VALUE_ATTRIBUTE) != null);

        if(hasRefAttribute) {
            String refName = propertyElement.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)) {
                logger.error(elementName + "contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if(hasValueAttribute) {
            TypedStringValue value = new TypedStringValue(propertyElement.attributeValue(VALUE_ATTRIBUTE));
            return value;
        } else {
            throw new RuntimeException(elementName + "must specify a ref or value");
        }

    }

}
