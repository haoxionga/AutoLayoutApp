package com.example.administrator.autolayoutapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.autolayoutapplication.R;
import com.hx.autolayout.AutoLayout;
import com.hx.autolayout.base.BaseAutoV4Fragment;
import com.hx.autolayout.util.IterationContentViewUtil;

@AutoLayout

public class MyFragment extends BaseAutoV4Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.activity_main, null);
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


}
