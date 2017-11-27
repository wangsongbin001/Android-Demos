package com.wang.mtoolsdemo;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wang.mtoolsdemo.common.bean.ProgressBean;
import com.wang.mtoolsdemo.common.download.DownloadChangeObserver;
import com.wang.mtoolsdemo.common.util.SPUtil;

import java.io.File;

/**
 * Created by dell on 2017/11/24.
 * 检测更新App
 */

public class SixActivity extends Activity{
    String url = "http://m.creditflower.cn/apk/XinYongHua-V110-TEST.apk";
    String fileName = "XinYongHua-V110-TEST.apk";

    private DownloadManager mDownloadManager;
    private TextView tv_progress;
    private ProgressBar pb_content;

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressBean progressBean = (ProgressBean) msg.obj;
            float percent = progressBean.getProgress() * 1.0f/progressBean.getTotal();
            int progress = (int) (percent * 100);
            tv_progress.setText(progress + "%");
            pb_content.setProgress(progress);
        }
    };

    DownloadChangeObserver mDownloadChangeObserver = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_6);

        tv_progress = findViewById(R.id.tv_progress);
        pb_content = findViewById(R.id.pb_content);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //在执行下载任务前设置观察者
        register();
        //执行下载任务
        long downloadId = mDownloadManager.enqueue(createRequest());
        SPUtil.put(this, "downloadid", downloadId);
    }

    private DownloadManager.Request createRequest(){
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置下载时的网络状态
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        //设置通知栏是否可见
        request.setNotificationVisibility(View.VISIBLE);
        /**设置文件保存路径*/
        File fileDir = Environment.getExternalStoragePublicDirectory("/wangsongbin/file");
        if (fileDir.mkdirs() || fileDir.isDirectory()) {
            request.setDestinationInExternalPublicDir("/wangsongbin/file", "phoenix.apk");
        }
        return request;
    }

    private void register(){
        mDownloadChangeObserver = new DownloadChangeObserver(this, mainHandler);
        getContentResolver().registerContentObserver(Uri.parse("content://downloads/"), true, mDownloadChangeObserver);

    }
}
