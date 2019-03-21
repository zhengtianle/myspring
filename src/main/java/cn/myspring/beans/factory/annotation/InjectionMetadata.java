package cn.myspring.beans.factory.annotation;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 * List<InjectionElement> injectionElements 保存需要被注入的元素信息
 * inject()方法将全部依赖对象注入
 */
public class InjectionMetadata {

    private List<InjectionElement> injectionElements;

    public InjectionMetadata(List<InjectionElement> injectionElements) {
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    /**
     * 将传入对象的所有依赖对象注入进来
     */
    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        for (InjectionElement ele : injectionElements) {

            ele.inject(target);
        }
    }
}
