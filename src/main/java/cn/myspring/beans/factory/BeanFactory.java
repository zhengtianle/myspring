package cn.myspring.beans.factory;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public interface BeanFactory {

    Object getBean(String beanId);
}
