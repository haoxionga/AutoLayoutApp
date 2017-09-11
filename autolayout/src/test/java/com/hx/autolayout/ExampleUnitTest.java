package com.hx.autolayout;

import android.view.View;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        TestBean testBean = new TestBean();




        Field[] fields = View.class.getClass().getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }
}