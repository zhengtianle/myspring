package cn.myspring.beans;

import com.sun.corba.se.impl.io.TypeMismatchException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-11
 * @Author ZhengTianle
 * Description:
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
