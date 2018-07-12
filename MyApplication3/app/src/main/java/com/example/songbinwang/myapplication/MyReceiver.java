package com.example.songbinwang.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by SongbinWang on 2017/7/28.
 */

public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = getResultExtras(true).getString("tag");
        Log.i("MyReceiver", "onReceive tag:" + data);
    }
}
