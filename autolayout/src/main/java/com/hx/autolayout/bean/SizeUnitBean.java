package com.hx.autolayout.bean;

import com.hx.autolayout.constant.SizeUnitType;

/**
 * 存储横向纵向，字号尺寸单位的bean
 */

public class SizeUnitBean {


    /**
     * 宽度单位
     */
    private SizeUnitType widthUnit;

    /**
     * 高度单位
     */
    private SizeUnitType heightUnit;

    /**
     * 字号单位
     */
    private SizeUnitType textSizeUnit;

    public SizeUnitBean(SizeUnitType widthUnit, SizeUnitType heightUnit, SizeUnitType textSizeUnit) {
        this.widthUnit = widthUnit;
        this.heightUnit = heightUnit;
        this.textSizeUnit = textSizeUnit;
    }

    public static SizeUnitBean getDefaultPx() {
        return new SizeUnitBean(SizeUnitType.PX, SizeUnitType.PX, SizeUnitType.PX);
    }

    public static SizeUnitBean getDefaultDp() {
        return new SizeUnitBean(SizeUnitType.DP, SizeUnitType.DP, SizeUnitType.DP);
    }

    public SizeUnitType getWidthUnit() {
        return widthUnit;
    }

    public SizeUnitBean setWidthUnit(SizeUnitType widthUnit) {
        this.widthUnit = widthUnit;
        return this;
    }

    public SizeUnitType getHeightUnit() {
        return heightUnit;
    }

    public SizeUnitBean setHeightUnit(SizeUnitType heightUnit) {
        this.heightUnit = heightUnit;
        return this;
    }

    public SizeUnitType getTextSizeUnit() {
        return textSizeUnit;
    }

    public SizeUnitBean setTextSizeUnit(SizeUnitType textSizeUnit) {
        this.textSizeUnit = textSizeUnit;
        return this;
    }
}
