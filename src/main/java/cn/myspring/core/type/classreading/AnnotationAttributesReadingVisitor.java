package cn.myspring.core.type.classreading;

import cn.myspring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 * 注解属性解析
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    //注解类名 如cn.myspring.stereotype.Component
    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(org.springframework.asm.SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    /**
     * 注解类名 -> 属性map（key->value）
     */
    @Override
    public final void visitEnd(){
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    /**
     * 假设注解里面写的是value="person"
     * @param attributeName 属性名 如value
     * @param attributeValue 属性值 如person
     */
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }

}
