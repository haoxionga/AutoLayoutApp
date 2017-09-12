package com.hx.autolayout.util;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Shinelon on 2017/9/10.
 */

public class ObjectUtil {
    private static ObjectUtil instance;

    public static ObjectUtil getInstance() {
        if (instance == null) {
            synchronized (ObjectUtil.class) {
                if (instance == null) {
                    instance = new ObjectUtil();
                }
            }
        }
        return instance;
    }

    private String[] packageName = {
            "android.view", "android.widget", "android.support.v4.view", "android.support.v4.widget", "android.support.v7.widget", "android.support.v7.view.menu",
    };

    /**
     * 通过类名得到对象
     *
     * @param name1   view的simpleName
     * @param context 上下文
     * @return new出来的具体类
     */
    public Object getObject(String name1, Context context) {
        Object o = new Object();
        if (name1.contains(".")) {
            //全路径
            o = getObjectByName(name1, context);
        } else {
            //非全路径，使用包名去拼
            for (String s : packageName) {
                Object newObject = getObjectByName(s + "." + name1, context);
                if (newObject != null) {
                    o = newObject;
                    break;
                }
            }

        }
        //返回对象
        return o;
    }

    private Object getObjectByName(String name1, Context context) {
        Object o = null;
        try {
            Class<?> aClass = Class.forName(name1);
                 /*以下调用带参的、私有构造函数*/
            Constructor c1 = aClass.getDeclaredConstructor(new Class[]{Context.class,});
            c1.setAccessible(true);
            o = c1.newInstance(new Object[]{context});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }


}
