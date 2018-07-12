package com.wang.csdnapp.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by SongbinWang on 2017/6/14.
 */

public abstract class MBaseActivity extends AppCompatActivity {

    //获取布局中fragmentID
    public abstract int getFragmentContentID();

    //展示一个新的fragment，
    protected void show(Fragment fragment){
        Fragment fg = getSupportFragmentManager()
                .findFragmentByTag(fragment.getClass().getSimpleName());
        if(null == fg){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getFragmentContentID(), fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(fg)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 切换Fragment
     * @param from
     * @param to
     */
    protected void switchContent(Fragment from, Fragment to){
        Fragment fg = getSupportFragmentManager()
                .findFragmentByTag(to.getClass().getSimpleName());
        if(null == fg){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(from)
                    .add(getFragmentContentID(), to, to.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(from)
                    .show(fg)
                    .commitAllowingStateLoss();
        }
    }

    //隐藏原本一个Fragment
    protected void hide(){

    }


    /**
     * 直接退出activity
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){

        }
        return super.onKeyDown(keyCode, event);
    }
}
