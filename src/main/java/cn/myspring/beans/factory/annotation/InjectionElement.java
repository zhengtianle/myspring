package cn.myspring.beans.factory.annotation;

import cn.myspring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 */
public abstract class InjectionElement {
    //a single member (a field or a method) or a constructor.
    protected Member member;
    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member,AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
