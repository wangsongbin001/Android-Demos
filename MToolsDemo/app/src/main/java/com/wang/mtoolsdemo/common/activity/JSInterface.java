package com.wang.mtoolsdemo.common.activity;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by dell on 2017/12/5.
 */

public class JSInterface extends Object{

    @JavascriptInterface
    public void hello(String msg){
        Log.i("wangsongbin", "msg:" + msg);
    }
}
