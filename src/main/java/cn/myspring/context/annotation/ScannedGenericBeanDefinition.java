package cn.myspring.context.annotation;

import cn.myspring.beans.factory.annotation.AnnotatedBeanDefinition;
import cn.myspring.beans.factory.support.GenericBeanDefinition;
import cn.myspring.core.type.AnnotationMetadata;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 * 扫描得到的BeanDefinition
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;


    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }


    public final AnnotationMetadata getMetadata() {
        return this.metadata;
    }

}
