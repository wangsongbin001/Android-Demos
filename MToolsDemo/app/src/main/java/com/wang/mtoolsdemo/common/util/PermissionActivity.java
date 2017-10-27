package com.wang.mtoolsdemo.common.util;

import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/12.
 * 使用方法只需要，继承PermissionActivity，
 * 调用checkNeedPermissions方法即可，当拒绝权限回调为null时，启动默认处理模式，
 */

public class PermissionActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static String PERMISSION_KEY = "permission_key";
    private String[] needPermissions;
    private PermissionChecker mChecker;
    AlertDialog aDialog;
    public static final String tag = "wangsongbin";

    //授权成功回调
    protected interface AllPermissionGrantedCallBack {
        //所有权限被授予
        void allPermissionGranted();
    }

    AllPermissionGrantedCallBack allPermissionGrantedCallBack;

    //授权失败回调
    protected interface PermissionsDeniedCallback {
        //一些权限被拒
        void somePermissionsDenied(List<String> deniedPermissions);
//        //一些权限被授权
//        void somePermissionsGranted(List<String> grantedPermissions);
    }
    PermissionsDeniedCallback permissionsDeniedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getIntent() == null || !getIntent().hasExtra(PERMISSION_KEY)) {
//            Toast.makeText(this, "缺少参数", Toast.LENGTH_SHORT).show();
//            finish();
//        }
        mChecker = new PermissionChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("wangsongbin", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("wangsongbin", "onPause");
    }

    protected void checkNeedPermissions(String[] permissions, AllPermissionGrantedCallBack allPermissionGrantedCallBack
              ,PermissionsDeniedCallback permissionsDeniedCallback) {
        if (permissions == null) {
            Log.i(tag, "exception permissions is null");
            return;
        }
        this.needPermissions = permissions;
        this.allPermissionGrantedCallBack = allPermissionGrantedCallBack;
        this.permissionsDeniedCallback = permissionsDeniedCallback;
        if (mChecker.lackPermissions(permissions)) {
            //去获取权限
            requestPermissions();
        } else {
            //权限齐全
            allPermissionGranted();
        }
    }

    //请求权限，兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, needPermissions, PERMISSION_REQUEST_CODE);
    }

    //权限齐全回调
    private void allPermissionGranted() {
        Log.i(tag, "权限齐全");
        if (allPermissionGrantedCallBack != null) {
            allPermissionGrantedCallBack.allPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("wangsongbin", "onRequestPermissionsResult requestCode:" + requestCode);
        if (requestCode == PERMISSION_REQUEST_CODE && !mChecker.lackPermissions(permissions)) {
            allPermissionGranted();
        } else {
            //如果用户写了授权失败处理，我们就不处理，否则走封装好的逻辑
            if (permissionsDeniedCallback != null) {
                List<String> denied = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                     if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                         denied.add(permissions[i]);
                     }
                }
                permissionsDeniedCallback.somePermissionsDenied(denied);
            } else {
                //自己弹窗，提醒授权
                showMissPermissionDialog();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("wangsongbin", "onActivityResult requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == PERMISSION_REQUEST_CODE) {//从设置界面过来
            if (!mChecker.lackPermissions(needPermissions)) {
                allPermissionGranted();
            } else {
                if (permissionsDeniedCallback != null) {
                    List<String> denied = new ArrayList<>();
                    for (int i = 0; i < needPermissions.length; i++) {
                        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), needPermissions[i])
                                  != PackageManager.PERMISSION_GRANTED){
                            denied.add(needPermissions[i]);
                        }
                    }
                    permissionsDeniedCallback.somePermissionsDenied(denied);
                } else {
                    //自己弹窗，提醒授权
                    showMissPermissionDialog();
                }
            }
        }
    }

    private void showMissPermissionDialog() {
        if (aDialog == null) {
            aDialog = new AlertDialog.Builder(PermissionActivity.this)
                    .setTitle("帮助")
                    .setMessage("当前缺少必要权限，请前往设置")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startAppSetting();
                        }
                    })
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create();
            aDialog.show();
        } else {
            if (aDialog.isShowing()) {
                aDialog.dismiss();
            }
            aDialog.show();
        }
    }

    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    protected void startAppSetting() {//前往设置界面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
    }

    public static void startPermissionActivity(Activity mActivity, String... permissions) {
        Intent intent = new Intent(mActivity, PermissionActivity.class);
        intent.putExtra(PERMISSION_KEY, permissions);
        mActivity.startActivityForResult(intent, REQUEST_CODE);
    }
}
