package com.example.administrator.autolayoutapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.autolayoutapplication.R;
import com.hx.autolayout.AutoLayout;
import com.hx.autolayout.LayoutSizeUtil;
import com.hx.autolayout.base.BaseAutoAppCompactActivity;
import com.hx.autolayout.constant.SizeUnitType;
import com.hx.autolayout.util.LayoutUtil;

public class MainActivity extends BaseAutoAppCompactActivity {


    private TextView tvTitle;

    @AutoLayout(isAutoLayout = true,isChangeSizeType = true,widthUnit = SizeUnitType.PX,heightUnit = SizeUnitType.PX,textSizeUnit = SizeUnitType.PX)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        float textSize = tvTitle.getTextSize();

        getAndroiodScreenProperty();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        Log.d("MainActivitys", "tvTitle.getLayoutParams().width:" + tvTitle.getLayoutParams().width);
        Log.d("MainActivitys", "tvTitle.getLayoutParams().width:" + tvTitle.getLayoutParams().height);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        tvTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.d("MainActivitys", "tvTitle.getWidth():" + tvTitle.getWidth());
//                Log.d("MainActivitys", "tvTitle.getWidth():" + tvTitle.getTextSize());
//
//            }
//        });

    }

    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        Log.d("Mainsivity", "density:" + density);
        Log.d("Mainsivity", "screenWidth:" + screenWidth);
        Log.d("Mainsivity", "screenHeight:" + screenHeight);
    }


}