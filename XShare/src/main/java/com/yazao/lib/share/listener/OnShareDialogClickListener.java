package com.yazao.lib.share.listener;

public interface OnShareDialogClickListener {

    public static final int STATE_SUCCESS = 0;
    public static final int STATE_FAIL = 1;
    public static final int STATE_CANCEL = 2;

    /**
     * 微信 : 具体的逻辑已经实现，其实这里不需要做任何实现
     */
    void onDialogWXClick();

    /**
     * 朋友圈 : 具体的逻辑已经实现，其实这里不需要做任何实现
     */
    void onDialogWXCircleClick();

    /**
     * 取消 ：具体的逻辑已经实现，其实这里不需要做任何实现
     */
    void onDialogCancel();

    /**
     * 私信好友
     */
    void onDialogPrivateClick();

    /**
     * QQZone
     */
    void onDialogQQZoneClick(int state);

    /**
     * QQ
     */
    void onDialogQQClick(int state);

    /**
     * 举报
     */
    void onDialogReportClick();

    /**
     * 拉黑
     */
    void onDialogBlackListClick();

    /**
     * 分享海报
     */
    void onDialogSharePosterClick();

    /**
     * 复制链接
     */
    void onDialogCopyLinkClick();

    /**
     * 下载视频
     */
    void onDialogDownloadVideoClick();

    /**
     * 下载图片
     */
    void onDialogDownloadPicClick();
}
