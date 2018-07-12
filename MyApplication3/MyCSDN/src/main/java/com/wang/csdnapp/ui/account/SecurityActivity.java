package com.wang.csdnapp.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.view.SecurityLayout1;

/**
 * Created by SongbinWang on 2017/5/24.
 */

public class SecurityActivity extends Activity{

    private ImageView iv_back;
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        init();
        registerListeners();
    }

    private void init(){
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_back = (TextView) findViewById(R.id.tv_back);
    }

    private void registerListeners(){
        iv_back.setOnClickListener(onClicker);
        tv_back.setOnClickListener(onClicker);
    }

    private View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_back:
                case R.id.tv_back:
                    finish();
                    break;
            }
        }
    };
}
