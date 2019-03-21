package cn.myspring.beans.factory.support;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.PropertyValue;
import cn.myspring.beans.SimpleTypeConverter;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.config.BeanPostProcessor;
import cn.myspring.beans.factory.config.ConfigurableBeanFactory;
import cn.myspring.beans.factory.config.DependencyDescriptor;
import cn.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.myspring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-6
 * @Author ZhengTianle
 * Description:
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry{

    private ClassLoader beanClassLoader = null;

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();



    public DefaultBeanFactory() {}

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId, beanDefinition);
    }

    /**
     * 注意，提供的id所代表的类，需要有无参构造函数
     * @param beanId
     * @return bean的实例
     */
    @Override
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanId);
        if(beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }

        if(beanDefinition.isSingleton()) {
            //beanDefinition如果是单例的，则从DefaultSingletonBeanRegister中获取
            Object bean = this.getSingleton(beanId);
            if(bean == null) {
                //此bean之前没有单例注册过
                bean = createBean(beanDefinition);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        //beanDefinition不是单例，直接反射创建返回
        return createBean(beanDefinition);
    }

    /**
     * 通过beanDefinition反射调用无参构造函数创建对象并执行setter注入
     * @param beanDefinition bean描述
     * @return beanDefinition描述的bean对象实例（已设置属性值）
     */
    private Object createBean(BeanDefinition beanDefinition) {
        //创建实例
        Object bean = instantiateBean(beanDefinition);
        //设置属性
        populateBean(beanDefinition, bean);
        return bean;
    }

    /**
     * 通过beanDefinition反射调用无参构造函数创建对象
     * @param beanDefinition bean的描述信息
     * @return beanDefinition描述的bean对象实例（属性值均为默认值）
     */
    private Object instantiateBean(BeanDefinition beanDefinition) {
        if(beanDefinition.hasConstructorArgumentValues()) {
            ConstructorResolver constructorResolver = new ConstructorResolver(this);
            return constructorResolver.autowireConstructor(beanDefinition);
        }
        ClassLoader classLoader = this.getBeanClassloader();
        String beanClassName = beanDefinition.getBeanClassName();
        try{
            //根据全类名让ClassLoader将此类的.class文件load进来
            Class<?> clz = classLoader.loadClass(beanClassName);
            //反射调用无参构造函数
            return clz.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
        }
    }

    /**
     * 如果该<bean>中有property，则调用set方法给bean对象注入属性值
     * @param beanDefinition bean的描述definition
     * @param bean <bean>元素对应的无参构造函数创建的对象
     */
    private void populateBean(BeanDefinition beanDefinition, Object bean) {

        for(BeanPostProcessor processor : this.getBeanPostProcessors()){
            if(processor instanceof InstantiationAwareBeanPostProcessor){
                //后置属性处理器，自动注入@Autowired
                ((InstantiationAwareBeanPostProcessor)processor).postProcessPropertyValues(bean, beanDefinition.getId());
            }
        }


        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();

        //没有<property>则不需要setter注入
        if(propertyValues == null || propertyValues.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try{
            for(PropertyValue property : propertyValues) {
                String propertyName = property.getName();
                Object originalValue = property.getValue();//RuntimeBeanReference等reference
                //resolve成实例
                Object resolvedValue = resolver.resolveValueIfNessary(originalValue);
                //调用set方法
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for(PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)) {//找到对应的属性
                        //convert类型
                        Object convertedVlaue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedVlaue);//调用set方法
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassloader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 从当前bean工厂中找出descriptor描述的依赖对象
     */
    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        //找到依赖对象的类型
        Class<?> typeToMatch = descriptor.getDependencyType();
        //从已经获取到的beanDefinition列表中查找
        for(BeanDefinition bd : this.beanDefinitionMap.values()) {
            //通过beanDefinition中的className（xml中的class字符串）解析Class
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if(typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getId());
            }
        }
        return null;
    }

    private void resolveBeanClass(BeanDefinition bd) {
        if(bd.hasBeanClass()) {
            return ;
        } else {
            try {
                bd.resolveBeanClass(getBeanClassloader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class: "+bd.getBeanClassName());
            }
        }
    }
}
