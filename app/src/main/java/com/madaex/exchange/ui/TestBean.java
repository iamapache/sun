package com.madaex.exchange.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目：  frame
 * 类名：  TestBean.java
 * 时间：  2018/6/28 17:28
 * 描述：  ${TODO}
 */
public class TestBean implements Parcelable {

    /**
     * apikey : -7nuxAM83ByvUqGo5Rwg0928nZeF44tsvco0sEAaQtfaTQtTZaYSu6MYw76Lsg
     * uid : 359096
     * must_fill : 1
     * device_id : 123456
     */

    private String apikey;
    private int uid;
    private int must_fill;
    private int device_id;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMust_fill() {
        return must_fill;
    }

    public void setMust_fill(int must_fill) {
        this.must_fill = must_fill;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.apikey);
        dest.writeInt(this.uid);
        dest.writeInt(this.must_fill);
        dest.writeInt(this.device_id);
    }

    public TestBean() {
    }

    protected TestBean(Parcel in) {
        this.apikey = in.readString();
        this.uid = in.readInt();
        this.must_fill = in.readInt();
        this.device_id = in.readInt();
    }

    public static final Parcelable.Creator<TestBean> CREATOR = new Parcelable.Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel source) {
            return new TestBean(source);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
