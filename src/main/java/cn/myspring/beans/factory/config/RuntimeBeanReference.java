package cn.myspring.beans.factory.config;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 * <property name="nameDao" ref="nameDao" />
 */
public class RuntimeBeanReference {

    //ref
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
