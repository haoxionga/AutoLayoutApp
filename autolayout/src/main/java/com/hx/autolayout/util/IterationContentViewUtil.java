package com.hx.autolayout.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hx.autolayout.AutoLayout;
import com.hx.autolayout.LayoutSizeUtil;
import com.hx.autolayout.constant.SizeUnitType;
import com.hx.autolayout.bean.SizeUnitBean;
import com.hx.autolayout.bean.ViewBean;

import java.lang.reflect.Method;
import java.util.Map;

/***
 * 遍历ContentView
 *
 * */
public class IterationContentViewUtil {
    private static IterationContentViewUtil singleton = null;

    public static IterationContentViewUtil getInstance() {
        if (singleton == null) {
            synchronized (IterationContentViewUtil.class) {
                if (singleton == null) {
                    singleton = new IterationContentViewUtil();
                }
            }
        }
        return singleton;
    }

    public void initNativeFragmentSize(Fragment fragment, int layoutId) {
        ViewGroup contentView = (ViewGroup) fragment.getView();
        if (contentView == null) {
            throw new ExceptionInInitializerError(fragment.getClass().getName() + ":布局文件为空");
        }
        if (LayoutSizeUtil.getInstance().getResources() == null) {
            throw new ExceptionInInitializerError("LayoutSizeUtil尚未初始化,请在您项目Application的onCreate处调用");
        }
        AutoLayout annotation;
        try {
            //先获取类上的注解
            annotation = fragment.getClass().getAnnotation(AutoLayout.class);
            if (annotation == null) {
                //获取方法上的注解
                Method method = fragment.getClass().getMethod("onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class);
                annotation = method.getAnnotation(AutoLayout.class);
            }
            initSize(fragment.getContext(), layoutId, contentView, annotation);
            Log.d("IterationsentViewUti", "有数据");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void initActivitySize(Activity activity, int layoutResID) {
        ViewGroup contentView = (ViewGroup) ((ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
        if (contentView == null) {
            throw new ExceptionInInitializerError(activity.getClass().getName() + ":布局文件为空");
        }
        if (LayoutSizeUtil.getInstance().getResources() == null) {
            throw new ExceptionInInitializerError("LayoutSizeUtil尚未初始化,请在您项目Application的onCreate处调用");
        }
        AutoLayout annotation;
        try {
            //先获取类上的注解
            annotation = activity.getClass().getAnnotation(AutoLayout.class);
            if (annotation == null) {
                //获取方法上的注解
                Method method = activity.getClass().getDeclaredMethod("onCreate", Bundle.class);
                annotation = method.getAnnotation(AutoLayout.class);
            }
            initSize(activity, layoutResID, contentView, annotation);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void initNativeFragmentSize(android.app.Fragment fragment, int layoutId) {
        ViewGroup contentView = (ViewGroup) fragment.getView();
        if (contentView == null) {
            throw new ExceptionInInitializerError(fragment.getClass().getName() + ":布局文件为空");
        }
        if (LayoutSizeUtil.getInstance().getResources() == null) {
            throw new ExceptionInInitializerError("LayoutSizeUtil尚未初始化,请在您项目Application的onCreate处调用");
        }
        AutoLayout annotation;
        try {
            //先获取类上的注解
            annotation = fragment.getClass().getAnnotation(AutoLayout.class);
            if (annotation == null) {
                //获取方法上的注解
                Method method = fragment.getClass().getMethod("onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class);
                annotation = method.getAnnotation(AutoLayout.class);
            }
            initSize(fragment.getActivity(), layoutId, contentView, annotation);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private void initSize(Context context, int layoutResID, ViewGroup contentView, AutoLayout annotation) {
        if (annotation != null) {
            boolean autoLayout = annotation.isAutoLayout();
            boolean changeSizeTYpe = annotation.isChangeSizeType();
            SizeUnitBean sizeUnit = null;
            if (changeSizeTYpe) {
                SizeUnitType heightUnit = annotation.heightUnit();
                SizeUnitType widthUnit = annotation.widthUnit();
                SizeUnitType textSizeUnit = annotation.textSizeUnit();
                sizeUnit = new SizeUnitBean(widthUnit, heightUnit, textSizeUnit);

            }
            //自适应
            if (autoLayout) {
                initSizeView(context, layoutResID, contentView, changeSizeTYpe, sizeUnit);
            }
        }
    }


    /**
     * @param context          上下文对象
     * @param layoutResID      内容View的布局id
     * @param isChangeSizeType 是否改变尺寸的单位,不改变默认使用Application中定义的
     * @param sizeUnit         尺寸单位的bean
     * @param contentView      需要适配的布局view
     */
    public void initSizeView(Context context, int layoutResID, ViewGroup contentView, boolean isChangeSizeType, SizeUnitBean sizeUnit) {
        if (isChangeSizeType) {
            SizeUtil.getInstance().setActivitySizeUnit(sizeUnit);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //小于android6.0的情况
            forEachViewBelowSDKM(contentView, layoutResID, context);
        } else {
            //大于android6.0的情况
            forEachViewAboveSDKM(contentView);
        }
        if (isChangeSizeType) {
            SizeUtil.getInstance().resetActivitySizeUnit();
        }
    }

    /**
     * 该方法默认不改变尺寸单位
     *
     * @param context     上下文对象
     * @param layoutResID 内容View的布局id
     * @param contentView 需要适配的布局ViewGroup,
     */
    public void initSizeView(Context context, int layoutResID, ViewGroup contentView) {
        initSizeView(context, layoutResID, contentView, false, null);
    }


    /***
     *大于++6.0的情况，直接遍历设置contentView
     * */
    private void forEachViewAboveSDKM(ViewGroup rootView) {
        Map<String, String> rootAttrMap = AttributeUtil.getInstance().getAttributeMaps(rootView);
        LayoutSizeUtil.getInstance().setViewSize(rootView, rootAttrMap);
        int childCount = rootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = rootView.getChildAt(i);
            if (view instanceof ViewGroup) {
                forEachViewAboveSDKM((ViewGroup) view);
            } else if (view instanceof View) {
                Map<String, String> maps = AttributeUtil.getInstance().getAttributeMaps(view);
                LayoutSizeUtil.getInstance().setViewSize(view, maps);
            }
        }
    }


    /***
     *小于6.0的情况，解析xml
     *
     * */

    private void forEachViewBelowSDKM(ViewGroup contentView, int layoutResID, Context context) {
        ViewBean rootViewBean = XmlParseUtil.getInstance().convertXmlToBean(layoutResID, context);
        relevancyViewBeanWidthContentView(rootViewBean, contentView);
        forEachViewBean(rootViewBean);
    }

    /**
     * 遍历ViewBean且改变大小
     **/
    private void forEachViewBean(ViewBean rootViewBean) {
        LayoutSizeUtil.getInstance().setViewSize(rootViewBean.getView(), rootViewBean.getAttributeMap());
        int size = rootViewBean.getChildList().size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ViewBean bean = rootViewBean.getChildList().get(i);
                if (bean.getChildList() != null && bean.getChildList().size() > 0) {
                    //又是一个父容器
                    forEachViewBean(bean);
                } else {
                    LayoutSizeUtil.getInstance().setViewSize(bean.getView(), bean.getAttributeMap());
                }
            }
        }
    }

    /**
     * 将View关联至bean里面
     */
    private void relevancyViewBeanWidthContentView(ViewBean rootViewBean, ViewGroup contentView) {
        rootViewBean.setView(contentView);
        int childCount = contentView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                ViewBean bean = rootViewBean.getChildList().get(i);
                View view = contentView.getChildAt(i);
                if (view instanceof ViewGroup) {
                    relevancyViewBeanWidthContentView(bean, (ViewGroup) view);
                } else {
                    bean.setView(view);
                }
            }
        }

    }


}
