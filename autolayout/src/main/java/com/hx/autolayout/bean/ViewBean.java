package com.hx.autolayout.bean;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ViewBean
 */
public class ViewBean {
    private View view;
    private Map<String, String> attributeMap;
    private List<ViewBean> childList;
    private String name;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public List<ViewBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ViewBean> childList) {
        this.childList = childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildView(ViewBean viewBean) {
        if (childList == null) {
            childList = new ArrayList<ViewBean>();
        }
        childList.add(viewBean);
    }

//    @Override
//    public String toString() {
//        return "name:"+name+"\n\r"+"attributeMap:"+attributeMap.toString()+"\n\r"+"childList:"+ childList.toString();
//    }
}
