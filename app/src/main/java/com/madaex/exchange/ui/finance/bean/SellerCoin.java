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
     * data : {"is_arr":1,"pro_arr":["ERC20","OMNI"],"xnb_num":"10000.000000","xnb_fee":["0.2","0.4"],"zc_min":"0.1","zc_max":"10000"}
     * message : 操作成功
     */

    private int status;
    private DataBean data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean implements Parcelable {
        /**
         * is_arr : 1
         * pro_arr : ["ERC20","OMNI"]
         * xnb_num : 10000.000000
         * xnb_fee : ["0.2","0.4"]
         * zc_min : 0.1
         * zc_max : 10000
         */

        private int is_arr;
        private String xnb_num;
        private String zc_min;
        private String zc_max;
        private List<String> pro_arr;
        private List<String> xnb_fee;

        public int getIs_arr() {
            return is_arr;
        }

        public void setIs_arr(int is_arr) {
            this.is_arr = is_arr;
        }

        public String getXnb_num() {
            return xnb_num;
        }

        public void setXnb_num(String xnb_num) {
            this.xnb_num = xnb_num;
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

        public List<String> getPro_arr() {
            return pro_arr;
        }

        public void setPro_arr(List<String> pro_arr) {
            this.pro_arr = pro_arr;
        }

        public List<String> getXnb_fee() {
            return xnb_fee;
        }

        public void setXnb_fee(List<String> xnb_fee) {
            this.xnb_fee = xnb_fee;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.is_arr);
            dest.writeString(this.xnb_num);
            dest.writeString(this.zc_min);
            dest.writeString(this.zc_max);
            dest.writeStringList(this.pro_arr);
            dest.writeStringList(this.xnb_fee);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.is_arr = in.readInt();
            this.xnb_num = in.readString();
            this.zc_min = in.readString();
            this.zc_max = in.readString();
            this.pro_arr = in.createStringArrayList();
            this.xnb_fee = in.createStringArrayList();
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
        dest.writeString(this.message);
    }

    public SellerCoin() {
    }

    protected SellerCoin(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Creator<SellerCoin> CREATOR = new Creator<SellerCoin>() {
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
