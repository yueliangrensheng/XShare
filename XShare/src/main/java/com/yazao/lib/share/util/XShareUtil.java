package com.yazao.lib.share.util;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.tauth.UiError;
import com.yazao.lib.share.R;
import com.yazao.lib.share.bean.XShareBean;
import com.yazao.lib.share.config.XShareConfig;
import com.yazao.lib.share.listener.OnShareDialogClickListener;
import com.yazao.lib.share.permission.PermissionConfig;
import com.yazao.lib.share.permission.PermissionUtils;
import com.yazao.lib.share.util.download.AndroidDownloadManager;
import com.yazao.lib.share.util.download.AndroidDownloadManagerListener;
import com.yazao.lib.share.widget.ShareDialog;
import com.yazao.lib.toast.XToast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * 具体的分享逻辑操作在这里
 */
public class XShareUtil {

    private OnShareDialogClickListener listener;

    public void registerListener(OnShareDialogClickListener listener) {
        this.listener = listener;
    }

    //******************************** 举报 ********************************


    /**
     * 跳转到举报页面
     *
     * @param activity
     * @param shareToReportInfo
     */
    public void report(Activity activity, XShareBean.ShareToReport shareToReportInfo) {
        if (this.listener != null) {
            this.listener.onDialogReportClick();
        }
    }

    //******************************** 微信分享 ********************************

    /**
     * 分享到微信好友
     *
     * @param shareBean
     */
    public void shareWX(Activity mContext, XShareBean shareBean) {

        if (shareBean == null) {
            return;
        }

        doWxShare(mContext, shareBean);
    }

    private void doWxShare(Activity mContext, XShareBean shareBean) {
        if (shareBean == null) {
            return;
        }
        XShareBean.ShareWXInfo shareWXInfo = shareBean.shareWXInfo;
        XShareBean.ShareInfo shareInfo = shareBean.shareInfo;

        String coverImg = shareInfo.coverUrl;
        String url = shareInfo.url;
        String title = shareInfo.title;
        String desc = shareInfo.desc;


        boolean isVideoShare = shareInfo.isVideoShare;
        boolean isPicShare = shareInfo.isPicShare;
        boolean isWebShare = shareInfo.isWebShare;

        String miniPath4Video = shareWXInfo.miniPath4Video;
        String miniPath4Pic = shareWXInfo.miniPath4Pic;
        String miniPath4Web = shareWXInfo.miniPath4Web;

        String uid = "";

        if (shareWXInfo.isShowWXCircle) {
            //朋友圈

            if (isPicShare) {
                //图片分享
                WxUtils.shareImageToWx(coverImg, SendMessageToWX.Req.WXSceneTimeline);
            } else if (isVideoShare) {
                // 视频分享

            } else if (isWebShare) {
                // web 分享
                WxUtils.shareWebToWx(url, title, desc, coverImg,
                        SendMessageToWX.Req.WXSceneTimeline);
            }


        } else {
            //微信好友

            if (shareWXInfo.isShowWXMini) {
                //小程序
                if (isVideoShare) {
                    //小程序 - 小视频
                    WxUtils.shareToWxMiniProgram(coverImg,
                            title,
                            desc,
                            miniPath4Video);
                } else if (isPicShare) {
                    //小程序 - 图片
                    WxUtils.shareToWxMiniProgram(coverImg,
                            title,
                            desc,
                            miniPath4Pic);
                } else if (isWebShare) {
                    //小程序 - web
                    WxUtils.shareToWxMiniProgram(coverImg,
                            title,
                            desc,
                            miniPath4Web);
                }

            } else {
                //非小程序 -- 图片/小视频/web链接

                if (isPicShare) {
                    //图片分享
                    WxUtils.shareImageToWx(coverImg, SendMessageToWX.Req.WXSceneSession);
                } else if (isVideoShare) {
                    // 视频分享

                } else if (isWebShare) {
                    // web 分享
                    WxUtils.shareWebToWx(url, title, desc, coverImg,
                            SendMessageToWX.Req.WXSceneSession);
                }

            }
        }
    }

    //******************************** 复制链接 ********************************

