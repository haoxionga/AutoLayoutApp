package com.example.administrator.autolayoutapplication.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

import com.example.administrator.autolayoutapplication.R;

/**
 * Created by Shinelon on 2017/9/9.
 */

public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getResources().obtainAttributes(attrs, R.styleable.View);
        init(attrs,a);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getResources().obtainAttributes(attrs,R.styleable.View);
        init(attrs,a);
    }


    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.getResources().obtainAttributes(attrs,R.styleable.View);
        init(attrs,a);
    }

    private void init(@Nullable AttributeSet attrs, TypedArray a) {
//        for (int i = 0; i < attrs.getAttributeCount(); i++) {
//            Log.d("LayoutSizeUtils", attrs.getAttributeName(i) + "-----" + attrs.getAttributeValue(i));
//        }
    }

    private static SparseArray<String> mAttributeMap;

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray<String>();
        }
        return mAttributeMap;
    }

    private String[] mAttributes;

    private void saveAttributeData(AttributeSet attrs, TypedArray a) {
        int length = ((attrs == null ? 0 : attrs.getAttributeCount()) + a.getIndexCount()) * 2;
        mAttributes = new String[length];

        int i = 0;
        if (attrs != null) {
            for (i = 0; i < attrs.getAttributeCount(); i += 2) {
                mAttributes[i] = attrs.getAttributeName(i);
                mAttributes[i + 1] = attrs.getAttributeValue(i);
            }

        }

        SparseArray<String> attributeMap = getAttributeMap();
        for (int j = 0; j < a.length(); ++j) {
            if (a.hasValue(j)) {
                try {
                    int resourceId = a.getResourceId(j, 0);
                    if (resourceId == 0) {
                        continue;
                    }

                    String resourceName = attributeMap.get(resourceId);
                    if (resourceName == null) {
                        resourceName = a.getResources().getResourceName(resourceId);
                        attributeMap.put(resourceId, resourceName);
                    }

                    mAttributes[i] = resourceName;
                    mAttributes[i + 1] = a.getText(j).toString();
                    i += 2;
                } catch (Resources.NotFoundException e) {
                    // if we can't get the resource name, we just ignore it
                }
            }
        }
    }
}
