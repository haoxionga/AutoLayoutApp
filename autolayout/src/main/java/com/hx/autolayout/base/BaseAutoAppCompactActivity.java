package com.hx.autolayout.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.hx.autolayout.util.IterationContentViewUtil;


public class BaseAutoAppCompactActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        IterationContentViewUtil.getInstance().initActivitySize(this,layoutResID);
    }
}
