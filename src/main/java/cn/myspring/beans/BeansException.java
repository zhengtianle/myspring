package cn.myspring.beans;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public class BeansException extends RuntimeException{

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeansException(String msg) {
        super(msg);
    }
}
