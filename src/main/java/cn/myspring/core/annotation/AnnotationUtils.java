package cn.myspring.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 */
public abstract class AnnotationUtils {

    public static <T extends Annotation> T getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
        T annotation = ae.getAnnotation(annotationType);
        if (annotation == null) {
            for (Annotation metaAnnotation : ae.getAnnotations()) {
                annotation = metaAnnotation.annotationType().getAnnotation(annotationType);
                if (annotation != null) {
                    break;
                }
            }
        }
        return annotation;
    }
}
