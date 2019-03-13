package cn.myspring.core.io.support;

import cn.myspring.core.io.FileSystemResource;
import cn.myspring.core.io.Resource;
import cn.myspring.util.Assert;
import cn.myspring.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 */
public class PackageResourceLoader {

    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * 类路径或者包路径 -> 每个类一个Resource
     */
    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        //得到.class的路径
        URL url = classLoader.getResource(location);
        File rootFile = new File(url.getFile());

        //得到目录下的每个类文件
        Set<File> matchingFiles = retrieveMatchingFiles(rootFile);

        //类文件转换成Resource
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for(File file : matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    /**
     * 判断需要被检索的文件是否正常（存在，是目录，可读）
     * 调用检索文件方法
     */
    private Set<File> retrieveMatchingFiles(File rootFile) throws IOException {
        if(!rootFile.exists()) {
            //此路径表示的文件不存在
            if(logger.isDebugEnabled()) {
                logger.debug("跳过 [" + rootFile.getAbsolutePath() + "], 因为此路径表示的文件或者文件夹不存在");
            }
            return Collections.emptySet();
        }

        if(!rootFile.isDirectory()) {
            //不是目录
            if (logger.isWarnEnabled()) {
                logger.warn("跳过 [" + rootFile.getAbsolutePath() + "], 因为此路径表示的不是一个文件目录");
            }
            return Collections.emptySet();
        }

        if(!rootFile.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("无法在 [" + rootFile.getAbsolutePath() +
                        "] 目录下搜索匹配的文件，因为不允许应用程序读取此目录");
            }
            return Collections.emptySet();
        }

        //rootFile正常可操作，进入下面的递归搜索匹配中
        Set<File> result = new LinkedHashSet<File>(8);
        doRetrieveMatchingFiles(rootFile, result);
        return result;
    }

    /**
     * 真正的在目录下寻找匹配类文件，并将类文件存储到result中
     */
    private void doRetrieveMatchingFiles(File rootFile, Set<File> result) {
        File[] dirContents = rootFile.listFiles();
        if(dirContents == null) {
            if(logger.isWarnEnabled()) {
                logger.warn("无法检索该目录中的内容 [" + rootFile.getAbsolutePath() + "]");
            }
            return;
        }

        for(File content : dirContents) {
            if(content.isDirectory()) {
                //是目录
                if(!content.canRead()) {
                    if(logger.isDebugEnabled()) {
                        logger.debug("跳过子目录 [" + rootFile.getAbsolutePath() +
                                "] 因为不允许应用程序读取此目录");
                    }
                } else {
                    //递归检索该目录下的文件
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                //是一个类文件
                result.add(content);
            }
        }
    }

}
