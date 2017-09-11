package com.hx.autolayout.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.autolayout.util.IterationContentViewUtil;


public abstract class BaseAutoV4Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IterationContentViewUtil.getInstance().initNativeFragmentSize(this, getLayoutId());
    }

    /**
     * 返回fragment布局文件id
     **/
    protected abstract int getLayoutId();

}
