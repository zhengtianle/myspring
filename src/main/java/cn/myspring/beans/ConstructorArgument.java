package cn.myspring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 */
public class ConstructorArgument {

    private final List<ValueHolder> argumentValues = new LinkedList<>();

    public ConstructorArgument() {}

    public void addArgumentValue(ValueHolder valueHolder) {
        this.argumentValues.add(valueHolder);
    }

    public List<ValueHolder> getArgumentValues() {
        //不可修改的
        return Collections.unmodifiableList(this.argumentValues);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }


    /**
     * 单独抽象一个具体的类，是因为除了value还可以支持type、name……
     * 例如
     * <constructor-arg type="int" value="10" />
     * <constructor-arg name="myname" value="ztl" />
     * TODO:实现 name type index
     */
    public static class ValueHolder {

        private Object value;

        /*private String type;

        private String name;*/

        public ValueHolder(Object value) {
            this.value = value;
        }

        /*public ValueHolder(Object value, String type) {
            this(value);
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this(value, type);
            this.name = name;
        }*/

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        /*public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }*/
    }
}
