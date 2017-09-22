package com.wang.xyhua.common.volley;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.wang.xyhua.common.utils.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by dell on 2017/9/22.
 */

public class MCustomPostRequest extends StringRequest{

    private static final String DEFAULT_ENGCODE = "UTF-8";
    private Map<String, String> params;

    public MCustomPostRequest(String url,
                              Map<String, String> params,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.params = params;
    }

    @Override
    protected String getParamsEncoding() {
        return DEFAULT_ENGCODE;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        //编码
        headers.put("Charset", getParamsEncoding());
        //
        headers.put("Accept-Encoding", "gzip,deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        //硬件ID
//        headers.put("deviceNo", DeviceIdUtils.getDeviceId(
//                             App.getInstance().getApplicationContext()).toString());
        String sessionId = HttpUtils.getSessionId();
        if(!TextUtils.isEmpty(sessionId)){
            headers.put("accessToken", sessionId);
        }
//        return super.getHeaders();
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String strDate = response.headers.get("Date");
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
            HttpUtils.setServerDate(sdf.parse(strDate));

            String authToken = response.headers.get("newAccessToken");
            if (!TextUtils.isEmpty(authToken))
            {
                HttpUtils.setSessionId(authToken);
                LogUtil.i("newAccessToken:" + HttpUtils.getSessionId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String parsed =getRealString(response.data);
            return Response.success(parsed,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    private int getShort(byte[] data)
    {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    private String getRealString(byte[] data) {
        byte[] h = new byte[2];
        h[0] = (data)[0];
        h[1] = (data)[1];
        int head = getShort(h);
        boolean t = head == 0x1f8b;
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            if (t) {
                in = new GZIPInputStream(bis);
            } else {
                in = bis;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
