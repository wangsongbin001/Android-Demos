package com.wang.xyhua.common.net;

import android.content.Context;

import com.android.volley.Response;
import com.wang.xyhua.common.volley.RequestLisenter;

/**
 * Created by dell on 2017/9/21.
 */

public class IMJsonListener implements Response.Listener{

    RequestLisenter requestLisenter;
    Context context;

    public IMJsonListener(RequestLisenter requestLisenter, Context context){
        this.requestLisenter = requestLisenter;
        this.context = context;
    }

    @Override
    public void onResponse(Object response) {

    }
}
