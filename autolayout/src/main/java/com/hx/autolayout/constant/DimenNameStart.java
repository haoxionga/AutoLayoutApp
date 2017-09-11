package com.hx.autolayout.constant;

/**
 * 定义尺寸的单位
 */

public enum DimenNameStart {

    X("x_"), Y("y_"), T("t_");

    private String strStart;

    DimenNameStart(String strStart) {
        this.strStart = strStart;
    }

    public String getStrStart() {
        return strStart;
    }

    public void setStrStart(String strStart) {
        this.strStart = strStart;
    }
}
