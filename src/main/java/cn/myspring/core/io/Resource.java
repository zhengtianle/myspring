package cn.myspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 * 获取文件的InputStream
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

    String getDescription();
}
