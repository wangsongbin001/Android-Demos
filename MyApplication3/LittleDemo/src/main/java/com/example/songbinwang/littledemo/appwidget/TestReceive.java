package com.example.songbinwang.littledemo.appwidget;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.songbinwang.littledemo.MainActivity;
import com.example.songbinwang.littledemo.util.LogUtil;

/**
 * Created by songbinwang on 2017/2/16.
 */

public class TestReceive extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.i(AppWidgetClockProvider.tag, "testOnReceive");
        String action = intent.getAction();
        LogUtil.i(AppWidgetClockProvider.tag, "test action" + action);
        if(AppWidgetClockProvider.AppWidget_Click_Action.equals(action)){
            Intent in = new Intent(context, MainActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        }
    }
}
