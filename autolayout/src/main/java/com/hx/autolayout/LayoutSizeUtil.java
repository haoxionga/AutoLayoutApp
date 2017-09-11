package com.hx.autolayout;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.hx.autolayout.bean.SizeUnitBean;
import com.hx.autolayout.constant.DimenNameStart;
import com.hx.autolayout.constant.SizeUnitType;
import com.hx.autolayout.util.LayoutUtil;
import com.hx.autolayout.util.SizeUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class LayoutSizeUtil {
    private static LayoutSizeUtil singleton = null;


    public static LayoutSizeUtil getInstance() {
        if (singleton == null) {
            synchronized (LayoutSizeUtil.class) {
                if (singleton == null) {
                    singleton = new LayoutSizeUtil();
                }
            }
        }
        return singleton;
    }

    private Resources resources;


    /**
     * @param sizeUnitBean      尺寸单位bean，为空的情况，默认定义单位为dp
     * @param baseWith          基准宽度，px尺寸
     * @param baseContentHeight 基准内容高度px尺寸，内容宽度 不包括状态栏！！！！
     * @param baseWidthDp       基准宽度dp ,dp尺寸
     * @param baseContentHeightDp      基准内容高度dp，dp尺寸
     * @param application       上下文
     * @param listener          计算尺寸的接口
     */
    public void initConfig(double baseWith, double baseContentHeight, double baseWidthDp, double baseContentHeightDp, Application application, SizeUnitBean sizeUnitBean, ReckonSizeListener listener) {
        SizeUnitBean appSizeUnit;
        if (sizeUnitBean != null) {
            appSizeUnit = sizeUnitBean;
        } else {
            appSizeUnit = SizeUnitBean.getDefaultDp();
        }
        SizeUtil.getInstance().setAppSizeUnitBean(appSizeUnit);
        SizeUtil.getInstance().setListener(listener);
        initConfig(baseWith, baseContentHeight, baseWidthDp, baseContentHeightDp, application);
    }

    /**
     * @param sizeUnitBean      尺寸单位bean，为空的情况，默认定义单位为dp
     * @param baseWith          基准宽度，px尺寸
     * @param baseContentHeight 基准内容高度px尺寸，内容宽度 不包括状态栏！！！！
     * @param baseWidthDp       基准宽度dp ,dp尺寸
     * @param baseContentHeightDp      基准内容高度dp，dp尺寸
     * @param application       上下文
     */
    public void initConfig(double baseWith, double baseContentHeight, double baseWidthDp, double baseContentHeightDp, SizeUnitBean sizeUnitBean, Application application) {
        SizeUnitBean appSizeUnit;
        if (sizeUnitBean != null) {
            appSizeUnit = sizeUnitBean;
        } else {
            appSizeUnit = SizeUnitBean.getDefaultDp();
        }
        SizeUtil.getInstance().setAppSizeUnitBean(appSizeUnit);
        initConfig(baseWith, baseContentHeight, baseWidthDp, baseContentHeightDp, application);

    }

    private void initConfig(double baseWith, double baseContentHeight, double baseWithDp, double baseContentHeightDp, Application application) {
        resources = application.getResources();

        WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        double currentWidth = dm.widthPixels;         // 屏幕宽度（像素）
        double currentHeight = dm.heightPixels;       // 屏幕高度（像素）

        float density = dm.density;
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidthDp = (int) (currentWidth / density);  // 屏幕宽度(dp)
        int screenContentHeightDp = (int) (currentHeight / density);// 屏幕高度(dp)

        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        float statusBarHeight = resources.getDimension(resourceId);
        currentHeight -= statusBarHeight;
        screenContentHeightDp -= (statusBarHeight / density);

        SizeUtil.getInstance().init(baseWith, baseContentHeight,baseWithDp,baseContentHeightDp, currentWidth, currentHeight, screenWidthDp, screenContentHeightDp, density);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            LayoutUtil.getInstance().init(application);
        } else {
            try {
                Field field1 = View.class.getField("mDebugViewAttributes");
                field1.setAccessible(true);
                field1.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Resources getResources() {
        return resources;
    }

    /**
     * @param view 要是配的VIew
     * @param map  要适配的View的属性集合
     */
    public void setViewSize(View view, Map<String, String> map) {





        Log.d("LayoutSizeUtilsss", map.toString());

        //获取当前宽度值
        String width = "layout_width";
        if (map.containsKey(width) && !"-2".equals(map.get(width)) && !"-1".equals(map.get(width))) {
            if (map.get(width).startsWith("@")) {
                double value = getCurrentValue(map.get(width));
                Log.d("LayoutSizeUtilsss", "value:" + value);
                view.getLayoutParams().width = (int) value ;
            }
        }
        //获取当前的高度值
        String height = "layout_height";
        if (map.containsKey(height) && !"-2".equals(map.get(height)) && !"-1".equals(map.get(height))) {
            if (map.get(height).startsWith("@")) {
                double value = getCurrentValue(map.get(height));
                view.getLayoutParams().height = (int) value;
            }
        }
        /**
         * 设置字号
         * */
        if (view instanceof TextView) {
            String textSize = "textSize";
            if (map.containsKey(textSize)) {
                if (map.get(textSize).startsWith("@")) {
                    int value = (int) getCurrentValue(map.get(textSize));
                    Log.d("LayoutSizeUtils", "textsize:    " + value + "");
                    ((TextView) view).setTextSize(value);
                }
            }
        }


        if (resources == null) {
            throw new ExceptionInInitializerError("LayoutSizeUitil尚未初始化,请在您项目Application的onCreate处调用");
        }
        //内边距，
        int paddingLeft = 0, paddingRight = 0,
                paddingTop = 0, paddingBottom = 0;
        //获取内边距
        String padding = "padding";
        if (map.containsKey(padding)) {
            if (map.get(padding).startsWith("@")) {
                int value = (int) getCurrentValue(map.get(padding));
                view.setPadding(value, value, value, value);
            }

        } else {

            String strPaddingLeft = "paddingLeft";
            if (map.containsKey(strPaddingLeft)) {
                if (map.get(strPaddingLeft).startsWith("@")) {
                    paddingLeft = (int) getCurrentValue(map.get(strPaddingLeft));
                }
            }
            String strPaddingRight = "paddingRight";
            if (map.containsKey(strPaddingRight)) {
                if (map.get(strPaddingRight).startsWith("@")) {
                    paddingRight = (int) getCurrentValue(map.get(strPaddingRight));
                }
            }
            String strPaddingTop = "paddingTop";
            if (map.containsKey(strPaddingTop)) {
                if (map.get(strPaddingTop).startsWith("@")) {
                    paddingTop = (int) getCurrentValue(map.get(strPaddingTop));
                }
            }
            String strPaddingBottom = "paddingBottom";
            if (map.containsKey(strPaddingBottom)) {
                if (map.get(strPaddingBottom).startsWith("@")) {
                    paddingBottom = (int) getCurrentValue(map.get(strPaddingBottom));
                }
            }
            if (paddingBottom != 0 || paddingTop != 0 || paddingRight != 0 || paddingLeft != 0) {
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            }
        }
        //外边距
        int layout_marginLeft = 0, layout_marginRight = 0,
                layout_marginTop = 0, layout_marginBottom = 0;

        //获取外边距
        String layout_margin = "layout_margin";
        if (map.containsKey(layout_margin)) {

            if (map.get(layout_margin).startsWith("@")) {
                int value = (int) getCurrentValue(map.get(layout_margin));
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
                    marginLayoutParams.setMargins(value, value, value, value);
                    view.setLayoutParams(marginLayoutParams);
                }
            }


        } else {

            String strlayout_marginLeft = "layout_marginLeft";
            if (map.containsKey(strlayout_marginLeft)) {
                if (map.get(strlayout_marginLeft).startsWith("@")) {
                    layout_marginLeft = (int) getCurrentValue(map.get(strlayout_marginLeft));
                }
            }
            String strlayout_marginRight = "layout_marginRight";
            if (map.containsKey(strlayout_marginRight)) {
                if (map.get(strlayout_marginRight).startsWith("@")) {
                    layout_marginRight = (int) getCurrentValue(map.get(strlayout_marginRight));
                }
            }
            String strlayout_marginTop = "layout_marginTop";
            if (map.containsKey(strlayout_marginTop)) {
                if (map.get(strlayout_marginTop).startsWith("@")) {
                    layout_marginTop = (int) getCurrentValue(map.get(strlayout_marginTop));
                }
            }
            String strlayout_marginBottom = "layout_marginBottom";
            if (map.containsKey(strlayout_marginBottom)) {
                if (map.get(strlayout_marginBottom).startsWith("@")) {
                    layout_marginBottom = (int) getCurrentValue(map.get(strlayout_marginBottom));
                }
            }
            if (layout_marginBottom != 0 || layout_marginTop != 0 || layout_marginRight != 0 || layout_marginLeft != 0) {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) params;
                    marginLayoutParams.setMargins(layout_marginLeft, layout_marginTop, layout_marginRight, layout_marginBottom);
                    view.setLayoutParams(marginLayoutParams);
                }
            }
        }


    }

    private double getCurrentValue(String strId) {
        if (strId == null || strId.length() < 1) {
            return 0;
        }
        Log.d("LayoussizeUtil", "--------------:");

        int id = Integer.parseInt(strId.replace("@", "").trim());
        String entryName = resources.getResourceEntryName(id);
        Log.d("LayoussizeUtil", "entryName:" + entryName);

        String typeName = resources.getResourceTypeName(id);
        double newValue = resources.getDimension(id);
        Log.d("LayoussizeUtil", "newValue:" + newValue);
        if (entryName.startsWith(DimenNameStart.X.getStrStart())) {
            //以屏幕宽度比例计算新的长度

            newValue = SizeUtil.getInstance().getScaleX(newValue);
        } else if (entryName.startsWith(DimenNameStart.Y.getStrStart())) {
            //以屏幕高度比例计算新的长度
            newValue = SizeUtil.getInstance().getScaleY(newValue);
        } else if (entryName.startsWith(DimenNameStart.T.getStrStart())) {
            //以屏幕宽度比例计算新的长度
            newValue = SizeUtil.getInstance().getScaleTextSize(newValue);
        }

        //四舍五入，取整
        newValue = Math.round(newValue);
        Log.d("LayoussizeUtil", "newValue:" + newValue);

        return newValue;
    }


}
