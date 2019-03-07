package cn.myspring.beans.factory.support;

import cn.myspring.beans.BeanDefinition;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 * 1. 获取BeanDefinition实例
 * 2. 注册BeanDefinition实例
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanId);

    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}
