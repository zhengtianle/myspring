package cn.myspring.core.io;


import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    /**
     * 通过默认的ClassLoader加载classpath下的文件
     * @param path classpath下的文件路径
     */
    public ClassPathResource(String path) {
        this(path, null);
    }

    /**
     * 通过ClassLoader加载classpath下的文件
     * @param path classpath下的文件路径
     * @param classLoader 加载文件的类加载器
     */
    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    /**
     *
     * @return 给定classpath路径下文件对应的流
     * @throws IOException 类路径下找不到该文件
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.classLoader.getResourceAsStream(this.path);

        if(inputStream == null) {
            throw new BeanDefinitionStoreException(path + " cannot be opened", null);
        }
        return inputStream;
    }

    @Override
    public String getDescription() {
        return path;
    }
}
