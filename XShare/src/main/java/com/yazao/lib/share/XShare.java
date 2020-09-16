package com.yazao.lib.share;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;

import com.yazao.lib.share.bean.XShareBean;
import com.yazao.lib.share.listener.OnShareDialogClickListener;
import com.yazao.lib.share.util.WxUtils;
import com.yazao.lib.share.widget.ShareDialog;
import com.yazao.lib.toast.XToast;

import java.lang.ref.WeakReference;

/**
 * <p>
 * 分享模块调度中心</p>
 * <p>
 * 可配置分享界面属性（背景色值、Item元素显示顺序和类型）
 * </p>
 *
 *
 * <p>
 * 在调用的地方使用：
 * OnShareDialogClickListener listener = new SimpleShareDialogClickListenerImpl(){
 *
 * @Override public void onDialogWXClick() {
 * super.onDialogWXClick();
 * }
 * //可以选择需要对应方法
 * };
 * </p>
 * <p>
 * 显示Dialog的地方：
 * <p>
 * XShareBean shareBean = XShare.getInstance().getDefaultConfig(); //根据自己需求来配置
 * XShare.getInstance().init(getActivity()).setConfig(shareBean).setListener(listener).build();
 * XShare.getInstance().showDialog();
 * <p>
 * 需要处理 Activity.onActivityResult的 需要调用：
 * <p>
 * XShare.getInstance().onActivityResult(requestCode, resultCode, data);
 * </p>
 */
public class XShare {

    private WeakReference<Context> reference;
    private ShareDialog shareDialog;
    private OnShareDialogClickListener listener;
    private int columnNum;


    //******************************** 例模式 ********************************
    private static class SingleHolder {
        private static final XShare ourInstance = new XShare();
    }

    public static XShare getInstance() {
        return SingleHolder.ourInstance;
    }

    private XShare() {
    }


    //******************************** init ********************************
    public XShare init(Context context) {
        destroy();
        reference = new WeakReference<>(context);
        return this;
    }

    public Context getContext() {
        return (reference != null && reference.get() != null) ? reference.get() : null;
    }

    //******************************** 监听事件 ********************************
    public XShare setListener(OnShareDialogClickListener listener) {
        this.listener = listener;
        return this;
    }

    //******************************** 布局UI元素列数 ********************************
    public XShare setColumnNum(int columnNum) {
        this.columnNum = columnNum;
        return this;
    }

    //******************************** Activity回调 ********************************
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        checkDialog();
        shareDialog.onActivityResult(requestCode, resultCode, data);
    }

    //******************************** 权限申请 ********************************
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        checkDialog();
        shareDialog.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //******************************** 分享UI 元素显示 载体 ********************************
    //分享元素开关显示的承载体
    private XShareBean shareBean;

    public XShare setConfig(XShareBean shareBean) {
        this.shareBean = shareBean;
        return this;
    }

    /**
     * 仅显示 微信 和 朋友圈
     *
     * @return
     */
    public XShareBean getDefaultConfig() {
        XShareBean shareBean = new XShareBean();
        shareBean.isShowCancel = false;
        shareBean.isShowWX = true;
        shareBean.isShowWXCircle = true;
        shareBean.isShowQQ = false;
        shareBean.isShowQQZone = false;
        shareBean.isShowCopyLink = false;
        shareBean.isShowSharePoster = false;
        shareBean.isShowBlackList = false;
        shareBean.isShowPrivate = false;
        shareBean.isShowToReport = false;
        shareBean.isShowDownloadVideo = false;
        this.shareBean = shareBean;
        return shareBean;
    }

    //******************************** 构建Xshare ********************************
    public void build() {

        if (this.shareBean == null) {
            this.shareBean = getDefaultConfig();
        }

        if (shareDialog != null) {
            shareDialog.cancel();
            shareDialog = null;
        }

        shareDialog = new ShareDialog.Builder(getContext())
                .setLayoutId(R.layout.xshare_dialog_share_layout)
                .setGravity(Gravity.BOTTOM)
                .setCancelOnTouchOutside(true)
                .setData(shareBean)
                .setClickListener(listener)
                .setColumnNum(columnNum)
                .build();
    }

    //******************************** 显示Dialog ********************************

    public void checkDialog() {
        if (shareDialog == null) {
            build();
        }
    }

    public void showDialog() {

        checkDialog();

        if (!shareDialog.isShowing()) {
            shareDialog.show();
        }

    }

    public void hideDialog() {
        if (shareDialog != null && shareDialog.isShowing()) {
            shareDialog.dismiss();
        }
    }

    public void destroy() {
        if (reference != null) {
            reference.clear();
            reference = null;
        }

        if (listener != null){
            listener = null;
        }
    }
}
