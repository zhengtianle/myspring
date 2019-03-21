package cn.myspring.beans.factory.annotation;

import cn.myspring.beans.BeansException;
import cn.myspring.beans.annotation.Autowired;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.config.AutowireCapableBeanFactory;
import cn.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.myspring.core.annotation.AnnotationUtils;
import cn.myspring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-21
 * @Author ZhengTianle
 * Description:
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;
    private final String requiredParameterName = "required";
    private boolean requiredParameterValue = true;


    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

    public AutowiredAnnotationProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    /**
     * 核心方法
     * 找出传入的类及其祖先依赖的对象
     */
    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        LinkedList<InjectionElement> elements = new LinkedList<>();

        //当前类到父类，注册寻找依赖对象并保存到LinkedList<InjectionElement> elements中
        while (clazz != null && clazz != Object.class) {
            LinkedList<InjectionElement> currElements = new LinkedList<>();
            //属性注入
            for(Field field : clazz.getDeclaredFields()) {
                Annotation annotation = findAutowiredAnnotation(field);
                if(annotation != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    //获得required的属性值
                    boolean required = determineRequiredStatus(annotation);
                    //需要被注入的属性
                    currElements.add(new AutowiredFieldElement(field, required,beanFactory));
                }
            }

            //方法注入
            for(Method method : clazz.getDeclaredMethods()) {
                //TODO:暂时不作处理
            }
            elements.addAll(0, currElements);
            clazz = clazz.getSuperclass();
        }

        return new InjectionMetadata(elements);
    }

    private boolean determineRequiredStatus(Annotation annotation) {
        try {
            Method method = ReflectionUtils.findMethod(annotation.annotationType(), this.requiredParameterName);
            if (method == null) {
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, annotation));
        }
        catch (Exception e) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            //目前支持自动注入的注解信息，目前就是获得@Autowired注解里面的信息
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * bean的声明周期中被调用
     * 后置处理器注入依赖属性值
     */
    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        }
        catch (Throwable e) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", e);
        }
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
