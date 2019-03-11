package cn.myspring.beans;

import cn.myspring.beans.propertyeditors.CustomBooleanEditor;
import cn.myspring.beans.propertyeditors.CustomNumberEditor;
import cn.myspring.util.ClassUtils;
import com.sun.corba.se.impl.io.TypeMismatchException;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-11
 * @Author ZhengTianle
 * Description:
 */
public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;

    public SimpleTypeConverter() {}

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if(ClassUtils.isAssignableValue(requiredType, value)) {
            return (T) value;
        } else {
            //TODO：当前只支持String类型
            if(value instanceof String) {
                PropertyEditor editor = findDefaultEditor(requiredType);
                try{
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value + " can't be parsed to " + requiredType);
                }
                return (T)editor.getValue();
            } else {
                throw new RuntimeException("Todo: can't convert value for " + value + " class:" + requiredType);
            }
        }
    }

    private PropertyEditor findDefaultEditor(Class<?> requiredType) {
        PropertyEditor editor = this.getDefaultEditor(requiredType);
        if(editor == null) {
            throw new RuntimeException("Editor for " + requiredType + " has not been implemented");
        }
        return editor;
    }

    private PropertyEditor getDefaultEditor(Class<?> requiredType) {
        if(this.defaultEditors == null) {
            createDefaultEditors();
        }
        return this.defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);

        //TODO:当前只支持布尔和整数

        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));

        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
    }


}
