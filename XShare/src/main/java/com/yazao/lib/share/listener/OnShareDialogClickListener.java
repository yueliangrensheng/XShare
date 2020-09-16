package com.yazao.lib.share.listener;

public interface OnShareDialogClickListener {

    public static final int STATE_SUCCESS = 0;
    public static final int STATE_FAIL = 1;
    public static final int STATE_CANCEL = 2;
    public static final int STATE_NO_ACTION = 3;// XShare内部不处理点击事件

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

    /**
     * 小程序
     *
     * @param session 0:微信会话 1：朋友圈 -1:XShare内部不处理具体事务
     */
    void onDialogMiniClick(int session);

    /**
     * 删除
     */
    void onDialogDelClick();

    /**
     * 设置权限
     */
    void onDialogSetPermissionClick();

    /**
     * 不感兴趣
     */
    void onDialogNoInterestClick();
}
