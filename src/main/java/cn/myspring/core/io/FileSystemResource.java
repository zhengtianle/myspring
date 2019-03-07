package cn.myspring.core.io;

import cn.myspring.util.Assert;
import cn.myspring.util.ClassUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public class FileSystemResource implements Resource {

    private final String path;

    private final File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
        file = new File(path);
    }

    /**
     *
     * @return 给定文件系统路径下对应的流
     * @throws IOException 找不到该文件
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }
}
