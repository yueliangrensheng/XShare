package com.yazao.lib.share.demo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.yazao.lib.share.util.WxUtils;
import com.yazao.lib.toast.XToast;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;  ///< 登录
    private static final int RETURN_MSG_TYPE_SHARE = 2;  ///< 分享

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WxUtils.getApi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api = WxUtils.getApi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        int type = resp.getType(); //类型：分享还是登录

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //微信登录成功
                    ///< 用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) resp).code;

                    finish();
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    //"微信分享成功"

                    XToast.show("分享成功");
                    finish();
                }
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (type == RETURN_MSG_TYPE_SHARE) {
                //"分享取消"
                XToast.show("分享取消");
                finish();
            }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;

            default:
                if (type == RETURN_MSG_TYPE_SHARE) {
                    XToast.show("分享失败");
                    finish();
                } else if (type == RETURN_MSG_TYPE_LOGIN) {
                    XToast.show("登陆失败");
                    finish();
                }

                break;
        }
    }
//
//    /**
//     * @param code 根据code再去获取AccessToken
//     */
//    private void getAccessToken(final String code) {
//        //        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        ///< Post方式也可以...
//        //        RequestBody body = new FormBody.Builder()
//        //                .add("appid", "替换为你的appid")
//        //                .add("secret", "替换为你的app密钥")
//        //                .add("code", code)
//        //                .add("grant_type", "authorization_code")
//        //                .build();
//        url += "?appid=" + AppConstant.WX_APP_ID + "&secret="+AppConstant.WX_APP_SECRET
//                + "&code=" + code + "&grant_type=authorization_code";
//        final Request request = new Request.Builder()
//                .url(url)
//                //.post(body)
//                .build();
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                finish();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                AccessToken accessToken = new Gson().fromJson(json, AccessToken.class);
////                getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
//                ///< 发送广播到登录界面，把数据带过去; 可用EventBus
//                EventBus.getDefault().post(new LoginActivity.WxLoginEvent(AppConstant.WX_APP_ID,code));
//            }
//        });
//    }
//
//    /**
//     * @param access_token 根据access_token再去获取UserInfo
//     */
//    private void getUserInfo(String access_token, String openid) {
//        //        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
//        String url = "https://api.weixin.qq.com/sns/userinfo";
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("access_token", access_token)
//                .add("openid", openid)
//                .build();
//        final Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                finish();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String json = response.body().string();
//                WXUserInfo wxUserInfo = new Gson().fromJson(json, WXUserInfo.class);
//                ///< 发送广播到登录界面，把数据带过去; 可用EventBus
////                EventBus.getDefault().post(new LoginActivity.WxLoginEvent(wxUserInfo));
//                finish();
//            }
//        });
//    }
}