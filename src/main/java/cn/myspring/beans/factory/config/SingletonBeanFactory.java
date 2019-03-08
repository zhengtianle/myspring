package cn.myspring.beans.factory.config;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 * 单例接口
 */
public interface SingletonBeanFactory {

    void registerSingleton(String beanId, Object singletonObject);

    Object getSingleton(String beanId);
}
