package com.madaex.exchange.ui.market.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  EntrustList.java
 * 时间：  2018/9/5 15:33
 * 描述：  ${TODO}
 */

public class EntrustList extends CommonBaseBean implements Parcelable {

    /**
     * status : 1
     * data : [{"id":"69","price":"11.00000000","num":"11.00000000","type":"1","deal":"0.00000000","mum":"122.21000000","one_xnb":"ETH","two_xnb":"GRC"}]
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 69
         * price : 11.00000000
         * num : 11.00000000
         * type : 1
         * deal : 0.00000000
         * mum : 122.21000000
         * one_xnb : ETH
         * two_xnb : GRC
         */
        private String addtime;
        private String add_time;
        private String id;
        private String price;
        private String num;
        private String type;
        private String types;
        private String deal;
        private String mum;
        private String one_xnb;
        private String two_xnb;
        private int status;
        private String statuss;

        private String deal_money;
        private String cancel_number;
        private String status_name;
        public String getStatuss() {
            return statuss;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public void setStatuss(String statuss) {
            this.statuss = statuss;
        }

        public String getDeal_money() {
            return deal_money;
        }

        public void setDeal_money(String deal_money) {
            this.deal_money = deal_money;
        }

        public String getCancel_number() {
            return cancel_number;
        }

        public void setCancel_number(String cancel_number) {
            this.cancel_number = cancel_number;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeal() {
            return deal;
        }

        public void setDeal(String deal) {
            this.deal = deal;
        }

        public String getMum() {
            return mum;
        }

        public void setMum(String mum) {
            this.mum = mum;
        }

        public String getOne_xnb() {
            return one_xnb;
        }

        public void setOne_xnb(String one_xnb) {
            this.one_xnb = one_xnb;
        }

        public String getTwo_xnb() {
            return two_xnb;
        }

        public void setTwo_xnb(String two_xnb) {
            this.two_xnb = two_xnb;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.addtime);
            dest.writeString(this.add_time);
            dest.writeString(this.id);
            dest.writeString(this.price);
            dest.writeString(this.num);
            dest.writeString(this.type);
            dest.writeString(this.deal);
            dest.writeString(this.mum);
            dest.writeString(this.one_xnb);
            dest.writeString(this.two_xnb);
            dest.writeInt(this.status);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.addtime = in.readString();
            this.add_time = in.readString();
            this.id = in.readString();
            this.price = in.readString();
            this.num = in.readString();
            this.type = in.readString();
            this.deal = in.readString();
            this.mum = in.readString();
            this.one_xnb = in.readString();
            this.two_xnb = in.readString();
            this.status = in.readInt();
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
        dest.writeTypedList(this.data);
    }

    public EntrustList() {
    }

    protected EntrustList(Parcel in) {
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<EntrustList> CREATOR = new Parcelable.Creator<EntrustList>() {
        @Override
        public EntrustList createFromParcel(Parcel source) {
            return new EntrustList(source);
        }

        @Override
        public EntrustList[] newArray(int size) {
            return new EntrustList[size];
        }
    };
}
