package cn.myspring.context.support;

import cn.myspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import cn.myspring.beans.factory.config.ConfigurableBeanFactory;
import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.context.ApplicationContext;
import cn.myspring.core.io.Resource;
import cn.myspring.util.ClassUtils;

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

    private ClassLoader beanClassLoader;

    AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = getResource(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(beanClassLoader);
        registerBeanPostProcessors(factory);
    }

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        //准备 Autowired注解处理器
        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(factory);
        beanFactory.addBeanPostProcessor(postProcessor);
    }


    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassloader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }


    public abstract Resource getResource(String configFile);

}
