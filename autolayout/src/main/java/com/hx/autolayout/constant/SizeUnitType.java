package com.hx.autolayout.constant;

/**
 * 定义尺寸的单位
 */

public enum SizeUnitType {

    PX("px"), DP("dp"), SP("sp");

    private String strStart;

    SizeUnitType(String strStart) {
        this.strStart = strStart;
    }

    public String getStrStart() {
        return strStart;
    }

    public void setStrStart(String strStart) {
        this.strStart = strStart;
    }
}
