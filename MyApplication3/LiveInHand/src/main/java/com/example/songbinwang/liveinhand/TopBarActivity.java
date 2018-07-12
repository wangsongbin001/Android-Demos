package com.example.songbinwang.liveinhand;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.songbinwang.liveinhand.uitls.SystemBarTintManager;

/**
 * Created by songbinwang on 2016/10/20.
 */

public class TopBarActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            //激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            //激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置颜色
            tintManager.setStatusBarTintColor(getResources().getColor(android.R.color.holo_orange_dark));
            tintManager.setNavigationBarTintColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        setContentView(R.layout.layout_topbar);
    }

    public void onClick(View view){

    }
}
