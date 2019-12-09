package com.madaex.exchange.ui.finance.c2c.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.madaex.exchange.ui.common.CommonBaseBean;

/**
 * 项目：  madaexchange
 * 类名：  C2CTransationDetail.java
 * 时间：  2018/8/30 15:20
 * 描述：  ${TODO}
 */

public class C2CTransationDetail extends CommonBaseBean implements Parcelable {

    /**
     * status : 1
     * data : {"num":"100.00","status":"待支付","tradeno":"31566758","recetruename":"洋货栈","recebankname":" 平安银行","recebankaddr":"深圳市东门支行","recebankcard":"6226081365429875","rece_name":"洋货栈","rece_bank_addr":" 平安银行深圳市东门支行","rece_bank_card":"6226081365429875"}
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * num : 100.00
         * status : 待支付
         * tradeno : 31566758
         * recetruename : 洋货栈
         * recebankname :  平安银行
         * recebankaddr : 深圳市东门支行
         * recebankcard : 6226081365429875
         * rece_name : 洋货栈
         * rece_bank_addr :  平安银行深圳市东门支行
         * rece_bank_card : 6226081365429875
         */

        private String num;
        private String status;
        private String tradeno;
        private String recetruename;
        private String recebankname;
        private String recebankaddr;
        private String recebankcard;
        private String rece_name;
        private String rece_bank_addr;
        private String rece_bank_card;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTradeno() {
            return tradeno;
        }

        public void setTradeno(String tradeno) {
            this.tradeno = tradeno;
        }

        public String getRecetruename() {
            return recetruename;
        }

        public void setRecetruename(String recetruename) {
            this.recetruename = recetruename;
        }

        public String getRecebankname() {
            return recebankname;
        }

        public void setRecebankname(String recebankname) {
            this.recebankname = recebankname;
        }

        public String getRecebankaddr() {
            return recebankaddr;
        }

        public void setRecebankaddr(String recebankaddr) {
            this.recebankaddr = recebankaddr;
        }

        public String getRecebankcard() {
            return recebankcard;
        }

        public void setRecebankcard(String recebankcard) {
            this.recebankcard = recebankcard;
        }

        public String getRece_name() {
            return rece_name;
        }

        public void setRece_name(String rece_name) {
            this.rece_name = rece_name;
        }

        public String getRece_bank_addr() {
            return rece_bank_addr;
        }

        public void setRece_bank_addr(String rece_bank_addr) {
            this.rece_bank_addr = rece_bank_addr;
        }

        public String getRece_bank_card() {
            return rece_bank_card;
        }

        public void setRece_bank_card(String rece_bank_card) {
            this.rece_bank_card = rece_bank_card;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.num);
            dest.writeString(this.status);
            dest.writeString(this.tradeno);
            dest.writeString(this.recetruename);
            dest.writeString(this.recebankname);
            dest.writeString(this.recebankaddr);
            dest.writeString(this.recebankcard);
            dest.writeString(this.rece_name);
            dest.writeString(this.rece_bank_addr);
            dest.writeString(this.rece_bank_card);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.num = in.readString();
            this.status = in.readString();
            this.tradeno = in.readString();
            this.recetruename = in.readString();
            this.recebankname = in.readString();
            this.recebankaddr = in.readString();
            this.recebankcard = in.readString();
            this.rece_name = in.readString();
            this.rece_bank_addr = in.readString();
            this.rece_bank_card = in.readString();
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
        dest.writeParcelable(this.data, flags);
    }

    public C2CTransationDetail() {
    }

    protected C2CTransationDetail(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<C2CTransationDetail> CREATOR = new Parcelable.Creator<C2CTransationDetail>() {
        @Override
        public C2CTransationDetail createFromParcel(Parcel source) {
            return new C2CTransationDetail(source);
        }

        @Override
        public C2CTransationDetail[] newArray(int size) {
            return new C2CTransationDetail[size];
        }
    };
}
