package com.wang.mtoolsdemo.common.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.util.DensityUtil;

/**
 * Created by dell on 2017/11/28.
 * http://www.jianshu.com/p/672e6486a72a
 */

public class MHorizontalProgressDialog extends ProgressDialog{

    TextView tv_title, tv_size;
    NumberProgressBar progressBar;

    public MHorizontalProgressDialog(Context context) {
        super(context);
    }

    public MHorizontalProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(getContext());
    }

    private void initViews(Context context){
        setContentView(R.layout.dialog_horizontal);
        tv_title = getWindow().findViewById(R.id.tv_title);
        tv_size = getWindow().findViewById(R.id.tv_size);
        progressBar = getWindow().findViewById(R.id.progress);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = DensityUtil.dp2px(context.getApplicationContext(), 320);
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
    }

    @Override
    public void setProgress(int value) {
        super.setProgress(value);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        tv_title.setText(title);
    }

    public void update(long progress, long total){
        tv_size.setText(progress + "/" + total);
        progressBar.setProgress((int) (100 * (progress/total)));
    }
}
