package com.wang.mtoolsdemo.common.activity;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by dell on 2017/12/1.
 */

public class Android2Js extends Object {

    @JavascriptInterface
    public void hello(String msg) {
        System.out.println("" + msg);
    }

    @JavascriptInterface
    public void onButtonClick(String msg) {
        Log.i("wangsongbin", "msg:" + msg);
    }

    @JavascriptInterface
    public void onImageClick(String arg1, String arg2, String arg3) {
        Log.i("wangsongbin", "arg1:" + arg1 + ",arg2:" + arg2 + ",arg3:" + arg3);
    }

    @JavascriptInterface
    public void getContents(String data){
        Log.i("wangsongbin", "data:" + data);
    }
}
