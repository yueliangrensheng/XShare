package com.yazao.lib.share.config;

import android.content.Context;

import com.yazao.lib.share.util.WxUtils;
import com.yazao.lib.toast.XToast;

public class XShareConfig {
    /** 应用在QQ申请的APPId */
    public static String QQ_APP_ID = "";
    /** 小程序原始id */
    public static String WX_MINI_PROGRAM_ID = "";
    /** 应用在wx申请的APPId */
    public static String WX_APP_ID = "";

    /** 应用名称 */
    public static String APP_NAME = "";

    /**
     * @param context
     * @param appName            应用名称
     * @param appIdForWX         微信的 AppID
     * @param miniProgramIdForWX 微信小程序的 原始id
     * @param appIdForQQ         QQ的 AppID
     */
    public static void init(Context context, String appName, String appIdForWX, String miniProgramIdForWX, String appIdForQQ) {
        APP_NAME = appName;
        WX_APP_ID = appIdForWX;
        WX_MINI_PROGRAM_ID = miniProgramIdForWX;
        QQ_APP_ID = appIdForQQ;
        //wx init
        WxUtils.regToWx(context);//微信分享初始化
        //toast init
        XToast.init(context);
    }
}
