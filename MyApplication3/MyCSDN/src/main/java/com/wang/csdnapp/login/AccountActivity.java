package com.wang.csdnapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wang.csdnapp.Constant;
import com.wang.csdnapp.R;

import cn.bmob.v3.Bmob;

/**
 * Created by SongbinWang on 2017/2/28.
 */

public class AccountActivity extends BaseActivity{

    @Override
    public int getContentViewID() {
        return R.layout.activity_account;
    }

    @Override
    public int getFragmentContentID() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());

        //初始化BMOB
        Bmob.initialize(this, Constant.BMOB_APP_ID);
        //处理intent
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
        if(null == getSupportFragmentManager().getFragments()){
            BaseFragment firstFragment = getFirstFragment();
            if(firstFragment != null){
                addFragment(firstFragment);
            }
        }else{//内存不足，重启时调用
            //可以结合，SaveInstance存储其tag
        }
    }

    //处理传参
    private void handleIntent(Intent intent){

    }

    private BaseFragment getFirstFragment(){
        return LoginFragment.newInstance("");
    }

}