    /**
     * 复制链接
     *
     * @param context
     * @param shareCopyLinkInfo
     */
    public void copyLink(Activity context, XShareBean.ShareCopyLinkInfo shareCopyLinkInfo) {
        if (shareCopyLinkInfo == null) {
            return;
        }
        String url = shareCopyLinkInfo.copyLink;

        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText(XShareConfig.APP_NAME, url);
        cm.setPrimaryClip(mClipData);

        if (this.listener != null) {
            this.listener.onDialogCopyLinkClick();
        }
    }

    //******************************** 拉黑 ********************************

    /**
     * 拉黑
     *
     * @param activity
     * @param shareBlackListInfo
     */
    public void blackList(Activity activity, XShareBean.ShareBlackListInfo shareBlackListInfo) {
        if (shareBlackListInfo == null) {
            return;
        }

        if (this.listener != null) {
            this.listener.onDialogBlackListClick();
        }
    }

    //******************************** QQ ********************************

    public void shareQQ(Activity activity, XShareBean shareBean) {
        if (shareBean == null || activity == null) {
            return;
        }
        doQQShare(activity, shareBean);
    }

    private void doQQShare(Activity activity, XShareBean shareBean) {
        if (shareBean == null || activity == null) {
            return;
        }

        WxUtils.QQShareListener qqShareListener = new WxUtils.QQShareListener() {

            @Override
            public void onComplete(Object o) {
                if (listener != null) {
                    if (shareBean.shareQQInfo.isQQZone) {
                        listener.onDialogQQZoneClick(OnShareDialogClickListener.STATE_SUCCESS);
                    } else {
                        listener.onDialogQQClick(OnShareDialogClickListener.STATE_SUCCESS);
                    }
                }
            }

            @Override
            public void onError(UiError uiError) {

                if (listener != null) {
                    if (shareBean.shareQQInfo.isQQZone) {
                        listener.onDialogQQZoneClick(OnShareDialogClickListener.STATE_FAIL);
                    } else {
                        listener.onDialogQQClick(OnShareDialogClickListener.STATE_FAIL);
                    }
                }
            }

            @Override
            public void onCancel() {
                if (listener != null) {
                    if (shareBean.shareQQInfo.isQQZone) {
                        listener.onDialogQQZoneClick(OnShareDialogClickListener.STATE_CANCEL);
                    } else {
                        listener.onDialogQQClick(OnShareDialogClickListener.STATE_CANCEL);
                    }
                }
            }
        };

        XShareBean.ShareInfo shareInfo = shareBean.shareInfo;

        String coverImg = shareInfo.coverUrl;
        String url = shareInfo.url;
        String title = shareInfo.title;
        String desc = shareInfo.desc;

        boolean isQQZone = shareBean.shareQQInfo.isQQZone;

        if (isQQZone) {
            WxUtils.shareToQQZone(activity, url, title, desc, coverImg, qqShareListener);
        } else {
            WxUtils.shareToQQFriend(activity, url, title, desc, coverImg, qqShareListener);
        }


    }


    //******************************** 海报 ********************************

    /**
     * 海报
     *
     * @param activity
     * @param shareBean
     */
    public void poster(Activity activity, XShareBean shareBean) {
        if (activity == null || shareBean == null) {
            return;
        }

        if (this.listener != null) {
            this.listener.onDialogSharePosterClick();
        }
    }

    //******************************** 私信好友 ********************************

    /**
     * 私信好友
     *
     * @param shareBean
     */
    public void privater(XShareBean shareBean) {
        if (shareBean == null) {
            return;
        }

        if (this.listener != null) {
            this.listener.onDialogPrivateClick();
        }
    }

