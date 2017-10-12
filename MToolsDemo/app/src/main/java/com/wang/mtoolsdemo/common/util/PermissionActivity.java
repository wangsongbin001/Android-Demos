package com.wang.mtoolsdemo.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by dell on 2017/10/12.
 */

public class PermissionActivity extends Activity {

    private static final int REQUEST_CODE = 1001;
    private static final int RESULT_CODE = 1002;
    private static final int PERMISSION_REQUEST_CODE = 0;
    private static String PERMISSION_KEY = "permission_key";
    private String[] needPermissions;
    private boolean requestCheck = true;
    private PermissionChecker mChecker;
    AlertDialog aDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(PERMISSION_KEY)) {
            Toast.makeText(this, "缺少参数", Toast.LENGTH_SHORT).show();
            finish();
        }
//        setContentView(R.layout.activity_permission);
        mChecker = new PermissionChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("wangsongbin", "onResume requestcheck:" + requestCheck);
        if (requestCheck) {
            //拿到参数
            needPermissions = getIntent().getStringArrayExtra(PERMISSION_KEY);
            if (mChecker.lackPermissions(needPermissions)) {
                //去获取权限
                requestPermissions();
            } else {
                allPermissionGranted();
            }
        }
        else {
            requestCheck = true;
        }
    }

    //请求权限，兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, needPermissions, PERMISSION_REQUEST_CODE);
    }

    private void allPermissionGranted() {
        Toast.makeText(this, "权限齐全", Toast.LENGTH_SHORT).show();
        setResult(RESULT_CODE, getIntent());
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("wangsongbin", "onPause requestcheck:" + requestCheck);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("wangsongbin", "onRequestPermissionsResult requestcheck:" + requestCheck);
        if (requestCode == PERMISSION_REQUEST_CODE && !mChecker.lackPermissions(permissions)) {
            allPermissionGranted();
            requestCheck = true;
        } else {
            requestCheck = false;//不走检测流程
            //自己弹窗，提醒授权
            showMissPermissionDialog();
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

    private void startAppSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    public static void startPermissionActivity(Activity mActivity, String... permissions) {
        Intent intent = new Intent(mActivity, PermissionActivity.class);
        intent.putExtra(PERMISSION_KEY, permissions);
        mActivity.startActivityForResult(intent, REQUEST_CODE);
    }
}
