package com.hx.autolayout.util;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shinelon on 2017/9/10.
 */

public class AttributeUtil {
    private static AttributeUtil instance;

    public static AttributeUtil getInstance() {
        if (instance == null) {
            synchronized (AttributeUtil.class) {
                if (instance == null) {
                    instance = new AttributeUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 根据VIew获取属性集合，
     */
    public Map<String, String> getAttributeMaps(View view) {
        Map<String, String> map =new HashMap<String, String>();
        try {
            Field field1 = view.getClass().getField("mAttributes");
            if (field1.getType().isArray()) {
                String[] strings = (String[]) field1.get(view);
                if (strings != null) {
                    Map<String, String> newMap = convertArray(strings);
                    if (newMap != null && !newMap.isEmpty()) {
                        map = newMap;
                    }
                }
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 将属性结合转换成key，value形式
     */
    private Map<String, String> convertArray(String[] strings) {
        Map<String, String> map = new HashMap<>();
        int length = strings.length;
        for (int i = 0; i < length; i += 2) {
            String key = strings[i];
            String value = strings[i + 1];
            if (key != null && value != null) {
                map.put(key.trim(), value.trim());
            }
        }
        return map;
    }
}
