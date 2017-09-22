package com.wang.xyhua.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.wang.xyhua.common.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * Created by dell on 2017/9/19.
 */

public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//防止输入法键盘遮挡输入框
//        StatusBarUtils.statusBarLightMode(this);//由于本APP UI 设计状态栏为白底，许多系统是白底白字无法看清，故增加任务栏白底黑字适配
        setContentView(getLayoutId());
        //ButterKnife 初始化
        ButterKnife.bind(this);

        initViews();
    }
    //返回布局
    protected abstract int getLayoutId();

    protected void initViews(){};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
