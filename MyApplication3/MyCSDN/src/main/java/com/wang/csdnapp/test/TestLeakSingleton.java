package com.wang.csdnapp.test;

import android.content.Context;
import android.widget.TextView;

import com.wang.csdnapp.R;

/**
 * Created by SongbinWang on 2017/7/12.
 */

public class TestLeakSingleton {

    private TextView tv;
    private Context context;

    private static TestLeakSingleton singleton = null;

    public static TestLeakSingleton getInstance(Context context){
        if(singleton == null){
            singleton = new TestLeakSingleton(context);
        }
        return singleton;
    }

    private TestLeakSingleton(Context context){
        this.context = context;
    }

    public void setTvAppName(TextView tv){
        this.tv = tv;
        tv.setText(context.getString(R.string.app_name));
    }
}
