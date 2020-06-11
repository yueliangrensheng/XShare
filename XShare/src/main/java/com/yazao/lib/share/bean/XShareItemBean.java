package com.yazao.lib.share.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IdRes;

/**
 * 分享UI中元素Item数据bean
 */
public class XShareItemBean implements Parcelable {
    //图标资源Id
    @IdRes
    private int iconResId;
    //文案
    private String content;
    //Item标识 -- 用于点击事件来源的判断
    private int flag;

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public XShareItemBean(int iconResId, String content, int flag) {
        this.iconResId = iconResId;
        this.content = content;
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.iconResId);
        dest.writeString(this.content);
        dest.writeInt(this.flag);
    }

    protected XShareItemBean(Parcel in) {
        this.iconResId = in.readInt();
        this.content = in.readString();
        this.flag = in.readInt();
    }

    public static final Creator<XShareItemBean> CREATOR = new Creator<XShareItemBean>() {
        @Override
        public XShareItemBean createFromParcel(Parcel source) {
            return new XShareItemBean(source);
        }

        @Override
        public XShareItemBean[] newArray(int size) {
            return new XShareItemBean[size];
        }
    };
}
