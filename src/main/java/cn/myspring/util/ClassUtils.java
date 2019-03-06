package cn.myspring.util;

import com.sun.istack.internal.Nullable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public abstract class ClassUtils {

    /**
     *
     */
    /**
     * 直接复制的Spring源码 ==> /org/springframework/util/ClassUtils.class
     * ClassLoader解析：https://blog.csdn.net/briblue/article/details/54973413
     * 1. ClassLoader用来加载class文件的。
     * 2. 系统内置的ClassLoader通过双亲委托来加载指定路径下的class和资源。
     * 3. 可以自定义ClassLoader一般覆盖findClass()方法。
     * 4. ContextClassLoader与线程相关，可以获取和设置，可以绕过双亲委托的机制。
     *
     *
     * 源码注释：
     * Return the default ClassLoader to use: typically the thread context
     * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
     * class will be used as fallback.
     * <p>Call this method if you intend to use the thread context ClassLoader
     * in a scenario where you clearly prefer a non-null ClassLoader reference:
     * for example, for class path resource loading (but not necessarily for
     * {@code Class.forName}, which accepts a {@code null} ClassLoader
     * reference as well).
     * @return the default ClassLoader (only {@code null} if even the system
     * ClassLoader isn't accessible)
     * @see Thread#getContextClassLoader()
     * @see ClassLoader#getSystemClassLoader()
     */
    @Nullable
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }
}
