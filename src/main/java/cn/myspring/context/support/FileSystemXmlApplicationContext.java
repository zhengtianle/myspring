package cn.myspring.context.support;

import cn.myspring.beans.factory.support.DefaultBeanFactory;
import cn.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.myspring.context.ApplicationContext;
import cn.myspring.core.io.FileSystemResource;
import cn.myspring.core.io.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {


    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }


    @Override
    public Resource getResource(String configFile) {
        return new FileSystemResource(configFile);
    }
}
