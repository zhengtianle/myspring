package cn.myspring.test.v2;

import cn.myspring.beans.SimpleTypeConverter;
import cn.myspring.beans.TypeConverter;
import com.sun.corba.se.impl.io.TypeMismatchException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-11
 * @Author ZhengTianle
 * Description:
 */
public class TypeConverterTest {
    TypeConverter converter;

    @Before
    public void setUp() {
        converter = new SimpleTypeConverter();
    }


    @Test
    public void testConvertStringToInt() {

        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());

        try{
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertStringToBoolean1() {
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertEquals(true, b.booleanValue());

        try{
            converter.convertIfNecessary("xxx", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertStringToBoolean2() {
        Boolean b = converter.convertIfNecessary("on", Boolean.class);
        Assert.assertEquals(true, b.booleanValue());

        try{
            converter.convertIfNecessary("xxx", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertStringToBoolean3() {
        Boolean b = converter.convertIfNecessary("0", Boolean.class);
        Assert.assertEquals(false, b.booleanValue());

        try{
            converter.convertIfNecessary("xxx", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }
}
