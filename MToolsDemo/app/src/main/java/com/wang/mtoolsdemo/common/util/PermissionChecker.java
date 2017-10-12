package com.wang.mtoolsdemo.common.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by dell on 2017/10/12.
 */

public class PermissionChecker {

    private final Context context;

    public PermissionChecker(Context context) {
        this.context = context;
    }

    /**
     * 检查是否缺失权限
     *
     * @return
     */
    public boolean lackPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lackPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public boolean lackPermission(String permission) {
        //权限被拒
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
