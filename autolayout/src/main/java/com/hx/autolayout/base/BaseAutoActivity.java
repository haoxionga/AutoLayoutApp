package com.hx.autolayout.base;

import android.app.Activity;
import android.support.annotation.LayoutRes;

import com.hx.autolayout.util.IterationContentViewUtil;


public class BaseAutoActivity extends Activity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        IterationContentViewUtil.getInstance().initActivitySize(this,layoutResID);
    }
}
