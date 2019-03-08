package cn.myspring.beans.factory.xml;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.beans.factory.support.BeanDefinitionRegistry;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.support.GenericBeanDefinition;
import cn.myspring.core.io.Resource;
import cn.myspring.util.ClassUtils;
import jdk.internal.util.xml.impl.Input;
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

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String SCOPE_ATTRIBUTE = "scope";

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

}
