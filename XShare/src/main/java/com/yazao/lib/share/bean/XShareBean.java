package com.yazao.lib.share.bean;

import java.util.Map;

/**
 * 分享UI显示Item配置以及Item相关数据配置
 *
 */
public class XShareBean {
    //是否显示取消按钮
    public boolean isShowCancel = true;
    //是否显示私信好友
    public boolean isShowPrivate = false;
    //是否显示微信
    public boolean isShowWX = true;
    //是否显示朋友圈
    public boolean isShowWXCircle = true;
    //是否显示QQ
    public boolean isShowQQ = false;
    //是否显示QQ空间
    public boolean isShowQQZone = false;
    //是否显示复制链接
    public boolean isShowCopyLink = false;
    //是否显示分享海报
    public boolean isShowSharePoster = false;
    //是否显示举报
    public boolean isShowToReport = false;
    //是否显示拉黑
    public boolean isShowBlackList = false;
    //是否显示下载视频
    public boolean isShowDownloadVideo = false;
    //是否显示下载图片
    public boolean isShowDownloadPic = false;


    //共有配置信息
    public ShareInfo shareInfo = new ShareInfo();

    //微信/朋友圈 配置信息
    public ShareWXInfo shareWXInfo = new ShareWXInfo();
    //QQ/QQZone 配置信息
    public ShareQQInfo shareQQInfo = new ShareQQInfo();
    //举报 配置信息
    public ShareToReport shareToReportInfo = new ShareToReport();
    //复制链接 配置信息
    public ShareCopyLinkInfo shareCopyLinkInfo = new ShareCopyLinkInfo();
    //拉黑 配置信息
    public ShareBlackListInfo shareBlackListInfo = new ShareBlackListInfo();
    //海报 配置信息
    public SharePosterInfo sharePosterInfo = new SharePosterInfo();
    //私信好友 配置信息
    public SharePrivateInfo sharePrivateInfo = new SharePrivateInfo();
    //下载视频 配置信息
    public ShareDownloadVideoInfo shareDownloadVideoInfo = new ShareDownloadVideoInfo();
    //下载图片 配置信息
    public ShareDownloadPicInfo shareDownloadPicInfo = new ShareDownloadPicInfo();

    //共有数据
    public static class ShareInfo {
        /** 封面Url */
        public String coverUrl = "";
        /** 链接Url */
        public String url = "";
        /** 标题 */
        public String title = "";
        /** 描述*/
        public String desc = "";

        /** 小视频分享 */
        public boolean isVideoShare = false;
        /** 图片分享 */
        public boolean isPicShare = false;
        /** Web分享 */
        public boolean isWebShare = false;

        /** 被分享者的 id . 如果 {@link #isVideoShare} is true,那么当前字段{@link #sharedId} 需要传值 */
        public String sharedId = "";
        /** 分享码 . 如果 {@link #isVideoShare} is true,那么当前字段{@link #reCode} 需要传值 */
        public String reCode = "";
    }


    //微信配置信息
    public static class ShareWXInfo {

        /** 内部调用 微信好友/朋友圈  true:朋友圈, false: 微信好友. 这个字段在外界不可调用，即使调用了也不起作用 */
        public boolean isShowWXCircle = false;

        /** 分享方式是 true:小程序, false: web */
        public boolean isShowWXMini = false;


        /** 分享到微信之前是否需要获取数据，如果当前 {@link #beforeRequest } is true,需要用到字段 {@link #paramsMap} 来传递参数 */
        public boolean beforeRequest = false;
        /** 分享到微信之前需要获取数据的请求参数列表.  如果{@link #beforeRequest } is false,那么当前{@link #paramsMap} 字段可以不传值*/
        public Map paramsMap;

    }

    //QQ配置信息
    public static class ShareQQInfo{
        /** QQ/QQ空间  true:QQ空间, false: QQ. 这个字段在外界不可调用，即使调用了也不起作用 */
        public boolean isQQZone = false;
    }

    //举报配置信息
    public static class ShareToReport {
        public int REQUEST_CODE_REPORT = 10001;
        /** 被举报者mid */
        public String reportedMid = "";
        /** 举报者mid */
        public String currentMid = "";
    }

    //复制链接配置信息
    public static class ShareCopyLinkInfo {
        /** 复制链接的内容 */
        public String copyLink = "";
    }

    //拉黑配置信息
    public static class ShareBlackListInfo {
        /** 拉黑者mid */
        public String currentMid = "";
        /** 被拉黑者mid */
        public String blockedMid = "";
    }

    //海报配置信息
    public static class SharePosterInfo {
        /** 分享前，是否请求必要的数据 */
        public boolean beforeRequest = true;
        /** {@link #beforeRequest} is true, 请求参数配置到{@link #paramsMap} */
        public Map paramsMap;
        /** 是否需要内部处理海报分享操作 */
        public boolean hasDeal = true;
    }

    //私信好友配置信息
    public static class SharePrivateInfo {

    }

    //下载视频配置信息
    public static class ShareDownloadVideoInfo {
        /** 视频下载地址 */
        public String videoPath;
    }

    //下载图片配置信息
    public static class ShareDownloadPicInfo {
        /** 图片下载地址 */
        public String picPath;
    }
}
