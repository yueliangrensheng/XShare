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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.init share component
        XShare.getInstance().init(this);

        //2.config share component
        //config app name
        XShareConfig.APP_NAME = getResources().getString(R.string.app_name);
        //config wx appId if need wx share
        XShareConfig.WX_APP_ID = "";
        //config wx mini programId if need wxmin share
        XShareConfig.WX_MINI_PROGRAM_ID = "";
        //config qq appId if need qq share
        XShareConfig.QQ_APP_ID = "1110598732";


        //3.config UI & data
        //设置分享UI显示元素
        XShareBean shareBean = new XShareBean();
        shareBean.isShowPrivate = false;
        shareBean.isShowWX = false;
        shareBean.isShowWXCircle = false;
        shareBean.isShowQQ = true;
        shareBean.isShowQQZone = true;
        shareBean.isShowToReport = true;
        shareBean.isShowBlackList = false;
        shareBean.isShowCopyLink = true;
        shareBean.isShowSharePoster = false;

        //设置共有数据
        shareBean.shareInfo.coverUrl = "https://github.com/yueliangrensheng/yueliangrensheng.github.io/blob/master/img/about-author.jpg";
        shareBean.shareInfo.title = "title";
        shareBean.shareInfo.desc = "desc";
        shareBean.shareInfo.url = "https://github.com/yueliangrensheng";

        shareBean.shareInfo.isVideoShare = false;
        shareBean.shareInfo.sharedId = "1001";
        shareBean.shareInfo.reCode = "2020";

        shareBean.shareInfo.isPicShare = false;
        shareBean.shareInfo.isWebShare = false;


        //设置私信好友数据

        //设置微信数据
        //设置微信朋友圈数据
        shareBean.shareWXInfo.isShowWXMini = true;


        //设置QQ数据
        //设置QQZone数据
        //QQ/QQZone 暂不设置其他数据

        //设置举报数据
        shareBean.shareToReportInfo.currentMid = "";
        shareBean.shareToReportInfo.reportedMid = "";

        //设置复制链接数据
        shareBean.shareCopyLinkInfo.copyLink = "";

        //设置拉黑数据
        shareBean.shareBlackListInfo.currentMid = "";
        shareBean.shareBlackListInfo.blockedMid = "";

        //设置分享海报数据
        shareBean.sharePosterInfo.hasDeal = false;

        //设置数据
        XShare.getInstance().init(this).setConfig(shareBean).setListener(listener).build();


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
                    break;
                case OnShareDialogClickListener.STATE_FAIL:
                    break;
                case OnShareDialogClickListener.STATE_CANCEL:
                    break;
            }
        }
    };
}
