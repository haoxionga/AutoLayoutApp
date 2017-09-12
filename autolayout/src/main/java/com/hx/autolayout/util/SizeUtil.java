package com.hx.autolayout.util;

import com.hx.autolayout.ReckonSizeListener;
import com.hx.autolayout.bean.SizeUnitBean;
import com.hx.autolayout.constant.SizeUnitType;

/**
 * Created by Shinelon on 2017/9/10.
 */

public class SizeUtil {
    private static SizeUtil instance;

    public static SizeUtil getInstance() {
        if (instance == null) {
            synchronized (SizeUtil.class) {
                if (instance == null) {
                    instance = new SizeUtil();
                }
            }
        }
        return instance;
    }

    private double currentWidth;
    private double currentContentHeight;

    private double baseWith, baseContentHeight;
    private double baseWithDp, baseContentHeightDp;
    private int currentWidthDp, currentContentHeightDp;

    private float density = 0.0f;

    public void init(double baseWith, double baseHeight, double baseWithDp, double baseContentHeightDp, double currentWidth, double currentHeight, int screenWidthDp, int screenHeightDp, float density) {
        this.density = density;
        this.baseWith = baseWith;
        this.baseContentHeight = baseHeight;
        this.baseWithDp = baseWithDp;
        this.baseContentHeightDp = baseContentHeightDp;
        this.currentWidth = currentWidth;

        this.currentContentHeight = currentHeight;
        this.currentWidthDp = screenWidthDp;
        this.currentContentHeightDp = screenHeightDp;

    }

    /**
     * 包含了app尺寸单位的bean
     */
    private SizeUnitBean appSizeUnit;

    public void setAppSizeUnitBean(SizeUnitBean appSizeUnit) {
        this.appSizeUnit = appSizeUnit;
    }

    private SizeUnitBean actSizeUnit;

    /**
     * 设置针对一个界面的尺寸类型
     *
     * @param sizeUnit 尺寸bean
     **/
    public void setActivitySizeUnit(SizeUnitBean sizeUnit) {
        actSizeUnit = sizeUnit;
    }

    /**
     * 重置界面的尺寸，置为空
     **/
    public void resetActivitySizeUnit() {
        actSizeUnit = null;
    }

    private ReckonSizeListener listener;

    /**
     * 设置计算尺寸的接口
     *
     * @param listener 计算尺寸的接口
     */
    public void setListener(ReckonSizeListener listener) {
        this.listener = listener;
    }

    /**
     * 获取x轴的比例
     *
     * @param value 原始值
     * @return 通过屏幕比例计算得到的新值
     */
    public double getScaleX(double value) {
        double newValue = 0.0;
        SizeUnitType widthUnit = getSizeUnit().getWidthUnit();
        if (widthUnit == SizeUnitType.PX) {
            newValue = (currentWidth / baseWith) * value;
        } else if (widthUnit == SizeUnitType.DP) {
            //当尺寸是dp的时候
            newValue = (float) dip2px((float) ((currentWidthDp / baseWithDp) * (value))) / density;
        }
        if (listener != null) {
            double v = listener.reckonWidth(value, baseWith, baseWithDp, currentWidth, currentWidthDp, widthUnit);
            if (v != 0) {
                newValue = v;
            }
        }
        return newValue;
    }

    /**
     * 获取y轴的比例
     *
     * @param value 原始值
     * @return 通过比例计算得到的新值
     */
    public double getScaleY(double value) {
        double newValue = 0.0;
        SizeUnitType heightUnit = getSizeUnit().getHeightUnit();
        if (heightUnit == SizeUnitType.PX) {
            //px的时候
            newValue = (currentContentHeight / baseContentHeight) * value;
        } else if (heightUnit == SizeUnitType.DP) {
            //当尺寸是dp的时候
            newValue = (float) dip2px((float) ((currentContentHeightDp / baseContentHeightDp) * (value))) / density;
        }
        if (listener != null) {
            double v = listener.reckonHeight(value, baseContentHeight, baseContentHeightDp, currentContentHeight, currentContentHeightDp, getSizeUnit().getHeightUnit());
            if (v != 0) {
                newValue = v;
            }
        }
        return newValue;
    }

    /***
     * 获取字号的比例
     * @param value 原始值
     * @return 通过比例计算得到的新值
     *
     * */
    public double getScaleTextSize(double value) {
        double newValue = 0.0;
        SizeUnitType textSizeUnit = getSizeUnit().getTextSizeUnit();
        if (textSizeUnit == SizeUnitType.PX) {
            newValue = (currentWidth / baseWith) * value;
        } else {
            newValue = (float) ((currentWidthDp / baseWithDp) * (value));
        }
        if (listener != null) {
            double v = listener.reckonTextSize(value, baseWith, baseWithDp, currentWidth, currentWidthDp, baseContentHeight, baseContentHeightDp, currentContentHeight, currentContentHeightDp, textSizeUnit);
            if (v != 0) {
                newValue = v;
            }
        }


        return Math.round(newValue / density);
    }


    /**
     * dp转成px
     *
     * @param dipValue
     * @return
     */
    private double dip2px(float dipValue) {
        return dipValue * density + 0.5f;
    }

    private SizeUnitBean getSizeUnit() {
        if (actSizeUnit != null) {
            return actSizeUnit;
        }
        return appSizeUnit;
    }


}
