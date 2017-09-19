package com.wang.xyhua.work.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.wang.xyhua.common.base.BaseActivity;

/**
 * Created by dell on 2017/9/19.
 */

public abstract class BaseLaunchActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
