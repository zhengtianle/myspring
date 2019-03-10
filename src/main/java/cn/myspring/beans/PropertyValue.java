package cn.myspring.beans;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    //标识当前propertyValue的value是不是转换成convertedValue(实例)
    private boolean converted = false;

    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public synchronized boolean isConverted() {
        return this.converted;
    }


}
