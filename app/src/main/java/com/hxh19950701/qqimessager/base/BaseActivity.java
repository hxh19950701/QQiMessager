package com.hxh19950701.qqimessager.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{

    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
        initData();
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }
}
