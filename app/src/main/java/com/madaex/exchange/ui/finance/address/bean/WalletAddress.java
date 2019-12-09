package com.madaex.exchange.ui.finance.address.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  Address.java
 * 时间：  2018/8/30 18:07
 * 描述：  ${TODO}
 */

public class WalletAddress implements Parcelable {


    /**
     * status : 1
     * data : [{"id":"11","addr":"12312321","name":"大幅度"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 11
         * addr : 12312321
         * name : 大幅度
         */

        private String id;
        private String addr;
        private String name;
        public boolean isCheck= false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.addr);
            dest.writeString(this.name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.addr = in.readString();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeTypedList(this.data);
    }

    public WalletAddress() {
    }

    protected WalletAddress(Parcel in) {
        this.status = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<WalletAddress> CREATOR = new Parcelable.Creator<WalletAddress>() {
        @Override
        public WalletAddress createFromParcel(Parcel source) {
            return new WalletAddress(source);
        }

        @Override
        public WalletAddress[] newArray(int size) {
            return new WalletAddress[size];
        }
    };
}
