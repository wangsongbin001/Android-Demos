package com.wang.xyhua.common.volley;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wang.xyhua.common.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2017/9/22.
 */

public class IMResponseListener<T> implements Response.Listener {
    private RequestLisenter requestListener;
    private Class<T> dataClass;
    private static Gson gson;

    public IMResponseListener(RequestLisenter requestListener, Class<T> data) {
        this.requestListener = requestListener;
        this.dataClass = data;
        if (gson == null) {
            gson = new GsonBuilder().excludeFieldsWithModifiers().create();
        }
    }

    @Override
    public void onResponse(Object response) {
        String result = (String) response;
        LogUtil.i("onResponse result;" + result);
        HttpResult httpResult = gson.fromJson(result, HttpResult.class);

        try {
            JSONObject obj = new JSONObject(result);
            JSONObject dataObj = (JSONObject) obj.get("data");
            if (dataObj != null) {
                LogUtil.i("onResponse data;" + dataObj.toString());
                T data = gson.fromJson(dataObj.toString(), dataClass);
                httpResult.setData(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestListener.onSuccess(httpResult);
    }
}
