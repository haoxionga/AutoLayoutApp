package com.hx.autolayout;

import com.hx.autolayout.constant.SizeUnitType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLayout {

    /**
     * 是否自动适配
     */
    boolean isAutoLayout() default true;

    /**
     * 是否改变当前页面尺寸的单位,不改变使用默认的
     */
    boolean isChangeSizeType() default false;

    /**
     * 宽度单位,界面布局有可能使用dp，也有可能使用px
     */
    SizeUnitType widthUnit() default SizeUnitType.DP;

    /**
     * 高度单位,界面布局有可能使用dp，也有可能使用px
     */
    SizeUnitType heightUnit() default SizeUnitType.DP;

    /**
     * 字号单位,界面布局有可能使用dp，也有可能使用px，
     */
    SizeUnitType textSizeUnit() default SizeUnitType.DP;
}
