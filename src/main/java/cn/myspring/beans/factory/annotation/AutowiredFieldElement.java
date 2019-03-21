package cn.myspring.beans.factory.annotation;

import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.config.AutowireCapableBeanFactory;
import cn.myspring.beans.factory.config.DependencyDescriptor;
import cn.myspring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 */
public class AutowiredFieldElement extends InjectionElement {

    private boolean required;

    public AutowiredFieldElement(Field field, boolean required, AutowireCapableBeanFactory factory) {
        super(field, factory);
        this.required = required;
    }

    public Field getField(){
        return (Field)this.member;
    }

    /**
     * 将target对象中的field注入实例
     */
    @Override
    public void inject(Object target) {
        Field field = this.getField();

        try{
            DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(descriptor);
            if(value != null) {
                //将一个字段设置为可读写，主要针对private字段；
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (IllegalAccessException e) {
            throw new BeanCreationException("Could not autowire field: " + field, e);
        }
    }
}
