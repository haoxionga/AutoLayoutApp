package com.hx.autolayout.util;

import android.app.Application;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shinelon on 2017/9/10.
 */

public class LayoutUtil {
    private static LayoutUtil instance;

    public static LayoutUtil getInstance() {
        if (instance == null) {
            synchronized (LayoutUtil.class) {
                if (instance == null) {
                    instance = new LayoutUtil();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        String className = application.getPackageName() + ".R";
        try {
            Class<?> RClass = Class.forName(className);
            Class<?>[] classes = RClass.getClasses();
            for (Class<?> aClass : classes) {
                if ("layout".equals(aClass.getSimpleName())) {
                    Field[] fields = aClass.getFields();
                    for (Field field : fields) {
                        layoutIdMap.put(field.getName(), field.get(aClass));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Integer getLayoutIdByName(String layoutName) {
        if (layoutIdMap == null) {
            return -1;
        }
        Integer integer = (Integer) layoutIdMap.get(layoutName);
        if (integer == null) {
            return -1;
        }
        return integer;
    }

    private Map<String, Object> layoutIdMap = new HashMap<String, Object>();

}