    //******************************** 下载视频 ********************************
    public void downloadVideo(Activity activity, XShareBean shareBean) {
        if (shareBean == null) {
            return;
        }

        String videoPath = shareBean.shareDownloadVideoInfo.videoPath;

        if (TextUtils.isEmpty(videoPath)) {
            XToast.show(R.string.xshare_video_path_empty);
            return;
        }

        //读写权限申请
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean checkResult = PermissionUtils.checkPermissionsGroup(activity, permission);
        if (!checkResult) {
            XToast.show(R.string.xshare_permission_no_write);
            PermissionConfig.showPermissionDialog(activity, PermissionConfig.MESSAGE_WRITE_EXTERNAL_PERMISSION);
        } else {
            if (flag) {
                XToast.show(R.string.xshare_video_download_begin);
                flag = false;
            } else {
                XToast.show(R.string.xshare_video_downloading);
            }

            doDownloadVideo(activity, videoPath);
        }
    }

    private static boolean flag = true;

    private void doDownloadVideo(final Activity activity, String video_path) {
        try {
            new AndroidDownloadManager(activity, video_path)
                    .setListener(new AndroidDownloadManagerListener() {
                        @Override
                        public void onPrepare() {
                            Log.d("downloadVideo", "onPrepare");
                        }

                        @Override
                        public void onSuccess(String path) {
                            XToast.show(R.string.xshare_video_download_success);
                            saveVideo(activity, new File(path));
                            flag = true;
                            Log.d("downloadVideo", "onSuccess >>>>" + path);
                        }

                        @Override
                        public void onFailed(Throwable throwable) {
                            XToast.show(R.string.xshare_video_download_failed);
                            Log.e("downloadVideo", "onFailed", throwable);
                            flag = true;
                        }
                    })
                    .download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveVideo(Activity activity, File file) {
        //是否添加到相册
        try {
            ContentResolver localContentResolver = activity.getContentResolver();
            ContentValues localContentValues = getVideoContentValues(activity, file, System.currentTimeMillis());
            Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put(MediaStore.Video.Media.TITLE, paramFile.getName());
        localContentValues.put(MediaStore.Video.Media.DISPLAY_NAME, paramFile.getName());
        localContentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        localContentValues.put(MediaStore.Video.Media.DATE_TAKEN, Long.valueOf(paramLong));
        localContentValues.put(MediaStore.Video.Media.DATE_MODIFIED, Long.valueOf(paramLong));
        localContentValues.put(MediaStore.Video.Media.DATE_ADDED, Long.valueOf(paramLong));
        localContentValues.put(MediaStore.Video.Media.DATA, paramFile.getAbsolutePath());
        localContentValues.put(MediaStore.Video.Media.SIZE, Long.valueOf(paramFile.length()));
        return localContentValues;
    }

    //******************************** 下载图片 ********************************
    public void downloadPic(Activity activity, XShareBean shareBean) {
        if (shareBean == null) {
            return;
        }

        //读写权限申请
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean checkResult = PermissionUtils.checkPermissionsGroup(activity, permission);
        if (!checkResult) {
            XToast.show(R.string.xshare_permission_no_write);
            PermissionConfig.showPermissionDialog(activity, PermissionConfig.MESSAGE_WRITE_EXTERNAL_PERMISSION);
        } else {
            String sharePosterUrl = shareBean.shareDownloadPicInfo.picPath;
            SaveImageUtils.toSave(activity, sharePosterUrl);
        }


    }

    //******************************** 小程序 ********************************

    public void mini(Context context, XShareBean shareBean) {

        if (listener != null) {
            listener.onDialogMiniClick(-1);
        }
    }

    //******************************** 删除 ********************************
    public void del(Activity activity, XShareBean shareBean) {

        if (listener != null) {
            listener.onDialogDelClick();
        }
    }

    //******************************** 设置权限 ********************************
    public void setPermission(Activity activity, XShareBean shareBean) {
        if (listener != null) {
            listener.onDialogSetPermissionClick();
        }
    }

    //******************************** 不感兴趣 ********************************
    public void noInterest(Activity activity, XShareBean shareBean) {

        if (listener != null) {
            listener.onDialogNoInterestClick();
        }
    }


    //******************************** 单例模式 ********************************
    private static class SingleHolder {
        private static final XShareUtil ourInstance = new XShareUtil();
    }

    public static XShareUtil getInstance() {
        return XShareUtil.SingleHolder.ourInstance;
    }

    private XShareUtil() {
    }


}
