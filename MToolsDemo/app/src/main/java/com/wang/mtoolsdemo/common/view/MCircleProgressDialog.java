package com.wang.mtoolsdemo.common.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by dell on 2017/11/28.
 */

public class MCircleProgressDialog extends ProgressDialog {

    public MCircleProgressDialog(Context context) {
        this(context, 0);
    }

    public MCircleProgressDialog(Context context, int theme) {
        super(context, theme);
        initViews();
    }

    private void initViews(){

    }

}
