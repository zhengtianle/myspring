package cn.myspring.context.annotation;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.factory.BeanDefinitionStoreException;
import cn.myspring.beans.factory.support.BeanDefinitionRegistry;
import cn.myspring.beans.factory.support.BeanNameGenerator;
import cn.myspring.core.io.Resource;
import cn.myspring.core.io.support.PackageResourceLoader;
import cn.myspring.core.type.classreading.MetadataReader;
import cn.myspring.core.type.classreading.SimpleMetadataReader;
import cn.myspring.stereotype.Component;
import cn.myspring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 * 相当于之前的XmlBeanDefinitionReader
 * 类路径下的注解包扫描
 */
public class ClassPathBeanDefinitionScanner {

    protected final Log logger = LogFactory.getLog(getClass());

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 核心方法，扫描包，得到各个bean的BeanDefinition
     * @param packagesToScan 要扫描的包。多个包用逗号分割
     * @return 给定packagesToScan的各个包下的带有@Component注解的类的BeanDefinition
     */
    public Set<BeanDefinition> doScan(String packagesToScan) {
        //把一串包名分割到数组中
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");
        Set<BeanDefinition> beanDefinitions = null;
        for(String basePackage : basePackages) {
            beanDefinitions = findCandidateComponents(basePackage);
            for(BeanDefinition beanDefinition : beanDefinitions) {
                //将带有@Component注解的bean的beanDefinition注册到factory中
                registry.registerBeanDefinition(beanDefinition.getId(), beanDefinition);
            }
        }
        return beanDefinitions;
    }

    /**
     *
     * @param basePackage 包名
     * @return 给定包内带有@Component注解的类的beanDefinition集合
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try{
            //将包内的所有类转换成Resource
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for(Resource resource : resources) {
                try{
                    //得到类的注解元数据和类信息元数据
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);
                    //判断当前遍历的类是否有@Component注解
                    if(metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                        //生成beanDefinition
                        ScannedGenericBeanDefinition scannedBeanDefinition = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                        //生成beanName(beanId)
                        String beanName = this.beanNameGenerator.generateBeanName(scannedBeanDefinition);
                        scannedBeanDefinition.setId(beanName);
                        candidates.add(scannedBeanDefinition);
                    }

                } catch (Throwable e) {
                    throw new BeanDefinitionStoreException("Failed to read candidate component class: " + resource, e);
                }
            }
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("类路径扫描IO失败", e);
        }
        return candidates;
    }
}
