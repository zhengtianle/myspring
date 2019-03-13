package cn.myspring.core.type;

import cn.myspring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 */
public interface AnnotationMetadata extends ClassMetadata{

    /**
     * 得到类的所有注解类型
     */
    Set<String> getAnnotationTypes();

    /**
     * 判断该类里是否有这个注解类型
     */
    boolean hasAnnotation(String annotationType);

    /**
     * 得到该类的某个类型注解里面的属性值，如value="xxx"
     */
    public AnnotationAttributes getAnnotationAttributes(String annotationType);
}
