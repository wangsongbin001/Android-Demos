package com.wang.xyhua.common.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wang.xyhua.common.net.IMErrorListener;
import com.wang.xyhua.common.net.IMJsonListener;
import com.wang.xyhua.common.net.IOnCancelListener;
import com.wang.xyhua.common.net.PostJsonRequest;
import com.wang.xyhua.common.volley.RequestLisenter;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dell on 2017/9/21.
 */

public class HttpUtil {

    public static volatile RequestQueue mQueue;
    public static Map<Context, HttpUtil> httpUtilMaps = new ConcurrentHashMap<Context, HttpUtil>();

    private Context context;
    //是否需要加载弹窗
    private boolean isOpenProgressBar = false;

    public HttpUtil(Context context){
        this.context = context;
    }

    public static HttpUtil getInstance(Context context){
        if(mQueue == null){
            synchronized (HttpUtil.class){
                if(mQueue == null){
                    mQueue = Volley.newRequestQueue(context.getApplicationContext());
                }
            }
        }
        if(httpUtilMaps.containsKey(context)){
            return httpUtilMaps.get(context);
        }
        HttpUtil httpUtil = new HttpUtil(context);
        httpUtilMaps.put(context, httpUtil);
        return httpUtil;
    }

    public Request<JSONObject> doPostByJson(String url, Map<String, String> params,
                                            RequestLisenter requestListener){
        return doPostByJson(url, params, requestListener, isOpenProgressBar);
    }

    public Request<JSONObject> doPostByJson(String url, Map<String, String> params,
                                            RequestLisenter requestListener,
                                            boolean isOpenProgressBar){
        JSONObject jsonObject = new JSONObject(params);
        return doPostByJson(url, jsonObject, requestListener, isOpenProgressBar, null);
    }

    public Request<JSONObject> doPostByJson(String url, JSONObject jsonObject,
                                            RequestLisenter requestListener,
                                            boolean isOpenProgressBar,
                                            IOnCancelListener mIOnCancelLisenter){
        if(context == null){
            return null;
        }
        //检查网络
        if(!NetWorkUtil.checkNetState(context)){
            Toast.makeText(context, "网络不可用！", Toast.LENGTH_LONG).show();
            return null;
        }
        //处理加载弹窗
        isShowProgressBar(isOpenProgressBar, mIOnCancelLisenter);
        LogUtil.i(String.format("HttpUtils:url = %s, params = %s", url, jsonObject.toString()));

        Request<JSONObject> request = mQueue.add(new PostJsonRequest(url,
                jsonObject,
                new IMJsonListener(requestListener, context),
                new IMErrorListener(requestListener, context)));
        request.setTag(context);
        return request;
    }

    public void isShowProgressBar(boolean isOpenProgressBar, IOnCancelListener mIOnCancelListener){

    }

    /**
     * 清除当前Activity的请求队列
     */
    public void cancelAllRequestQueue() {
        if (mQueue != null && context != null) {
            mQueue.cancelAll(context);
            httpUtilMaps.remove(context);
        }
    }


}
