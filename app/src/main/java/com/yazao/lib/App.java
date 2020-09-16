package com.yazao.lib;

import android.app.Application;

import com.yazao.lib.share.config.XShareConfig;
import com.yazao.lib.share.demo.R;

/**
 * Description :
 * Author : yueliangrensheng
 * Date : 2020/9/15
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initXShare();
    }

    private void initXShare() {
        //1. 分享相关初始化
        String appName = getResources().getString(R.string.app_name);
        String appIdForWX = "";
        String miniProgramIdForWX = "";
        String appIdForQQ = "";
        XShareConfig.init(this, appName, appIdForWX, miniProgramIdForWX, appIdForQQ);
    }
}
