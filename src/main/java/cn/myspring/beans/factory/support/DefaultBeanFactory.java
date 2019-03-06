package cn.myspring.beans.factory.support;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.beans.factory.BeanFactory;
import cn.myspring.util.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try {
            //通过类加载器拿到指定文件的字节流
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            is = classLoader.getResourceAsStream(configFile);

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
                this.beanDefinitionMap.put(id, beanDefinition);
            }

        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document failed", e);
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

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    /**
     * 注意，提供的id所代表的类，需要有无参构造函数
     * @param beanId
     * @return bean的实例
     */
    @Override
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if(beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }

        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try{
            //根据全类名让ClassLoader将此类的.class文件load进来
            Class<?> clz = classLoader.loadClass(beanClassName);
            //反射调用无参构造函数
            return clz.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
        }
    }
}
