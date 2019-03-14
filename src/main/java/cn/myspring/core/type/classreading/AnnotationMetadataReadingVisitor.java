package cn.myspring.core.type.classreading;

import cn.myspring.core.annotation.AnnotationAttributes;
import cn.myspring.core.type.AnnotationMetadata;
import jdk.internal.org.objectweb.asm.Type;
import org.springframework.asm.AnnotationVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 * 继承了ClassMetadataReadingVisitor
 * 间接继承了ClassVisitor
 * 重写了visitAnnotation()
 * 相当于这里可以得到注解信息也可以得到类信息
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

    /**
     *
     */
    private final Set<String> annotationSet = new LinkedHashSet<>(4);

    /**
     *
     */
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);

    public AnnotationMetadataReadingVisitor() {}

    /**
     *
     * @param description 类似于这种字符串：Lcn/myspring/stereotype/Component，L表示是一个Object
     * @param visible
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(String description, boolean visible) {
        //转换成注解的类名 .分割
        String className = Type.getType(description).getClassName();
        this.annotationSet.add(className);
        //解析注解的属性
        return new AnnotationAttributesReadingVisitor(className, this.attributesMap);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    @Override
    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    @Override
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }
}
