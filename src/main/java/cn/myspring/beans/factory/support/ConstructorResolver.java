package cn.myspring.beans.factory.support;

import cn.myspring.beans.BeanDefinition;
import cn.myspring.beans.ConstructorArgument;
import cn.myspring.beans.SimpleTypeConverter;
import cn.myspring.beans.factory.BeanCreationException;
import cn.myspring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 */
public class ConstructorResolver {

    protected final Log logger = LogFactory.getLog(getClass());

    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;
        try{
            beanClass = this.beanFactory.getBeanClassloader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(beanDefinition.getId(), "Instantiation of bean failed, can't resolve", e);
        }

        //得到类的构造函数集合
        Constructor<?>[] beanConstructors = beanClass.getConstructors();

        //实例对象
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);
        //<constructor-arg>集合
        ConstructorArgument constructorArgs = beanDefinition.getConstructorArgument();
        //普通value转换
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        //选择合适的构造器
        for(int i = 0; i < beanConstructors.length; i++) {
            //类里面的构造器的每个参数类型集合
            Class<?>[] parameterTypes = beanConstructors[i].getParameterTypes();
            if(parameterTypes.length != constructorArgs.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];
            //判断<constructor-arg>每个参数与构造器的每个参数是否类型匹配
            boolean result = this.valuesMathTypes(parameterTypes,
                    constructorArgs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);
            if(result) {
                constructorToUse = beanConstructors[i];
                break;
            }
        }

        //没有找到合适的构造器
        if(constructorToUse == null ){
            throw new BeanCreationException(beanDefinition.getId(), "can't find a apporiate constructor");
        }
        //找到了找到合适的构造器
        try{
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getId(), "can't find a create instance using " + constructorToUse, e);
        }
    }

    /**
     *
     * @param parameterTypes 类构造函数里面的参数类型集合
     * @param valueHolders <constructor-arg>内部属性集合，例如value,ref,……
     * @param argsToUse 调用构造函数需要使用的参数集合(只有初始空间没有值)
     * @param valueResolver reference -> bean
     * @param typeConverter value(默认String类型) -> 布尔或者整数
     * @return 是否可以使用此构造函数进行注入
     */
    private boolean valuesMathTypes(Class<?>[] parameterTypes,
                                    List<ConstructorArgument.ValueHolder> valueHolders,
                                    Object[] argsToUse,
                                    BeanDefinitionValueResolver valueResolver,
                                    SimpleTypeConverter typeConverter) {
        /**
         * TODO:实现构造函数的选择,如下例：
         * 在构造函数Person(NameDao nameDao, SexDao sexDao, int age)
         * 和Person(NameDao nameDao, SexDao sexDao, String age)中
         * 选择后者，因为age在xml中读取出来便是String类型，不需要转值(convert)
         */
        for(int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //得到TypedStringValue或者RuntimeBeanReference
            Object originalValue = valueHolder.getValue();
            try{
                //获得实例对象
                Object resolvedValue = valueResolver.resolveValueIfNessary(originalValue);
                //如果参数类型是int或者bool，但是其值在xml中默认是String，需要转型，例如"19"
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                //转型成功则记录在argToUse中
                //转型失败则抛出异常
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }
}
