package cn.myspring.beans;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";


    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();
}
