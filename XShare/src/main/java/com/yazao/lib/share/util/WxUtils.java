package com.yazao.lib.share.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yazao.lib.share.R;
import com.yazao.lib.share.XShare;
import com.yazao.lib.share.config.XShareConfig;
import com.yazao.lib.toast.XToast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 微信工具
 */
public class WxUtils {

    public static final String PACKAGE_WE_CHAT = "com.tencent.mm";
    public static final String PACKAGE_QQ = "com.tencent.mobileqq";
    public static final String PACKAGE_QQ_LITE = "com.tencent.qqlite";
    public static final String PACKAGE_WEI_BO = "com.sina.weibo";

    public static IWXAPI api;
    /**
     * 获取api
     *
     * @return
     */
    public static IWXAPI getApi() {
        return api;
    }
    /**
     * 初始化wx工具
     *
     * @param context
     */
    public static void regToWx(Context context) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, null);
        // 将应用的appId注册到微信
        api.registerApp(XShareConfig.WX_APP_ID);
    }

    private static boolean checkWX() {
        if (api == null) {
            throw new RuntimeException("please regToWx(context) first");
        }

        return isAvailable(XShare.getInstance().getContext().getApplicationContext(), PACKAGE_WE_CHAT);
    }

    /**
     * 跳转到小程序
     *
     * @param path 小程序路径  "page/xxx/details?xx"
     */
    public static void launchMiniProgram(String path) {
        if (!checkWX()) {
            XToast.show(R.string.xshare_no_wx_info);
            return;
        }
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = XShareConfig.WX_MINI_PROGRAM_ID; // 填小程序原始id
        req.path = path;// 拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }

    public static boolean isAvailable(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> info = packageManager.getInstalledPackages(0);
        for (int i = 0; i < info.size(); i++) {
            if (info.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 判断是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isAvailableWeChat(Context context) {
        boolean isAvailable = isAvailable(context, PACKAGE_WE_CHAT);
        if (!isAvailable) {
            XToast.showLong("您还没有安装微信，请先安装微信客户端");
        }
        return isAvailable;
    }

    /**
     * 分享图片到微信
     *
     * @param imgUrl
     * @param targetScene SendMessageToWX.Req.WXSceneSession 会话  SendMessageToWX.Req.WXSceneTimeline 朋友圈
     */
    public static void shareImageToWx(String imgUrl, int targetScene) {
        if (!checkWX()) {
            XToast.show(R.string.xshare_no_wx_info);
            return;
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            //初始化 WXImageObject 和 WXMediaMessage 对象
            new Thread(() -> {
                Bitmap bmp = null;
                WXMediaMessage msg = new WXMediaMessage();
                try {
                    bmp = Glide.with(XShare.getInstance().getContext().getApplicationContext())
                            .asBitmap()
                            .load(imgUrl)
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    WXImageObject wxImageObject = new WXImageObject(bmp);
                    msg.mediaObject = wxImageObject;
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, 80, false);
                    msg.thumbData = bmpToByteArray(thumbBmp, true);//封面图片，小于128k
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = targetScene;

                //调用api接口，发送数据到微信
                api.sendReq(req);
            }).start();

        } else {
            XToast.show("分享图片地址不能为空");
        }
    }

    /**
     * 以web方式分享
     *
     * @param url         链接url
     * @param title       标题
     * @param desc        描述
     * @param imgUrl      封面url
     * @param targetScene 朋友圈： SendMessageToWX.Req.WXSceneTimeline ； 微信会话：SendMessageToWX.Req.WXSceneSession
     */
    public static void shareWebToWx(String url, String title, String desc, String imgUrl, int targetScene) {
        new Thread(() -> {
            if (!checkWX()) {
                XToast.show(R.string.xshare_no_wx_info);
                return;
            }
            //初始化一个WXWebpageObject，填写url
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;

            //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = desc;
            Bitmap bmp = null;
            try {
                bmp = Glide.with(XShare.getInstance().getContext().getApplicationContext())
                        .asBitmap()
                        .load(imgUrl)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 80, 80, false);
                msg.thumbData = bmpToByteArray(thumbBmp, true);//封面图片，小于128k
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = targetScene;

            //调用api接口，发送数据到微信
            api.sendReq(req);
        }).start();
    }

    private static boolean checkQQ(Activity activity) {
        if (TextUtils.isEmpty(XShareConfig.QQ_APP_ID)) {
            throw new RuntimeException("please set value: XShareConfig.QQ_APP_ID");
        }
        return isAvailable(activity, PACKAGE_QQ) || isAvailable(activity, PACKAGE_QQ_LITE);
    }

    public static void shareToQQFriend(Activity activity, String url, String title, String content, String imgUrl, QQShareListener qqShareListener) {
        if (!checkQQ(activity)) {
            XToast.show(R.string.xshare_no_qq_info);
            return;
        }
        if (qqShareListener == null) {
            qqShareListener = new QQShareListener();
        }

        Tencent tencent = Tencent.createInstance(XShareConfig.QQ_APP_ID, activity.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, XShareConfig.APP_NAME);
        tencent.shareToQQ(activity, params, qqShareListener);
    }

    public static void shareToQQZone(Activity activity, String url, String title, String content, String imgUrl, QQShareListener qqShareListener) {
        if (!checkQQ(activity)) {
            XToast.show(R.string.xshare_no_qq_info);
            return;
        }
        if (qqShareListener == null) {
            qqShareListener = new QQShareListener();
        }

        Tencent tencent = Tencent.createInstance(XShareConfig.QQ_APP_ID, activity.getApplicationContext());
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);

        ArrayList<String> imgUrls = new ArrayList<>();
        imgUrls.add(imgUrl);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrls);
        tencent.shareToQQ(activity, params, qqShareListener);
    }

    static class QQShareListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            XToast.showLong("分享成功");
        }

        @Override
        public void onError(UiError uiError) {
            XToast.showLong("分享失败[" + uiError.errorCode + " " + uiError.errorMessage + "]");
        }

        @Override
        public void onCancel() {
            XToast.showLong("分享取消");
        }
    }

    /**
     * 分享到微信小程序
     *
     * @param imgUrl 封面
     * @param title  标题
     * @param desc   描述
     * @param path   小程序页面路径 例如 AppConstant.WX_MINI_ACCOUNT_SHARE_PATH + 需要的参数
     */
    public static void shareToWxMiniProgram(String imgUrl, String title, String desc, String path) {
        if (!checkWX()) {
            XToast.show(R.string.xshare_no_wx_info);
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "https://www.hqylh.com/"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = XShareConfig.WX_MINI_PROGRAM_ID;     // 小程序原始id
        miniProgramObj.path = path;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = desc;               // 小程序消息desc

        new Thread(() -> {
            Bitmap bmp = null;
            try {
                bmp = Glide.with(XShare.getInstance().getContext().getApplicationContext())
                        .asBitmap()
                        .load(imgUrl)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
//                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 415, 332, false);
                // bmp.recycle();
                if (bmp == null) {
                    XToast.show(R.string.xshare_error_get_share);
                    return;
                }
                Bitmap thumbBmp = BitmapUtils.setBitmapSize(bmp, 350, 350);
                msg.thumbData = bmpToByteArray(thumbBmp, true);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("miniProgram");
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
            api.sendReq(req);
        }).start();

    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                e.printStackTrace();
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

    }

    /**
     * 微信登录
     */
    public static void wxLogin() {
        if (!checkWX()) {
            XToast.show(R.string.xshare_no_wx_info);
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }


    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
