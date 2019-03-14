package cn.myspring.beans.factory.annotation;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.core.type.AnnotationMetadata;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 * 注解beanDefinition
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
