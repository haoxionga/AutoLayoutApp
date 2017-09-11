package com.example.administrator.autolayoutapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.autolayoutapplication.R;
import com.example.administrator.autolayoutapplication.fragment.MyFragment;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,new MyFragment()).commit();
    }
}
