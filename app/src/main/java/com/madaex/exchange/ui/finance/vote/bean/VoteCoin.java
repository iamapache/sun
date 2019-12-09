package com.madaex.exchange.ui.finance.vote.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  VoteCoin.java
 * 时间：  2018/9/10 16:49
 * 描述：  ${TODO}
 */

public class VoteCoin extends CommonBaseBean implements Parcelable {

    /**
     * status : 1
     * data : [{"i":1,"id":"1","total":"3000","img":"","coinname":"A1","my_num":0,"number":88,"votecoin":"CNY","assumnum":"1","cast_number":1009.14,"status":"去投票","details":"A1货币发行"},{"i":2,"id":"2","total":"5000","img":"","coinname":"B1","my_num":0,"number":67,"votecoin":"CNY","assumnum":"0","cast_number":1009.14,"status":"去投票","details":"B2特殊货币发行"}]
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
         * i : 1
         * id : 1
         * total : 3000
         * img :
         * coinname : A1
         * my_num : 0
         * number : 88
         * votecoin : CNY
         * assumnum : 1
         * cast_number : 1009.14
         * status : 去投票
         * details : A1货币发行
         */

        private int i;
        private String id;
        private String total;
        private String img;
        private String coinname;
        private int my_num;
        private int number;
        private String votecoin;
        private String assumnum;
        private double cast_number;
        private String status;
        private String details;
        private String deal;
        private String issue_price;
        public String getDeal() {
            return deal;
        }

        public void setDeal(String deal) {
            this.deal = deal;
        }

        /**
         * percent : 0.0362%
         * unit : GRC
         */

        private String percent;
        private String unit;

        public String getIssue_price() {
            return issue_price;
        }

        public void setIssue_price(String issue_price) {
            this.issue_price = issue_price;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCoinname() {
            return coinname;
        }

        public void setCoinname(String coinname) {
            this.coinname = coinname;
        }

        public int getMy_num() {
            return my_num;
        }

        public void setMy_num(int my_num) {
            this.my_num = my_num;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getVotecoin() {
            return votecoin;
        }

        public void setVotecoin(String votecoin) {
            this.votecoin = votecoin;
        }

        public String getAssumnum() {
            return assumnum;
        }

        public void setAssumnum(String assumnum) {
            this.assumnum = assumnum;
        }

        public double getCast_number() {
            return cast_number;
        }

        public void setCast_number(double cast_number) {
            this.cast_number = cast_number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.i);
            dest.writeString(this.id);
            dest.writeString(this.total);
            dest.writeString(this.img);
            dest.writeString(this.coinname);
            dest.writeInt(this.my_num);
            dest.writeInt(this.number);
            dest.writeString(this.votecoin);
            dest.writeString(this.assumnum);
            dest.writeDouble(this.cast_number);
            dest.writeString(this.status);
            dest.writeString(this.details);
            dest.writeString(this.deal);
            dest.writeString(this.percent);
            dest.writeString(this.unit);
            dest.writeString(this.issue_price);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.i = in.readInt();
            this.id = in.readString();
            this.total = in.readString();
            this.img = in.readString();
            this.coinname = in.readString();
            this.my_num = in.readInt();
            this.number = in.readInt();
            this.votecoin = in.readString();
            this.assumnum = in.readString();
            this.cast_number = in.readDouble();
            this.status = in.readString();
            this.details = in.readString();
            this.deal = in.readString();
            this.percent = in.readString();
            this.unit = in.readString();
            this.issue_price = in.readString();
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

    public VoteCoin() {
    }

    protected VoteCoin(Parcel in) {
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<VoteCoin> CREATOR = new Creator<VoteCoin>() {
        @Override
        public VoteCoin createFromParcel(Parcel source) {
            return new VoteCoin(source);
        }

        @Override
        public VoteCoin[] newArray(int size) {
            return new VoteCoin[size];
        }
    };
}
