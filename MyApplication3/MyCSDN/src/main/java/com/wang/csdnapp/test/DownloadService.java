package com.wang.csdnapp.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wang.csdnapp.okhttp.ProgressHelper;
import com.wang.csdnapp.okhttp.ProgressResponseListener;
import com.wang.csdnapp.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by SongbinWang on 2017/7/6.
 */

public class DownloadService extends Service implements ProgressResponseListener{

    private static final String TAG = "DownloadService";

    Thread thread = null;
    private OkHttpClient okHttpClient = ProgressHelper.addProgressResponseListener(this);
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "onStartCommand current Thread:" + Thread.currentThread());
        thread = new Thread(){
            @Override
            public void run() {
                super.run();
                String url = "http://g.hiphotos.baidu.com/zhidao/pic/item/18d8bc3eb13533fa746b1558aed3fd1f40345be3.jpg";
                LogUtil.i(TAG, "url:" + url);
                Request request = new Request.Builder().url(url).build();
                InputStream in = null;
                FileOutputStream fos = null;
                try {
                    String path = "mnt/sdcard/Download/qq.jpg";
                    File file = new File(path);
                    LogUtil.i(TAG, "loacal path:" + file.getAbsolutePath());
                    Response response = okHttpClient.newCall(request).execute();
                    if(response.isSuccessful()){
                        LogUtil.i(TAG, "response successfull!");
                        in = response.body().byteStream();
                        fos = new FileOutputStream(file);
                        int len = -1;
                        byte[] buf = new byte[1024];
                        while((len = in.read(buf)) != -1){
                            fos.write(buf, 0, len);
                        }
                    }else{
                        LogUtil.i(TAG, "response failiure:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(in != null){ try{ in.close();}catch (Exception e){}}
                    if(fos != null){ try{ fos.close();}catch (Exception e){}}
                }
            }
        };
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
        LogUtil.i(TAG, "onResponseProgress currentThread: " + Thread.currentThread() );
        LogUtil.i(TAG, "onResponseProgress byteRead:" + bytesRead + ",contentLength:" + contentLength + ",done" + done);
    }
}
