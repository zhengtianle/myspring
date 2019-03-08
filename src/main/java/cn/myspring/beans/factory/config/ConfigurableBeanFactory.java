package cn.myspring.beans.factory.config;

import cn.myspring.beans.factory.BeanFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassloader();
}
