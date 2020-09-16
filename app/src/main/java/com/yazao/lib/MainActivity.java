package com.yazao.lib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yazao.lib.share.XShare;
import com.yazao.lib.share.bean.XShareBean;
import com.yazao.lib.share.config.XShareConfig;
import com.yazao.lib.share.demo.R;
import com.yazao.lib.share.listener.OnShareDialogClickListener;
import com.yazao.lib.share.listener.impl.SimpleShareDialogClickListenerImpl;
import com.yazao.lib.toast.XToast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2.init share component
        XShare.getInstance().init(this);

        //3.config UI & data
        //设置分享UI显示元素
        XShareBean shareBean = new XShareBean();
        shareBean.isShowPrivate = false;
        shareBean.isShowWX = true;
        shareBean.isShowWXCircle = true;
        shareBean.isShowQQ = true;
        shareBean.isShowQQZone = true;
        shareBean.isShowToReport = true;
        shareBean.isShowBlackList = false;
        shareBean.isShowCopyLink = true;
        shareBean.isShowSharePoster = false;
        shareBean.isShowNoInterest = true;

        //设置共有数据
        shareBean.shareInfo.coverUrl = "https://github.com/yueliangrensheng/yueliangrensheng.github.io/blob/master/img/about-author.jpg";
        shareBean.shareInfo.title = "title";
        shareBean.shareInfo.desc = "desc";
        shareBean.shareInfo.url = "https://github.com/yueliangrensheng";
        shareBean.shareInfo.isVideoShare = false;
        shareBean.shareInfo.isPicShare = true;
        shareBean.shareInfo.isWebShare = false;


        //设置私信好友数据

        //设置微信数据
        //设置微信朋友圈数据
        shareBean.shareWXInfo.isDealInInner = true;//默认true，XShare内部处理微信分享Action
        shareBean.shareWXInfo.isShowWXMini = false;//分享类型是否是 小程序
        shareBean.shareWXInfo.miniPath4Pic = "";// 分享图片小程序的 路径
        shareBean.shareWXInfo.miniPath4Video = "";// 分享视频小程序的 路径
        shareBean.shareWXInfo.miniPath4Web = "";// 分享web小程序的 路径


        //设置QQ数据
        //设置QQZone数据
        shareBean.shareQQInfo.isDealInInner = true;//默认true，XShare内部处理QQ分享Action


        //设置复制链接数据
        shareBean.shareCopyLinkInfo.copyLink = "";

        //设置举报数据
        //设置拉黑数据
        //设置分享海报数据


        //4.设置数据
        XShare.getInstance().setConfig(shareBean).setListener(listener).build();


        findViewById(R.id.textClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 4.show dialog
                XShare.getInstance().showDialog();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 5.xshare deal with permission
        XShare.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 6.xshare deal with onActivityResult
        XShare.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    private OnShareDialogClickListener listener = new SimpleShareDialogClickListenerImpl() {
        @Override
        public void onDialogWXClick() {
            super.onDialogWXClick();
        }

        @Override
        public void onDialogQQZoneClick(int state) {
            onDialogQQClick(state);
        }

        @Override
        public void onDialogQQClick(int state) {
            switch (state) {
                case OnShareDialogClickListener.STATE_SUCCESS:
                    XToast.showLong("分享成功");
                    break;
                case OnShareDialogClickListener.STATE_FAIL:
                    XToast.showLong("分享失败");
                    break;
                case OnShareDialogClickListener.STATE_CANCEL:
                    XToast.showLong("分享取消");
                    break;
                case OnShareDialogClickListener.STATE_NO_ACTION://这里处理点击事件
                    XToast.showLong("do action here");
                    break;
            }
        }

        @Override
        public void onDialogBlackListClick() {
            super.onDialogBlackListClick();
            XToast.showLong("拉黑成功");
        }

        @Override
        public void onDialogReportClick() {
            super.onDialogReportClick();
            XToast.showLong("举报成功，我们将尽快处理");
        }

        @Override
        public void onDialogCopyLinkClick() {
            super.onDialogCopyLinkClick();
            XToast.showLong("复制成功");
        }

        @Override
        public void onDialogNoInterestClick() {
            super.onDialogNoInterestClick();
            XToast.showLong("我们将减少此类型推送");
        }
    };
}
