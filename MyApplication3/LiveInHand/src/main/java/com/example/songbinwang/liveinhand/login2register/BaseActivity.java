package com.example.songbinwang.liveinhand.login2register;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * 1，移除fragment，
 * 2，添加fragment
 * 3，onKeyDown
 * Created by songbinwang on 2016/6/16.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected abstract int getContentViewId();

    protected abstract int getFragmentViewId();

    protected void addFragment(BaseFragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentViewId(), fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    protected void removeFragment(){
        if(getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            if(getSupportFragmentManager().getBackStackEntryCount() == 1){
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
