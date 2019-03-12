package cn.myspring.beans.factory.support;

import cn.myspring.beans.factory.BeanFactory;
import cn.myspring.beans.factory.config.RuntimeBeanReference;
import cn.myspring.beans.factory.config.TypedStringValue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 * 使用描述类RuntimeBeanReference或者TypedStringValue 创建对应的实例对象
 */
public class BeanDefinitionValueResolver {

    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 如果传进来的值真的需要resolve，通过beanFactory的getBean得到value对应创建的对象
     * @param value beanReference
     * @return RuntimeBeanReference: beanReference创建的对象
     *         TypedStringValue: getValue()->字符串常量
     */
    public Object resolveValueIfNessary(Object value) {
        if(value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            //ref引用的就是id，所以将ref当做beanId使用
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        } else if(value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //TODO:以后还可能会有其他类型解析
            throw new RuntimeException("the value " + value + "has not implemented");
        }
    }
}
