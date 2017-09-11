package com.example.administrator.autolayoutapplication;

import android.app.Application;

import com.hx.autolayout.LayoutSizeUtil;
import com.hx.autolayout.bean.SizeUnitBean;
import com.hx.autolayout.constant.SizeUnitType;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LayoutSizeUtil.getInstance().initConfig(1080,1920,480, 800, SizeUnitBean.getDefaultPx(), this);
    }
}
