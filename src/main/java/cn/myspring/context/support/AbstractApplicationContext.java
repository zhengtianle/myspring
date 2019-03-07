package cn.myspring.context.support;

import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.context.ApplicationContext;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 * 模板方法模式，子类（例如FileSystemXmlApplicationContext）继承此抽象类，子类实现getResource
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = getResource(configFile);
        reader.loadBeanDefinitions(resource);
    }


    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }


    public abstract Resource getResource(String configFile);

}
