package com.example.songbinwang.liveinhand.im;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.view.ShareDialog;

/**
 * Created by songbinwang on 2016/8/2.
 */
public class IMMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ShareDialog mDialog;
    private RadioGroup rg_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_mainactivity);

        initData();
        initViews();
    }

    private void initData(){

    }

    private void initViews(){
        findViewById(R.id.iv_share).setOnClickListener(this);
        rg_share = (RadioGroup) findViewById(R.id.rg_share);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_share:
                showDialog();
                break;
        }
    }

    private void showDialog(){
        int id = rg_share.getCheckedRadioButtonId();
        ShareDialog  mDialog = new ShareDialog(this);
        mDialog.show();
    }
}
