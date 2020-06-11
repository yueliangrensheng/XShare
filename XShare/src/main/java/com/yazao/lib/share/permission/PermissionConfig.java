package com.yazao.lib.share.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.yazao.lib.share.R;
import com.yazao.lib.share.XShare;

/**
 * 权限相关配置信息
 */
public class PermissionConfig {

    public static final int PERMISSION_REQUEST_CODE = 0x1001;
    public static final String MESSAGE_WRITE_EXTERNAL_PERMISSION = XShare.getInstance().getContext().getApplicationContext().getResources().getString(R.string.xshare_permission_write_info);

    /**
     * 系统授权设置的弹框
     *
     * @param activity
     */
    public static void showPermissionDialog(final Activity activity, String message) {
        AlertDialog openAppDetDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setPositiveButton(XShare.getInstance().getContext().getApplicationContext().getString(R.string.xshare_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                activity.startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton(XShare.getInstance().getContext().getApplicationContext().getString(R.string.xshare_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        if (null == openAppDetDialog) {
            openAppDetDialog = builder.create();
        }
        if (null != openAppDetDialog && !openAppDetDialog.isShowing()) {
            openAppDetDialog.show();
        }
    }
}
