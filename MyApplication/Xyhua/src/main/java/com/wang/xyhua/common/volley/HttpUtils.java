package com.wang.xyhua.common.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.wang.xyhua.common.utils.LogUtil;
import com.wang.xyhua.common.utils.NetWorkUtil;
import com.wang.xyhua.common.utils.ToastUtil;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dell on 2017/9/22.
 */

public class HttpUtils {

    public static final String BaseUrl = "http://10.138.60.131:10000/app/";

    public static RequestQueue mQueue;

    private Context context;
    private static Map<Context, HttpUtils> httpUtilsMap = new ConcurrentHashMap<>();
    /**
     * 会话识别号
     */
    @Setter
    @Getter
    private static String sessionId;
    @Setter
    @Getter
    private static Date serverDate;

    private HttpUtils(Context context) {
        this.context = context;
    }

    public static HttpUtils getInstance(Context context) {
        if (mQueue == null) {
            synchronized (HttpUtils.class) {
                if (mQueue == null) {
                    mQueue = Volley.newRequestQueue(context.getApplicationContext());
                }
            }
        }
        if (httpUtilsMap.containsKey(context)) {
            return httpUtilsMap.get(context);
        } else {
            HttpUtils httpUtils = new HttpUtils(context);
            httpUtilsMap.put(context, httpUtils);
            return httpUtils;
        }

    }

    public <T> void doPostByJson(String url, Map<String, String> params,
                                 final RequestLisenter requestLisenter,
                                 final Class<T> cls) {
        if (TextUtils.isEmpty(url)) {
            ToastUtil.toast(context, "url 为 null");
            return;
        }
        //网络检测
        if (!NetWorkUtil.checkNetState(context)) {
            ToastUtil.toast(context, "网络异常");
            return;
        }

        String mUrl = createUrl(url);
        LogUtil.i("url:" + mUrl);

        MCustomPostRequest request = new MCustomPostRequest(mUrl, params,
                new IMResponseListener<T>(requestLisenter, cls),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestLisenter.onFailure(error.getMessage());
                    }
                });
        request.setTag(context);
        mQueue.add(request);
    }

    private String createUrl(String url) {
        if (url.startsWith("http:")) {
            return url;
        }
        StringBuilder strSb = new StringBuilder(BaseUrl);
        return strSb.append(url).toString();
    }

    public void cancelByTag(Context context) {
        mQueue.cancelAll(context);
        httpUtilsMap.remove(context);
    }

}
