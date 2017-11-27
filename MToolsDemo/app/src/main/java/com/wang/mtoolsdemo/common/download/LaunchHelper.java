package com.wang.mtoolsdemo.common.download;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;

import com.wang.mtoolsdemo.common.bean.NewVersion;
import com.wang.mtoolsdemo.common.bean.PlatformVersion;
import com.wang.mtoolsdemo.common.rxjava1.MErrorAction;
import com.wang.mtoolsdemo.common.rxjava1.RetrofitUtil1;
import com.wang.mtoolsdemo.common.util.AppUtil;
import com.wang.mtoolsdemo.common.util.DialogUtil;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.NetUtil;
import com.wang.mtoolsdemo.common.util.SDCardUtil;
import com.wang.mtoolsdemo.common.util.ToastUtil;

import rx.functions.Action1;
import rx.functions.Func1;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by dell on 2017/11/27.
 */

public class LaunchHelper {
    LaunchActivity mLauncherActivity;

    public LaunchHelper(LaunchActivity mLauncherActivity) {
        this.mLauncherActivity = mLauncherActivity;
    }

    public void checkVersion() {
        RetrofitUtil1.getService().getNewVersion3()
                .map(new Func1<NewVersion, PlatformVersion>() {
                    @Override
                    public PlatformVersion call(NewVersion newVersion) {
                        return newVersion.getAndroid();
                    }
                }).subscribe(new Action1<PlatformVersion>() {
            @Override
            public void call(PlatformVersion platformVersion) {
                LogUtil.i("" + platformVersion.toString());
                int oldVersionCode = AppUtil.getAppVersionCode(mLauncherActivity);
                //服务端返回的信息
                int newVersionCode = platformVersion.getVersionCode();
                String downloadUrl = platformVersion.getDownloadUrl();
                if(newVersionCode > oldVersionCode && !TextUtils.isEmpty(downloadUrl)){
                    mLauncherActivity.showUpdateView(platformVersion);
                }
            }
        }, new MErrorAction(mLauncherActivity));
    }

    /**
     * 开始更新
     */
    public void startDownload(PlatformVersion version){
        if(SDCardUtil.isSdcardEnable()){
            if(SDCardUtil.getSdcardAvailableSize() > version.getAppSize()){
                Intent intent = new Intent(mLauncherActivity, DownloadService.class);
                intent.putExtra("downloadUrl", version.getDownloadUrl());
                intent.putExtra("fileName", version.getVersionName());
                mLauncherActivity.startService(intent);
            }else{
                ToastUtil.showLong(mLauncherActivity, "SD内存空间不足");
            }
        }else{
            ToastUtil.showLong(mLauncherActivity, "SD卡不可用");
        }
    }


    /**
     * 判断传入的url,是否正在下载
     * @param url
     * @return
     */
    public boolean isDownloading(String url){
        Cursor c = ((DownloadManager) mLauncherActivity.getSystemService(DOWNLOAD_SERVICE))
                .query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        if (c != null && c.moveToFirst()) {
            String tmpURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
            if (tmpURI.equals(url)) {
                if (!c.isClosed()) {
                    c.close();
                }
                return true;
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return false;
    }

    public void checkUpdateNet(){
        //检查下载网络
        LogUtil.i("检查版本");
        if (!NetUtil.isWifi(mLauncherActivity)) {
            DialogUtil.showDialog(mLauncherActivity, "tips", "当前为数据网络，更新会消耗流量！",
                    "确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            return;
        }
    }

}
