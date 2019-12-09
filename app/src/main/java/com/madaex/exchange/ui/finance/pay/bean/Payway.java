package com.madaex.exchange.ui.finance.pay.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  Payway.java
 * 时间：  2019/5/15 12:58
 * 描述：  ${TODO}
 */
public class Payway implements Parcelable {

    /**
     * status : 1
     * data : [{"id":"25","user_id":"55","type":"1","account":"","img":"http://18.162.61.91/Uploads/wechat/2019-05-15/5cdbcafec78bc.jpg","bank":"","bank_branch":null,"status":"1","add_time":"1557908222","name":""},{"id":"26","user_id":"55","type":"2","account":"18320814644","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcb9eded15.jpg","bank":"","bank_branch":null,"status":"1","add_time":"1557908382","name":""},{"id":"31","user_id":"55","type":"3","account":"5588588","img":"","bank":"平安银行","bank_branch":null,"status":"1","add_time":"1557916031","name":"何巨强"}]
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
         * id : 25
         * user_id : 55
         * type : 1
         * account :
         * img : http://18.162.61.91/Uploads/wechat/2019-05-15/5cdbcafec78bc.jpg
         * bank :
         * bank_branch : null
         * status : 1
         * add_time : 1557908222
         * name :
         */

        private String id;
        private String user_id;
        private int type;
        private String account;
        private String img;
        private String bank;
        private String bank_branch;
        private String status;
        private String add_time;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBank_branch() {
            return bank_branch;
        }

        public void setBank_branch(String bank_branch) {
            this.bank_branch = bank_branch;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
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
            dest.writeString(this.user_id);
            dest.writeInt(this.type);
            dest.writeString(this.account);
            dest.writeString(this.img);
            dest.writeString(this.bank);
            dest.writeString(this.bank_branch);
            dest.writeString(this.status);
            dest.writeString(this.add_time);
            dest.writeString(this.name);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.user_id = in.readString();
            this.type = in.readInt();
            this.account = in.readString();
            this.img = in.readString();
            this.bank = in.readString();
            this.bank_branch = in.readString();
            this.status = in.readString();
            this.add_time = in.readString();
            this.name = in.readString();
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

    public Payway() {
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

    protected Payway(Parcel in) {
        this.status = in.readInt();
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<Payway> CREATOR = new Creator<Payway>() {
        @Override
        public Payway createFromParcel(Parcel source) {
            return new Payway(source);
        }

        @Override
        public Payway[] newArray(int size) {
            return new Payway[size];
        }
    };
}
