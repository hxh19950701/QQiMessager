package com.hxh19950701.qqimessager.base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by hxh19950701 on 2016/3/30.
 */
public abstract class BaseDialog extends AlertDialog implements View.OnClickListener {

    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View view);

    protected BaseDialog(Context context) {
        super(context);
    }

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
