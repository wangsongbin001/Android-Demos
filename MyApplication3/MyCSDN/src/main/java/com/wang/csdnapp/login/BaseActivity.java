package com.wang.csdnapp.login;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by SongbinWang on 2017/2/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    //获取文件布局id
    public abstract int getContentViewID();
    //获取布局中fragmentID
    public abstract int getFragmentContentID();

    //添加Fragment的方法
    protected void addFragment(BaseFragment fragment){
        if( fragment != null){
             getSupportFragmentManager().beginTransaction()
                     .replace(getFragmentContentID(), fragment, fragment.getClass().getSimpleName())
                     .addToBackStack(fragment.getClass().getSimpleName())
                     .commitAllowingStateLoss();
        }
    }

    //移除fragment方法(移除当前栈顶的fragment)
    protected void removeFragment(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

    //处理返回键事件
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
