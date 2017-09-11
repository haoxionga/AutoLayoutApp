package com.hx.autolayout.util;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.view.ViewGroup;

import com.hx.autolayout.bean.ViewBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * xml解析类
 */

public class XmlParseUtil {
    private static XmlParseUtil instance;

    public static XmlParseUtil getInstance() {
        if (instance == null) {
            synchronized (XmlParseUtil.class) {
                if (instance == null) {
                    instance = new XmlParseUtil();
                }
            }
        }
        return instance;
    }


    private String TAG = "XmlParseUtil";

    public ViewBean convertXmlToBean(int layoutResID, Context context) {
        ViewBean rootViewBean = null, lastViewGroup = null;
        String nearStartName = "";
        XmlResourceParser xmlParser = context.getResources().getLayout(layoutResID);
        try {
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
                if (xmlParser.getName() != null && xmlParser.getName().length() > 0) {
                }
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = xmlParser.getName();
                        //解析属性
                        int attributeCount = xmlParser.getAttributeCount();
                        Map<String, String> attributeMap = new HashMap<>();
                        for (int i = 0; i < attributeCount; i++) {
                            attributeMap.put(xmlParser.getAttributeName(i), xmlParser.getAttributeValue(i));
                        }
                        //如果是include引入标签
                        if ("include".equals(name)) {
                            String layout = attributeMap.get("layout");
                            layout = layout.replace("@layout/", "");
                            Integer layoutId = LayoutUtil.getInstance().getLayoutIdByName(layout);
                            if (layoutId != -1) {
                                ViewBean viewBean = convertXmlToBean(layoutId, context);
                                //上一个viewgroup是否为空
                                if (lastViewGroup == null) {
                                    rootViewBean.setChildView(viewBean);
                                } else {
                                    lastViewGroup.setChildView(viewBean);
                                }
                            }
                        } else {
                            //非include引入标签
                            nearStartName = name;
                            if (rootViewBean == null) {
                                //创建根标签
                                rootViewBean = getViewBean();
                                rootViewBean.setName(name);
                                rootViewBean.setAttributeMap(attributeMap);
                            } else {
                                //创建子标签
                                ViewBean bean = new ViewBean();
                                bean.setName(name);
                                bean.setAttributeMap(attributeMap);
                                //上一个viewGroup是否为空
                                if (lastViewGroup == null) {
                                    rootViewBean.setChildView(bean);
                                } else {
                                    lastViewGroup.setChildView(bean);
                                }
                                //通过名字判断是否是ViewGroup
                                if (ObjectUtil.getInstance().getObject(name, context) instanceof ViewGroup) {
                                    //是viewgroup的情况
                                    lastViewGroup = bean;
                                }

                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        String name1 = xmlParser.getName();
                        //非当前的标签，那就是上一个viewgroup的开始标签
                        if (!nearStartName.equals(name1)) {
                            lastViewGroup = null;
                        }
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            xmlParser.close();
        }
        return rootViewBean;
    }

    private ViewBean getViewBean() {
        return new ViewBean();
    }
}
