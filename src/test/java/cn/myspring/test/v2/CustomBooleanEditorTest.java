package cn.myspring.test.v2;

import cn.myspring.beans.propertyeditors.CustomBooleanEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-11
 * @Author ZhengTianle
 * Description:
 */
public class CustomBooleanEditorTest {

    @Test
    public void testConvertStringToBoolean() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("false");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("off");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true, ((Boolean)editor.getValue()).booleanValue());
        editor.setAsText("no");
        Assert.assertEquals(false, ((Boolean)editor.getValue()).booleanValue());

        try{
            editor.setAsText("aabbcc");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();

    }

}
