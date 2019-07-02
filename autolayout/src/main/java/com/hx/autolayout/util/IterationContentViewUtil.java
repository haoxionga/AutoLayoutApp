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
import java.util.List;
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
        AutoLayout annotation = null;
        try {
            //先获取类上的注解
            annotation = fragment.getClass().getAnnotation(AutoLayout.class);
            if (annotation == null) {
                //获取方法上的注解
                Method method = fragment.getClass().getMethod("onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class);
                annotation = method.getAnnotation(AutoLayout.class);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (annotation == null || annotation.isAutoLayout()) {
                initSize(fragment.getContext(), layoutId, contentView, annotation);
            }
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
        AutoLayout annotation = null;
        try {
            //先获取类上的注解
            annotation = activity.getClass().getAnnotation(AutoLayout.class);
            if (annotation == null) {
                //获取方法上的注解
                Method method = activity.getClass().getDeclaredMethod("onCreate", Bundle.class);
                annotation = method.getAnnotation(AutoLayout.class);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (annotation == null || annotation.isAutoLayout()) {
                initSize(activity, layoutResID, contentView, annotation);
            }
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
        AutoLayout annotation = null;
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
        } finally {
            if (annotation == null || annotation.isAutoLayout()) {
                initSize(fragment.getActivity(), layoutId, contentView, annotation);
            }
        }

    }

    private void initSize(Context context, int layoutResID, ViewGroup contentView, AutoLayout annotation) {
        SizeUnitBean sizeUnit = null;
        boolean changeSizeTYpe = false;
        if (annotation != null) {
            boolean autoLayout = annotation.isAutoLayout();
            changeSizeTYpe = annotation.isChangeSizeType();

            if (changeSizeTYpe) {
                SizeUnitType heightUnit = annotation.heightUnit();
                SizeUnitType widthUnit = annotation.widthUnit();
                SizeUnitType textSizeUnit = annotation.textSizeUnit();
                sizeUnit = new SizeUnitBean(widthUnit, heightUnit, textSizeUnit);
            }

        }

        initSizeView(context, layoutResID, contentView, changeSizeTYpe, sizeUnit);
    }


    /**
     * @param context          上下文对象
     * @param layoutResID      内容View的布局id
     * @param isChangeSizeType 是否改变尺寸的单位,不改变默认使用Application中定义的
     * @param sizeUnit         尺寸单位的bean
     * @param contentView      需要适配的布局view
     */
    public void initSizeView(Context context, int layoutResID, ViewGroup contentView, boolean isChangeSizeType, SizeUnitBean sizeUnit) {
        if (isChangeSizeType && sizeUnit != null) {
            SizeUtil.getInstance().setActivitySizeUnit(sizeUnit);
        }
         if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&&Build.VERSION.SDK_INT<=Build.VERSION_CODES.O){
            //android6.0和7.0使用该方法
            forEachViewAboveSDKM(contentView);
        }else {
            forEachViewBelowSDKM(contentView, layoutResID, context);
        }
        //使用完当前接界面的尺寸将其重置
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

    /**
     * 该方法默认不改变尺寸单位
     *
     * @param context     上下文对象
     * @param layoutResID 内容View的布局id
     * @param contentView 需要适配的布局View
     */
    public void initSizeView(Context context, int layoutResID, View contentView) {
        if (contentView instanceof ViewGroup) {
            initSizeView(context, layoutResID, (ViewGroup) contentView, false, null);

        }
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
        List<ViewBean> childList = rootViewBean.getChildList();
        //存在子view的情况
        if (childList != null) {
            if (childList.size() > 0 && contentView instanceof ViewGroup && contentView.getChildCount() >= childList.size()) {
                for (int i = 0; i < childList.size(); i++) {
                    ViewBean viewBean = childList.get(i);
                    View childView = contentView.getChildAt(i);
                    if ((childView instanceof ViewGroup) && viewBean.getChildList() != null && viewBean.getChildList().size() > 0) {
                        relevancyViewBeanWidthContentView(viewBean, (ViewGroup) childView);
                    } else {
                        viewBean.setView(childView);
                    }
                }
            }
        }
    }


}
