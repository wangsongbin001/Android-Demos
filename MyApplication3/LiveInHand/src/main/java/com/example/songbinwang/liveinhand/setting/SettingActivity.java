package com.example.songbinwang.liveinhand.setting;

import android.content.Intent;

import com.example.songbinwang.liveinhand.login2register.BaseFragment;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class SettingActivity extends BaseSettingActivity{

    @Override
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected BaseFragment getFirstFragment() {
        return SettingFragment.newInstance(null);
    }
}
