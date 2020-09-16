package com.yazao.lib.share.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.yazao.lib.share.R;
import com.yazao.lib.share.util.XShareUtil;
import com.yazao.lib.share.bean.XShareBean;
import com.yazao.lib.share.bean.XShareItemBean;
import com.yazao.lib.share.listener.OnShareDialogClickListener;
import com.yazao.lib.share.permission.PermissionConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.yazao.lib.share.listener.OnShareDialogClickListener.STATE_NO_ACTION;

public class ShareDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    private int layoutId = R.layout.xshare_dialog_share_layout;
    private int gravity = Gravity.CENTER;
    private boolean isCancelOnTouchOutside = false;//外部点击取消 默认不可取消
    private XShareBean shareBean;//Item数据
    private OnShareDialogClickListener listener;
    private Activity activity;
    private GridView mRootLayout;
    private ListAdapter adapter;
    private int columnNum = 4;//一行几个元素
    private List<XShareItemBean> data = new ArrayList<>();

    public ShareDialog(@NonNull Context context, int layoutId, int gravity, boolean isCancelOnTouchOutside, OnShareDialogClickListener listener) {
        super(context, R.style.XShareBottomSheetDialog);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        this.layoutId = layoutId;
        this.gravity = gravity;
        this.isCancelOnTouchOutside = isCancelOnTouchOutside;
        this.listener = listener;
    }

    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutId);
        initConfig();
        setCanceledOnTouchOutside(isCancelOnTouchOutside);
        initView();
    }

    private void initView() {
        mRootLayout = findViewById(R.id.xshare_dialog_share_gridview);
        adapter = new DialogShareAdapter(data);
        mRootLayout.setAdapter(adapter);

        mRootLayout.setNumColumns(columnNum);
        mRootLayout.setSelector(android.R.color.transparent);

        mRootLayout.setOnItemClickListener(this);


        if (shareBean == null) {
            return;
        }
        boolean isShowCancel = shareBean.isShowCancel;

        boolean isShowWX = shareBean.isShowWX;
        boolean isShowWXCircle = shareBean.isShowWXCircle;

        boolean isShowQQ = shareBean.isShowQQ;
        boolean isShowQQZone = shareBean.isShowQQZone;

        boolean isShowPrivate = shareBean.isShowPrivate;

        boolean isShowToReport = shareBean.isShowToReport;
        boolean isShowBlackList = shareBean.isShowBlackList;
        boolean isShowCopyLink = shareBean.isShowCopyLink;
        boolean isShowSharePoster = shareBean.isShowSharePoster;
        boolean isShowDownloadVideo = shareBean.isShowDownloadVideo;
        boolean isShowDownloadPic = shareBean.isShowDownloadPic;
        boolean isShowMini = shareBean.isShowMini;
        boolean isShowSetPermission = shareBean.isShowSetPermission;
        boolean isShowDel = shareBean.isShowDel;
        boolean isShowNoInterest = shareBean.isShowNoInterest;

        XShareItemBean itemBean = null;

        findViewById(R.id.xshare_cancel).setVisibility(isShowCancel ? View.VISIBLE : View.GONE);
        findViewById(R.id.xshare_item_divider).setVisibility(isShowCancel ? View.VISIBLE : View.GONE);
        findViewById(R.id.xshare_cancel).setOnClickListener(this);

        if (isShowPrivate) {
            itemBean = new XShareItemBean(R.mipmap.xshare_private, "私信好友", 0);
            data.add(itemBean);
        }
        if (isShowMini) {
            itemBean = new XShareItemBean(R.mipmap.xshare_mini, "小程序", 11);
            data.add(itemBean);
        }
        if (isShowWX) {
            itemBean = new XShareItemBean(R.mipmap.xshare_winxin, "微信", 1);
            data.add(itemBean);
        }
        if (isShowWXCircle) {
            itemBean = new XShareItemBean(R.mipmap.xshare_weixin_circle, "朋友圈", 2);
            data.add(itemBean);
        }

        if (isShowQQ) {
            itemBean = new XShareItemBean(R.mipmap.xshare_qq, "QQ", 3);
            data.add(itemBean);
        }
        if (isShowQQZone) {
            itemBean = new XShareItemBean(R.mipmap.xshare_zone, "QQ空间", 4);
            data.add(itemBean);
        }
        if (isShowSharePoster) {
            itemBean = new XShareItemBean(R.mipmap.xshare_poster_black, "分享海报", 8);
            data.add(itemBean);
        }
        if (isShowDownloadVideo) {
            itemBean = new XShareItemBean(R.mipmap.xshare_save_photo, "下载视频", 9);
            data.add(itemBean);
        }
        if (isShowDownloadPic) {
            itemBean = new XShareItemBean(R.mipmap.xshare_save_photo, "保存图片", 10);
            data.add(itemBean);
        }
        if (isShowNoInterest) {
            itemBean = new XShareItemBean(R.mipmap.xshare_no_inst, "不感兴趣", 14);
            data.add(itemBean);
        }
        if (isShowCopyLink) {
            itemBean = new XShareItemBean(R.mipmap.xshare_connect, "复制链接", 7);
            data.add(itemBean);
        }
        if (isShowToReport) {
            itemBean = new XShareItemBean(R.mipmap.xshare_report, "举报", 5);
            data.add(itemBean);
        }
        if (isShowBlackList) {
            itemBean = new XShareItemBean(R.mipmap.xshare_black, "拉黑", 6);
            data.add(itemBean);
        }
        if (isShowSetPermission) {
            itemBean = new XShareItemBean(R.mipmap.xshare_set_permission, "设置权限", 12);
            data.add(itemBean);
        }
        if (isShowDel) {
            itemBean = new XShareItemBean(R.mipmap.xshare_delete, "删除", 13);
            data.add(itemBean);
        }


    }

    private void initConfig() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        window.setGravity(gravity);

        if (gravity == Gravity.BOTTOM) {

            WindowManager windowManager = window.getWindowManager();
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = dm.widthPixels;//设置Dialog的宽度为屏幕宽度
            window.setAttributes(layoutParams);

            window.getDecorView().setPadding(0, 0, 0, 0);
        }
    }


    private void setData(XShareBean shareBean) {
        this.shareBean = shareBean;
    }

    private void setIsCanceledOnTouchOutside(boolean isCancelOnTouchOutside) {
        this.isCancelOnTouchOutside = isCancelOnTouchOutside;
    }

    private void setListener(OnShareDialogClickListener listener) {
        this.listener = listener;
        XShareUtil.getInstance().registerListener(listener);
    }

    public void setColumnNum(int columnNum) {
        if (columnNum <= 0) {
            return;
        }

        this.columnNum = columnNum;
    }

    @Override
    public void onClick(View v) {
        dismiss();

        if (v.getId() == R.id.xshare_cancel) {//取消
            if (this.listener != null) {
                this.listener.onDialogCancel();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.shareBean.shareToReportInfo.REQUEST_CODE_REPORT && resultCode == Activity.RESULT_OK) {
            //举报回调
            if (this.listener != null && this.shareBean != null) {
                this.listener.onDialogReportClick();
            }
        }
    }

    //权限申请
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PermissionConfig.PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                PermissionConfig.showPermissionDialog(activity, PermissionConfig.MESSAGE_WRITE_EXTERNAL_PERMISSION);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();

        XShareItemBean itemBean = this.data.get(position);
        if (itemBean == null) {
            return;
        }

        switch (itemBean.getFlag()) {

            case 0://私信好友
                if (this.shareBean.sharePrivateInfo != null) {
                    XShareUtil.getInstance().privater(this.shareBean);
                }
                break;
            case 1://微信

                if (this.shareBean.shareWXInfo != null) {

                    if (this.shareBean.shareWXInfo.isDealInInner) {//内部处理微信分享
                        this.shareBean.shareWXInfo.isShowWXCircle = false;
                        XShareUtil.getInstance().shareWX(activity, this.shareBean);
                    } else {
                        if (this.listener != null) {
                            this.listener.onDialogWXClick();
                        }
                    }
                }

                break;
            case 2://朋友圈

                if (this.shareBean.shareWXInfo != null) {
                    if (this.shareBean.shareWXInfo.isDealInInner) {//内部处理微信分享
                        this.shareBean.shareWXInfo.isShowWXCircle = true;
                        XShareUtil.getInstance().shareWX(activity, this.shareBean);
                    } else {
                        if (this.listener != null) {
                            this.listener.onDialogWXCircleClick();
                        }
                    }
                }

                break;
            case 3://QQ

                if (this.shareBean.shareQQInfo != null) {
                    if (this.shareBean.shareQQInfo.isDealInInner) {
                        this.shareBean.shareQQInfo.isQQZone = false;
                        XShareUtil.getInstance().shareQQ(activity, this.shareBean);
                    } else {
                        if (this.listener != null) {
                            this.listener.onDialogQQClick(STATE_NO_ACTION);
                        }
                    }
                }
                break;
            case 4://QQZone
                if (this.shareBean.shareQQInfo != null) {
                    if (this.shareBean.shareQQInfo.isDealInInner) {
                        this.shareBean.shareQQInfo.isQQZone = true;
                        XShareUtil.getInstance().shareQQ(activity, this.shareBean);
                    } else {
                        if (this.listener != null) {
                            this.listener.onDialogQQZoneClick(STATE_NO_ACTION);
                        }
                    }
                }
                break;
            case 5://举报
                if (this.shareBean.shareToReportInfo != null) {
                    XShareUtil.getInstance().report(activity, this.shareBean.shareToReportInfo);
                }
                break;
            case 6://拉黑
                if (this.shareBean.shareBlackListInfo != null) {
                    XShareUtil.getInstance().blackList(activity, this.shareBean.shareBlackListInfo);
                }
                break;
            case 7://复制链接
                if (this.shareBean.shareCopyLinkInfo != null) {
                    XShareUtil.getInstance().copyLink(activity, this.shareBean.shareCopyLinkInfo);
                }
                break;
            case 8://分享海报
                if (this.shareBean.sharePosterInfo != null) {
                    XShareUtil.getInstance().poster(activity, this.shareBean);
                }
                break;
            case 9://分享下载视频
                if (this.shareBean.shareDownloadVideoInfo != null) {
                    if (this.shareBean.shareDownloadVideoInfo.isDealInInner) {
                        XShareUtil.getInstance().downloadVideo(activity, this.shareBean);
                    } else {
                        if (this.listener != null) {
                            this.listener.onDialogDownloadVideoClick();
                        }
                    }
                }
                break;
            case 10://分享下载图片
                if (this.shareBean.shareDownloadPicInfo != null) {
                    if (this.shareBean.shareDownloadVideoInfo.isDealInInner) {
                        XShareUtil.getInstance().downloadPic(activity, this.shareBean);
                    } else {
                        if (this.listener != null) {
                            this.listener.onDialogDownloadPicClick();
                        }
                    }
                }
                break;
            case 11://小程序

                if (this.shareBean.shareMiniInfo != null) {
                    XShareUtil.getInstance().mini(activity, this.shareBean);
                }
                break;
            case 12://设置权限
                if (this.shareBean.shareSetPermissionInfo != null) {
                    XShareUtil.getInstance().setPermission(activity, this.shareBean);
                }
                break;
            case 13://删除
                if (this.shareBean.shareDelInfo != null) {
                    XShareUtil.getInstance().del(activity, this.shareBean);
                }
                break;
            case 14://不感兴趣
                if (this.shareBean.shareNoInterestInfo != null) {
                    XShareUtil.getInstance().noInterest(activity, this.shareBean);
                }
                break;
        }
    }

    public static class Builder {

        private WeakReference<Context> reference;
        Context mContext;

        ShareDialog shareDialog;

        private int layoutId = 0;
        private int gravity = Gravity.CENTER;
        private boolean isCancelOnTouchOutside = false;
        private XShareBean shareBean = null;
        private OnShareDialogClickListener listener;
        private int columnNum;


        public Builder(Context mContext) {
            if (reference != null) {
                reference.clear();
                reference = null;
            }

            reference = new WeakReference<>(mContext);
            this.mContext = mContext;
        }


        public Builder setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            isCancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public Builder setData(XShareBean shareBean) {
            this.shareBean = shareBean;
            return this;
        }

        public Builder setClickListener(OnShareDialogClickListener listener) {
            this.listener = listener;
            return this;
        }


        public Builder setColumnNum(int columnNum) {
            this.columnNum = columnNum;
            return this;
        }


        public ShareDialog build() {
            if (shareDialog == null) {
                shareDialog = new ShareDialog(mContext, layoutId, gravity, isCancelOnTouchOutside, listener);
            }

            shareDialog.setData(shareBean);
            shareDialog.setListener(listener);
            shareDialog.setIsCanceledOnTouchOutside(isCancelOnTouchOutside);
            shareDialog.setColumnNum(columnNum);
            shareDialog.create();
            return shareDialog;
        }

    }

    private class DialogShareAdapter extends BaseAdapter {
        List<XShareItemBean> data = new ArrayList<>();

        public DialogShareAdapter(List<XShareItemBean> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public Object getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.xshare_dialog_share_item_layout, null);
                holder = new ViewHolder();
                holder.contentTV = convertView.findViewById(R.id.xshare_item_share_dialog_content);
                holder.iconImage = convertView.findViewById(R.id.xshare_item_share_dialog_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.contentTV.setText(this.data.get(position).getContent());
            holder.iconImage.setImageResource(this.data.get(position).getIconResId());

            return convertView;
        }

        class ViewHolder {
            //图标资源Id
            public ImageView iconImage;
            //文案
            public TextView contentTV;
        }
    }
}
