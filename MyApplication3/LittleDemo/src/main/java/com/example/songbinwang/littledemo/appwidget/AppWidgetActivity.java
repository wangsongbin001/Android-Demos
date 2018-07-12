package com.example.songbinwang.littledemo.appwidget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2017/2/15.
 */

public class AppWidgetActivity extends Activity{

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appwidget);

        initViews();

        ClockDrawable clockDrawable = new ClockDrawable(this);
        imageView.setImageDrawable(clockDrawable);
    }

    private void initViews(){
       imageView = (ImageView) findViewById(R.id.id_iv_content);
    }

}
