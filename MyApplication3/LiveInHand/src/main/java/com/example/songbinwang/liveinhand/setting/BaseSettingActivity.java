package com.example.songbinwang.liveinhand.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.login2register.BaseActivity;
import com.example.songbinwang.liveinhand.login2register.BaseFragment;

/**
 * Created by songbinwang on 2016/6/27.
 */
public abstract class BaseSettingActivity extends BaseActivity{

    private ImageView iv_back;
    private TextView tv_title;

    protected abstract void handleIntent(Intent intent);

    protected abstract BaseFragment getFirstFragment();

    @Override
    protected int getContentViewId() {
        return R.layout.setting_base;
    }

    @Override
    protected int getFragmentViewId() {
        return R.id.fl_fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if(getIntent() != null){
            handleIntent(getIntent());
        }
        if (getSupportFragmentManager() != null) {
            BaseFragment firstFragment = getFirstFragment();
            if (firstFragment != null) {
                addFragment(firstFragment);
            }
        }

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               removeFragment();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    protected void setTitle(String title){
        tv_title.setText(title);
    }

}
