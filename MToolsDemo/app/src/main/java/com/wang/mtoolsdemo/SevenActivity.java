package com.wang.mtoolsdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dell on 2017/11/30.
 */

public class SevenActivity extends AppCompatActivity{

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        new AlertDialog.Builder(mContext)
                .setTitle("title")
                .setMessage("message")
                .create()
                .show();
    }
}
