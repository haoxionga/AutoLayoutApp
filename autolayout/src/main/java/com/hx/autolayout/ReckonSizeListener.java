package com.hx.autolayout;

import com.hx.autolayout.constant.SizeUnitType;

/**
 * 计算尺寸
 */

public interface ReckonSizeListener {
    /**
     * 计算宽度比例
     *
     * @param width        具体的尺寸值，返回在dimens中定义的尺寸
     * @param baseWidth    基准宽度,返回init中传入的值
     * @param currentWidth 当前屏幕宽度定义的单位
     * @param unitType     尺寸单位，
     */
    double reckonWidth(double width, double baseWidth, double baseWithDp, double currentWidth, double currentWidthDp, SizeUnitType unitType);

    double reckonHeight(double height, double baseHeight, double baseContentHeightDp, double currentHeight, double currentHeightDp, SizeUnitType unitType);

    double reckonTextSize(double textSize, double baseWidth, double baseWithDp, double currentWidth, double currentWidthDp, double baseHeight, double baseContentHeightDp, double currentHeight, double currentHeightDp, SizeUnitType unitType);
}
