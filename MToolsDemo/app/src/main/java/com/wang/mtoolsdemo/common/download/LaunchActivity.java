package com.wang.mtoolsdemo.common.download;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wang.mtoolsdemo.common.bean.PlatformVersion;
import com.wang.mtoolsdemo.common.util.DialogUtil;
import com.wang.mtoolsdemo.common.util.DownloadManagerResolver;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.NetUtil;
import com.wang.mtoolsdemo.common.util.PermissionActivity;
import com.wang.mtoolsdemo.common.util.ToastUtil;

import java.util.List;

/**
 * Created by dell on 2017/11/27.
 * App 启动页，版本更新
 * 1, 检查权限
 * 2，检查版本
 * 3，下载最新版本apk
 */

public class LaunchActivity extends PermissionActivity implements
        PermissionActivity.AllPermissionGrantedCallBack,
        PermissionActivity.PermissionsDeniedCallback{

    private Activity mActivity;
    LaunchHelper mLaunchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText("Welcome to Android");
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setGravity(Gravity.CENTER);
        setContentView(textView, layoutParams);

        mActivity = this;
        mLaunchHelper = new LaunchHelper(this);

        //1，检查权限
        LogUtil.i("检查权限");
        checkNeedPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, this, this);
    }

    @Override
    public void allPermissionGranted() {
        if (!NetUtil.isConnected(mActivity)) {
            ToastUtil.showLong(mActivity, "当前网络不可用");
            return;
        }
        //2检查版本
        LogUtil.i("检查版本");
        mLaunchHelper.checkVersion();
    }

    @Override
    public void somePermissionsDenied(List<String> deniedPermissions) {
        showMissPermissionDialog();
    }

    public void showUpdateView(PlatformVersion platformVersion){
        //如果正在下载
        if(mLaunchHelper.isDownloading(platformVersion.getDownloadUrl())){
            return;
        }
        //判断是否已经下载了有安装包

        //没有下载更新
        DialogUtil.showDialog(this, "tips", "有最新版本发布，是否更新", "立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //检查DownloadManager是否被禁用
                if(!DownloadManagerResolver.resolve(mActivity)){
                    dialog.dismiss();
                    return;
                }
//                mLaunchHelper.startDownload();

            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
