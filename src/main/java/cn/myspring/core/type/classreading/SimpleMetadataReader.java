package cn.myspring.core.type.classreading;

import cn.myspring.core.io.Resource;
import cn.myspring.core.type.AnnotationMetadata;
import cn.myspring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 * 利用ClassReader读取类信息
 * 获取类的注解元数据信息
 * 类的元数据信息
 */
public class SimpleMetadataReader implements MetadataReader{

    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;


    public SimpleMetadataReader(Resource resource) throws IOException {
        //classreader读取类信息
        InputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;
        try{
            classReader = new ClassReader(is);
        } finally {
            is.close();
        }

        //AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor extends ClassReader
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);
        //AnnotationMetadataReadingVisitor implements AnnotationMetadata
        this.annotationMetadata = visitor;
        //ClassMetadataReadingVisitor implements ClassMetadata
        this.classMetadata = visitor;
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
