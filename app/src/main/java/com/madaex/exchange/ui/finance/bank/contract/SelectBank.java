package com.madaex.exchange.ui.finance.bank.contract;

import android.os.Parcel;
import android.os.Parcelable;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  SelectBank.java
 * 时间：  2018/8/29 16:49
 * 描述：  ${TODO}
 */

public class SelectBank extends CommonBaseBean implements  Parcelable {

    /**
     * status : 1
     * data : [{"id":"15","bank_name":"平安银行"},{"id":"14","bank_name":"浦发银行"},{"id":"13","bank_name":"中国邮政银行"},{"id":"12","bank_name":"中国工商银行"},{"id":"11","bank_name":"广发银行"},{"id":"10","bank_name":"交通银行"},{"id":"9","bank_name":"中国民生银行"},{"id":"8","bank_name":"招商银行"},{"id":"7","bank_name":"中信银行"},{"id":"6","bank_name":"兴业银行"},{"id":"5","bank_name":"中国光大银行"},{"id":"4","bank_name":"中国建设银行"},{"id":"2","bank_name":"中国农业银行"},{"id":"1","bank_name":"中国银行"}]
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
         * id : 15
         * bank_name : 平安银行
         */

        private String id;
        private String bank_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.bank_name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.bank_name = in.readString();
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
        dest.writeTypedList(this.data);
    }

    public SelectBank() {
    }

    protected SelectBank(Parcel in) {
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<SelectBank> CREATOR = new Creator<SelectBank>() {
        @Override
        public SelectBank createFromParcel(Parcel source) {
            return new SelectBank(source);
        }

        @Override
        public SelectBank[] newArray(int size) {
            return new SelectBank[size];
        }
    };
}
