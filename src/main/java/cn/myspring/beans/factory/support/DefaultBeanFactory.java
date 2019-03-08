package cn.myspring.beans.factory.support;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.config.ConfigurableBeanFactory;
import cn.myspring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry{

    private ClassLoader beanClassLoader = null;

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

        if(beanDefinition.isSingleton()) {
            //beanDefinition如果是单例的，则从DefaultSingletonBeanRegister中获取
            Object bean = this.getSingleton(beanId);
            if(bean == null) {
                //此bean之前没有单例注册过
                bean = createBean(beanDefinition);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        //beanDefinition不是单例，直接反射创建返回
        return createBean(beanDefinition);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        ClassLoader classLoader = this.getBeanClassloader();
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

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassloader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }
}
