package cn.myspring.context.support;

import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.io.Resource;


/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-7
 * @Author ZhengTianle
 * Description:
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {


    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }


    @Override
    public Resource getResource(String configFile) {
        return new ClassPathResource(configFile, this.getBeanClassloader());
    }
}
