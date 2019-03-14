package cn.myspring.test.v4;

import cn.myspring.core.annotation.AnnotationAttributes;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.type.AnnotationMetadata;
import cn.myspring.core.type.classreading.MetadataReader;
import cn.myspring.core.type.classreading.SimpleMetadataReader;
import cn.myspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 * 测试读取元数据封装接口MetadataReader和其实现类SimpleMetadataReader
 */
public class MetadataReaderTest {
    /**
     * 相当于测试MetadataReader(SimpleMetadataReader)是否可以完成类信息和注解信息的读取
     */
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("cn/myspring/service/v4/PersonService.class");

        MetadataReader reader = new SimpleMetadataReader(resource);
        //注意：不需要单独使用ClassMetadata
        //ClassMetadata clzMetadata = reader.getClassMetadata();
        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("person", attributes.get("value"));

        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isInterface());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("cn.myspring.service.v4.PersonService", amd.getClassName());
        Assert.assertEquals("java.lang.Object", amd.getSuperClassName());
        Assert.assertEquals(0, amd.getInterfaceNames().length);

    }
}
