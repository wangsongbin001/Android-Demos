package com.wang.mtoolsdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dell on 2017/11/16.
 */

public class FiveActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(0, null)
                .addToBackStack("")
                .commit();
    }
}
