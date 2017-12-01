package com.wang.mtoolsdemo.common.activity;

import android.webkit.JavascriptInterface;

/**
 * Created by dell on 2017/12/1.
 */

public class Android2Js extends Object{

    @JavascriptInterface
    public void hello(String msg) {
        System.out.println("" + msg);
    }
}
