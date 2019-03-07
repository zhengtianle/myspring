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
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry{



    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory() {}

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId, beanDefinition);
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
