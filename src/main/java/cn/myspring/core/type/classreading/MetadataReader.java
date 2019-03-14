package cn.myspring.core.type.classreading;

import cn.myspring.core.io.Resource;
import cn.myspring.core.type.AnnotationMetadata;
import cn.myspring.core.type.ClassMetadata;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-14
 * @Author ZhengTianle
 * Description:
 */
public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
