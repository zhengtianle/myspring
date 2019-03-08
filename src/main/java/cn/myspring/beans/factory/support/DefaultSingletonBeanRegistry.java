package cn.myspring.beans.factory.support;

import cn.myspring.beans.factory.config.SingletonBeanFactory;
import cn.myspring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 * 单例接口默认实现类
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanFactory {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanId, Object singletonObject) {
        Assert.notNull(beanId, "'beanId' must not be null");

        Object oldObject = this.singletonObjects.get(beanId);
        if(oldObject != null) {
            //beanName不可重复
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanId + "': there is already object [" + oldObject + "] exist");
        }
        singletonObjects.put(beanId, singletonObject);
    }

    @Override
    public Object getSingleton(String beanId) {
        return this.singletonObjects.get(beanId);
    }
}
