package com.wang.xyhua.common.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wang.xyhua.common.volley.RequestLisenter;

/**
 * Created by dell on 2017/9/21.
 */

public class IMErrorListener implements Response.ErrorListener{

    RequestLisenter requestLisenter;
    Context context;

    public IMErrorListener(RequestLisenter requestLisenter, Context context){
        this.requestLisenter = requestLisenter;
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(requestLisenter != null){
            requestLisenter.onFailure(error.getMessage());
        }
    }
}
