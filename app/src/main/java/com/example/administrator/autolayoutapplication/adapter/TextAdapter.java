package com.example.administrator.autolayoutapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.autolayoutapplication.R;
import com.example.administrator.autolayoutapplication.activity.RecyclerViewActivity;
import com.hx.autolayout.util.IterationContentViewUtil;


public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextHolder> {


    private Context context;

    public TextAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextHolder holder = new TextHolder(LayoutInflater.from(context).inflate(R.layout.holder_item_text, parent, false));
        IterationContentViewUtil.getInstance().initSizeView(context, R.layout.holder_item_text, (ViewGroup) holder.itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(TextHolder holder, int position) {
        holder.textView.setText("----" + position + "----");
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class TextHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public TextHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
//            Log.d("TextHolders", "---------------");
//            forEachView(itemView);
        }

        private void forEachView(View itemView) {
            Log.d("TextHolders", itemView.getClass().getSimpleName());
            if (itemView instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) itemView;
                if (group.getChildCount() > 0) {
                    for (int i = 0; i < group.getChildCount(); i++) {
                        forEachView(group.getChildAt(i));
                    }
                }
            }
        }
    }
}
