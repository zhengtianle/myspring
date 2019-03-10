package cn.myspring.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public abstract class Assert {

    public static void notNull(Object object, String exceptionMessage) {
        if(object == null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
