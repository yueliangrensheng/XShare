
# XShare
> XShare是Android使用的分享share组件（包括微信、朋友圈、QQ、QQZone）

# how to use

- 1.init share component  
> in Application.onCreate() or Activity.onCreate()
```
XShare.getInstance().init(context);
```
- 2.config share component
```
//config app name
XShareConfig.APP_NAME = getResources().getString(R.string.app_name);
//config wx appId if need wx
XShareConfig.WX_APP_ID = "";
//config wx mini programId  if need wxmini
XShareConfig.WX_MINI_PROGRAM_ID = "";
//config qq appId if need qq
XShareConfig.QQ_APP_ID = "";
```
- 3.config UI & data
```       
//设置分享UI显示元素
XShareBean shareBean = new XShareBean();
shareBean.isShowPrivate = false; //false: not show this item
shareBean.isShowWX = true;
shareBean.isShowWXCircle = true; //true: show this item
shareBean.isShowQQ = false;
shareBean.isShowQQZone = false;
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

//设置数据
XShare.getInstance().init(this).setConfig(shareBean).setListener(listener).build();
```
- 4.show dialog
```
XShare.getInstance().showDialog();
```
- 5.xshare deal with permission
```
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  
  //xshare deal with permission
  XShare.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```
- 6.xshare deal with onActivityResult   
```
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  
  // xshare deal with onActivityResult
  XShare.getInstance().onActivityResult(requestCode, resultCode, data);
}
``` 
      