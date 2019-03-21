package cn.myspring.beans.factory.config;

import cn.myspring.util.Assert;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 */
public class DependencyDescriptor {

    private Field field;

    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        Assert.notNull(this.field, "Field must not be null");
        return this.field.getType();
    }

    public boolean isRequired() {
        return this.required;
    }
}
