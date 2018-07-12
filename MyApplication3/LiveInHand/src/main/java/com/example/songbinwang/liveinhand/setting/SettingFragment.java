package com.example.songbinwang.liveinhand.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.login2register.BaseFragment;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout ll_account_safe, ll_payment, ll_systemsetting, ll_update, ll_aboutus;

    @Override
    protected int getLayoutId() {
        return R.layout.setting_fg_main;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ll_account_safe = (LinearLayout) view.findViewById(R.id.ll_account_safe);
        ll_payment = (LinearLayout) view.findViewById(R.id.ll_payment);
        ll_systemsetting = (LinearLayout) view.findViewById(R.id.ll_system_setting);
        ll_update = (LinearLayout) view.findViewById(R.id.ll_checkupdate);
        ll_aboutus = (LinearLayout) view.findViewById(R.id.ll_aboutus);

        ll_account_safe.setOnClickListener(this);
        ll_payment.setOnClickListener(this);
        ll_systemsetting.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        ll_aboutus.setOnClickListener(this);
    }

    public static SettingFragment newInstance(Bundle bundle){
        SettingFragment settingFragment = new SettingFragment();
        settingFragment.setArguments(bundle);
        return settingFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_account_safe:
                break;
            case R.id.ll_payment:
                break;
            case R.id.ll_system_setting:
                break;
            case R.id.ll_checkupdate:
                break;
            case R.id.ll_aboutus:
                break;
        }
    }
}
