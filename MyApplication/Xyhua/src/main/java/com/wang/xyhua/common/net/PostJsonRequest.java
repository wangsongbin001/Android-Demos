package com.wang.xyhua.common.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/9/21.
 */

public class PostJsonRequest extends JsonObjectRequest{

    public static final String ENCODEING = "UTF-8";

    public PostJsonRequest(String url, JSONObject jsonRequest,
                           Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        super(Method.POST, url, jsonRequest, listener, errorListener);
//        setRetryPolicy(new )
    }

    @Override
    protected String getParamsEncoding() {
        return ENCODEING;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
//        headers.put();
        return super.getHeaders();
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }
}
