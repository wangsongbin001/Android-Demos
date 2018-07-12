package com.example.songbinwang.liveinhand.login2register;

import android.content.Intent;
import android.os.Bundle;

import com.example.songbinwang.liveinhand.R;

/**
 * Created by songbinwang on 2016/6/16.
 */
public abstract class AppActivity extends BaseActivity{

    protected abstract BaseFragment getFirstFragment();

    protected void handleIntent(Intent intent){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentViewId());
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
        if (getSupportFragmentManager().getFragments() == null) {
            BaseFragment firstFragment = getFirstFragment();
            if(firstFragment != null){
                addFragment(firstFragment);
            }
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.layout_login2register;
    }

    @Override
    protected int getFragmentViewId() {
        return R.id.fragment_container;
    }
}
