package com.hx.autolayout.base;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;

import com.hx.autolayout.util.IterationContentViewUtil;


public class BaseAutoFragmentActivity extends FragmentActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        IterationContentViewUtil.getInstance().initActivitySize(this,layoutResID);
    }
}
