package cn.myspring.context.annotation;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.annotation.AnnotatedBeanDefinition;
import cn.myspring.beans.factory.support.BeanDefinitionRegistry;
import cn.myspring.beans.factory.support.BeanNameGenerator;
import cn.myspring.core.annotation.AnnotationAttributes;
import cn.myspring.core.type.AnnotationMetadata;
import cn.myspring.stereotype.Component;
import cn.myspring.util.ClassUtils;
import cn.myspring.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    /**
     * 用给定的beanDefinition生成它的beanName
     * 1. 读取@Component注解里面的value值当做beanName
     * 或者
     * 2. 根据类名自动生成一个beanName
     */
    @Override
    public String generateBeanName(BeanDefinition definition) {
        if(definition instanceof AnnotatedBeanDefinition) {
            //如果是注解类型的beanDefiniton则可以读取其value
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if(StringUtils.hasText(beanName)) {
                return beanName;
            }
        }
        //如果不是注解类型的beanDefinition或者@Component注解里面没有value，或者value里面没有值，则根据类名自动创建一个beanName
        return buildDefaultBeanName(definition);
    }

    /**
     * 读取类的@Component注解里面的value属性值，用来当做beanName
     * @param definition
     * @return
     */
    private String determineBeanNameFromAnnotation(AnnotatedBeanDefinition definition) {
        AnnotationMetadata amd = definition.getMetadata();
        //得到所有注解类型
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for(String type : types) {
            //得到这个注解里面的属性定义
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if(isStereotypeWithNameValue(type, attributes)) {
                //是包含value属性的@Component，读取value对应的值
                Object value = attributes.get("value");
                if(value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal)) {
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    /**
     *
     * @param annotationType 注解类型
     * @param attributes 注解里面的属性值
     * @return 是否是包含value属性的@Component
     */
    private boolean isStereotypeWithNameValue(String annotationType, AnnotationAttributes attributes) {
        //检验是否是@Component注解
        boolean isStereotype = annotationType.equals(Component.class.getName());
        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }

    /**
     * e.g. "mypackage.MyJdbcDao" -> "myJdbcDao".
     */
    private String buildDefaultBeanName(BeanDefinition definition) {
        String shortName = ClassUtils.getShortName(definition.getBeanClassName());
        //Thus "FooBah" becomes "fooBah" and "X" becomes "x", but "URL" stays as "URL".
        return Introspector.decapitalize(shortName);
    }
}
