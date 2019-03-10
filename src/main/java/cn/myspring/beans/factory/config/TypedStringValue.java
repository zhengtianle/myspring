package cn.myspring.beans.factory.config;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 * <property name="age" value="19" />
 */
public class TypedStringValue {

    //value
    private String value;

    public TypedStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
