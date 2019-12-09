package com.madaex.exchange.ui.finance.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  SellerCoin.java
 * 时间：  2018/8/30 15:54
 * 描述：  ${TODO}
 */

public class SellerCoin implements Parcelable {

    /**
     * status : 1
     * data : {"xnb_num":"0.00000000","xnb_fee":["0.2"]}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * xnb_num : 0.00000000
         * xnb_fee : ["0.2"]
         */

        private String xnb_num;
        private List<String> xnb_fee;
        private String zc_min;
        private String zc_max;
        public String getXnb_num() {
            return xnb_num;
        }

        public void setXnb_num(String xnb_num) {
            this.xnb_num = xnb_num;
        }

        public List<String> getXnb_fee() {
            return xnb_fee;
        }

        public void setXnb_fee(List<String> xnb_fee) {
            this.xnb_fee = xnb_fee;
        }

        public String getZc_min() {
            return zc_min;
        }

        public void setZc_min(String zc_min) {
            this.zc_min = zc_min;
        }

        public String getZc_max() {
            return zc_max;
        }

        public void setZc_max(String zc_max) {
            this.zc_max = zc_max;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.xnb_num);
            dest.writeStringList(this.xnb_fee);
            dest.writeString(this.zc_min);
            dest.writeString(this.zc_max);
        }

        protected DataBean(Parcel in) {
            this.xnb_num = in.readString();
            this.xnb_fee = in.createStringArrayList();
            this.zc_min = in.readString();
            this.zc_max = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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
        dest.writeParcelable(this.data, flags);
    }

    public SellerCoin() {
    }

    protected SellerCoin(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SellerCoin> CREATOR = new Parcelable.Creator<SellerCoin>() {
        @Override
        public SellerCoin createFromParcel(Parcel source) {
            return new SellerCoin(source);
        }

        @Override
        public SellerCoin[] newArray(int size) {
            return new SellerCoin[size];
        }
    };
}
