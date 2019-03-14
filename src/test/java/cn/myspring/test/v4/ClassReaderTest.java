package cn.myspring.test.v4;

import cn.myspring.core.annotation.AnnotationAttributes;
import cn.myspring.core.io.ClassPathResource;
import cn.myspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import cn.myspring.core.type.classreading.ClassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 */
public class ClassReaderTest {

    /**
     * ASM：工作模式 Visitor访问者模式
     * 如果一个对象结构比较复杂，同时结构稳定不易变化，但却需要经常在此结构上定义新的操作，那就非常合适使用访问者模式
     * 如：需要在老师和学生（两个稳定的数据结构）中选拔科研优秀奖和劳动模范奖（两个操作）
     *  Client：new ClassWriter()   ---->    new ClassVisitor(wiriter)  ----> accept(visitor)
     *          ClassWriter
     *                      visit()
     *                      visitField()
     *                      visitMethod()
     *                      visitEnd()
     *                      <---------      ClassVisitor
     *                                                      visit()
     *                                                      visitField()
     *                                                      visitMethod()
     *                                                      visitEnd()
     *                                                      <---------      ClassReader
     */

    /**
     * 得到类的元数据信息
     * 测试ClassMetadataReadingVisitor
     */
    @Test
    public void testGetClassMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("cn/myspring/service/v4/PersonService.class");
        //ASM提供的classreader
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        //reader->visitor
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("cn.myspring.service.v4.PersonService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    /**
     * 得到注解中的信息
     */
    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("cn/myspring/service/v4/PersonService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        //reader->visitor
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "cn.myspring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("person", attributes.get("value"));

    }
}
