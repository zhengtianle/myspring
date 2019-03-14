package cn.myspring.beans.factory.support;

import cn.myspring.beans.BeanDefinition;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 */
public interface BeanNameGenerator {

    /**
     * 根据给定的BeanDefinition生成bean的name
     * @param definition
     * @return 生成的bean的name
     */
    String generateBeanName(BeanDefinition definition);
}
