package cn.myspring.beans.factory.config;

import cn.myspring.beans.factory.BeanFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 * 支持@Autowire自动注入的beanFactory
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 通过description中的field获得当前beanFactory中的bean实例
     */
    public Object resolveDependency(DependencyDescriptor descriptor);
}
