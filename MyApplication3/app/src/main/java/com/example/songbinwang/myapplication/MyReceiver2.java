package com.example.songbinwang.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by SongbinWang on 2017/7/28.
 */

public class MyReceiver2 extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MyReceiver", "onReceive2");
        try{
            Bundle bundle = new Bundle();
            bundle.putString("tag", "form receiver2");
            setResultExtras(bundle);
        }catch (Exception e){

        }


    }
}
