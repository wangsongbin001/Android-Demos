package com.wang.xyhua.common.volley;

/**
 * Created by dell on 2017/9/21.
 */

public interface RequestLisenter {

    public void onSuccess(HttpResult result);

    public void onFailure(String error);
}
