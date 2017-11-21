package com.wang.mtoolsdemo.common.rxjava1;

import android.text.TextUtils;

import com.wang.mtoolsdemo.App;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.HttpUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2017/11/21.
 */

public class CommonInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Charset", "UTF-8")
                // 不能自己添加，否则不会自动解压
                // .header("Accept-Encoding", "gzip, deflate")
                .header("deviceNo", AppUtil.getDeviceUUID(App.getInstance()).toString())
                .header("Accept-Language", "zh-CN, zh;q=0.8, en-US;q=0.5, en;q=0.3")
                .method(original.method(), original.body());
        if (HttpUtil.getSessionId() != null) {
            builder.header("accessToken", HttpUtil.getSessionId());
        }

        Response response = chain.proceed(builder.build());

        String strDate = response.header("Date");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        try {
            HttpUtil.setServerDate(sdf.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String token = response.header("newAccessToken");
        if (!TextUtils.isEmpty(token)) {
            HttpUtil.setSessionId(response.header("newAccessToken"));
        }
        return response;
    }
}
