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


    //是否显示小程序
    public boolean isShowMini = false;
    //是否显示显示设置权限
    public boolean isShowSetPermission = false;
    //是否显示删除
    public boolean isShowDel = false;
    //是否显示不感兴趣
    public boolean isShowNoInterest = false;



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
    //小程序 配置信息
    public ShareMiniInfo shareMiniInfo = new ShareMiniInfo();
    //设置权限 配置信息
    public ShareSetPermissionInfo shareSetPermissionInfo = new ShareSetPermissionInfo();
    //删除 配置信息
    public ShareDelInfo shareDelInfo = new ShareDelInfo();
    //不感兴趣 配置信息
    public ShareNoInterestInfo shareNoInterestInfo = new ShareNoInterestInfo();

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
    }


    //微信配置信息
    public static class ShareWXInfo {

        /** XShare内部处理微信相关分析, 默认内部处理 */
        public boolean isDealInInner = true;

        /** 内部调用 微信好友/朋友圈  true:朋友圈, false: 微信好友. 这个字段在外界不可调用，即使调用了也不起作用 */
        public boolean isShowWXCircle = false;

        /** 分享方式是 true:小程序, false: web */
        public boolean isShowWXMini = false;

        /**
         * 分享视频小程序路径。分享到微信好友会话中时候，分享的是 视频小程序 。 前提是 {@link #isShowWXMini } is true ，并且 {@link ShareInfo#isVideoShare} is true
         */
        public String miniPath4Video = "";

        /**
         * 分享图片小程序的路径。分享到微信好友会话中时候，分享的是 图片小程序。 前提是 {@link #isShowWXMini } is true ，并且 {@link ShareInfo#isPicShare} is true
         */
        public String miniPath4Pic = "";
        /**
         * 分享web小程序的路径。分享到微信好友会话中时候，分享的是 web小程序。 前提是 {@link #isShowWXMini } is true ，并且 {@link ShareInfo#isWebShare} is true
         */
        public String miniPath4Web = "";

    }

    //QQ配置信息
    public static class ShareQQInfo{
        /** XShare内部处理QQ相关分析, 默认内部处理 */
        public boolean isDealInInner = true;

        /** QQ/QQ空间  true:QQ空间, false: QQ. 这个字段在外界不可调用，即使调用了也不起作用 */
        public boolean isQQZone = false;
    }

    //举报配置信息
    public static class ShareToReport {
        public int REQUEST_CODE_REPORT = 10001;
    }

    //复制链接配置信息
    public static class ShareCopyLinkInfo {
        /** 复制链接的内容 */
        public String copyLink = "";
    }

    //拉黑配置信息
    public static class ShareBlackListInfo {
    }

    //海报配置信息
    public static class SharePosterInfo {
    }

    //私信好友配置信息
    public static class SharePrivateInfo {

    }

    //下载视频配置信息
    public static class ShareDownloadVideoInfo {
        /** XShare内部处理视频下载, 默认内部处理 */
        public boolean isDealInInner = true;
        /** 视频下载地址 */
        public String videoPath;
    }

    //下载图片配置信息
    public static class ShareDownloadPicInfo {
        /** XShare内部处理图片下载, 默认内部处理 */
        public boolean isDealInInner = true;
        /** 图片下载地址 */
        public String picPath;
    }

    //小程序配置信息
    public static class ShareMiniInfo {
        /** 小程序路径 */
        public String miniPath;
    }
    //设置权限配置信息
    public static class ShareSetPermissionInfo {

    }
    //删除配置信息
    public static class ShareDelInfo {

    }
    //不感兴趣配置信息
    public static class ShareNoInterestInfo {

    }
}
