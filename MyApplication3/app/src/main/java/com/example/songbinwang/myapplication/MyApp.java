package com.example.songbinwang.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.songbinwang.myapplication.util.UriUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.reflect.Field;

/**
 * Created by SongbinWang on 2017/7/12.
 */

public class MyApp extends Application{

    RefWatcher refWatcher = null;
    public static RefWatcher getRefWatcher(Context context){
        MyApp app = (MyApp) context.getApplicationContext();
        return app.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        refWatcher = LeakCanary.install(this);

        try {
            Log.i("zhuixu", "kaishi");
            Class instance = Class.forName("com.example.songbinwang.myapplication.util.UriUtil");
            Field field = instance.getDeclaredField("isDebug");
            field.setAccessible(true);
            field.setBoolean(instance, false);
            UriUtil.show();
            Log.i("zhuixu", "jieshu");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
