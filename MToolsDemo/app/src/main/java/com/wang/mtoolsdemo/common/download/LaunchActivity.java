package com.wang.mtoolsdemo.common.download;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wang.mtoolsdemo.MainActivity;
import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.bean.PlatformVersion;
import com.wang.mtoolsdemo.common.util.DialogUtil;
import com.wang.mtoolsdemo.common.util.DownloadManagerResolver;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.NetUtil;
import com.wang.mtoolsdemo.common.util.PermissionActivity;
import com.wang.mtoolsdemo.common.util.ToastUtil;
import com.wang.mtoolsdemo.common.view.MHorizontalProgressDialog;
import com.wang.mtoolsdemo.common.view.NumberProgressBar;

import java.util.List;

/**
 * Created by dell on 2017/11/27.
 * App 启动页，版本更新
 * 1, 检查权限
 * 2，检查版本
 * 3, 检查网络
 * 4，下载最新版本apk(实时更新进度条)
 * 5，安装apk（兼容Android 6.0， 7.0）
 */

public class LaunchActivity extends PermissionActivity implements
        PermissionActivity.AllPermissionGrantedCallBack,
        PermissionActivity.PermissionsDeniedCallback {

    private Activity mActivity;
    LaunchHelper mLaunchHelper;

    private TextView tv_progress;
    private NumberProgressBar progressBar;
    private MHorizontalProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        tv_progress = (TextView) findViewById(R.id.tv_progress);
        progressBar = (NumberProgressBar) findViewById(R.id.pb_content);

        mActivity = this;
        mLaunchHelper = new LaunchHelper(this);

        //1，检查权限
        LogUtil.i("wangsongbin", "检查权限");
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
        LogUtil.i("wangsongbin", "检查版本");
        mLaunchHelper.checkVersion();
    }

    @Override
    public void somePermissionsDenied(List<String> deniedPermissions) {
        showMissPermissionDialog();
    }

    public void showUpdateView(final PlatformVersion platformVersion) {
        //如果正在下载
        if (mLaunchHelper.isDownloading(platformVersion.getDownloadUrl())) {
            return;
        }

        //判断是否已经下载了有安装包
        if (mLaunchHelper.isDownloaded(platformVersion.getVersionCode())) {
            showApkDownloadedDialog(platformVersion);
            return;
        }

        //没有下载更新
        showInstallDialog(platformVersion);
    }

    private void showApkDownloadedDialog(final PlatformVersion version) {
        DialogUtil.showDialog(this, "tips", "检测到您已经下载了更新包，是否安装更新！",
                "立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mLaunchHelper.installApk(LaunchActivity.this, mLaunchHelper.getAppLocalPath(version.getVersionCode()));
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void showInstallDialog(final PlatformVersion version) {
        DialogUtil.showDialog(this, "tips", "有最新版本发布，是否更新", "立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //检查DownloadManager是否被禁用
                if(!DownloadManagerResolver.resolve(mActivity)){
                    dialog.dismiss();
                    return;
                }
                mLaunchHelper.startDownloadWithCheckNet(version);

            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 弹窗进度条
     * @param version
     */
    public void showProgressDialog(PlatformVersion version){
        if(mProgressDialog == null){
            mProgressDialog = new MHorizontalProgressDialog(mActivity);
        }
        mProgressDialog.setTitle("正在下载：" + mLaunchHelper.getApkName(version.getVersionCode()));
        mProgressDialog.update(0, (long) version.getAppSize());
        mProgressDialog.show();
    }

    public void go2MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 更新进度
     * @param progress
     */
    public void updateProgress(long progress, long total){
        int tempProgress = (int) (progress * 100.0f/total);
        tv_progress.setText( tempProgress + "%");
        progressBar.setProgress(tempProgress);
        if(mProgressDialog != null){
            mProgressDialog.update(progress, total);
        }
    }

    @Override
    protected void onDestroy() {
        mLaunchHelper.onDestory();
        super.onDestroy();

    }
}
