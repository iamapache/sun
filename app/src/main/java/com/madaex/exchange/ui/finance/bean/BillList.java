package com.madaex.exchange.ui.finance.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  BillList.java
 * 时间：  2018/9/4 17:59
 * 描述：  ${TODO}
 */

public class BillList implements Parcelable {

    /**
     * status : 1
     * data : {"list":[{"id":"282","userid":"314","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"315","num":"111.00","addtime":"2019-05-24 16:27","status_name":"成功"},{"id":"87","userid":"315","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"314","num":"111.00","addtime":"2019-05-24 16:27","status_name":"成功"},{"id":"281","userid":"314","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"315","num":"100.00","addtime":"2019-05-24 14:32","status_name":"成功"},{"id":"86","userid":"315","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"314","num":"100.00","addtime":"2019-05-24 14:32","status_name":"成功"},{"id":"280","userid":"20000044","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"316","num":"777.00","addtime":"2019-05-24 11:44","status_name":"成功"},{"id":"85","userid":"316","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"20000044","num":"777.00","addtime":"2019-05-24 11:44","status_name":"成功"},{"id":"279","userid":"316","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"20000044","num":"158.00","addtime":"2019-05-24 11:41","status_name":"成功"},{"id":"84","userid":"20000044","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"316","num":"158.00","addtime":"2019-05-24 11:41","status_name":"成功"},{"id":"278","userid":"20000044","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"316","num":"1.00","addtime":"2019-05-24 11:14","status_name":"成功"},{"id":"83","userid":"316","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"20000044","num":"1.00","addtime":"2019-05-24 11:14","status_name":"成功"}],"total":4}
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
         * list : [{"id":"282","userid":"314","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"315","num":"111.00","addtime":"2019-05-24 16:27","status_name":"成功"},{"id":"87","userid":"315","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"314","num":"111.00","addtime":"2019-05-24 16:27","status_name":"成功"},{"id":"281","userid":"314","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"315","num":"100.00","addtime":"2019-05-24 14:32","status_name":"成功"},{"id":"86","userid":"315","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"314","num":"100.00","addtime":"2019-05-24 14:32","status_name":"成功"},{"id":"280","userid":"20000044","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"316","num":"777.00","addtime":"2019-05-24 11:44","status_name":"成功"},{"id":"85","userid":"316","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"20000044","num":"777.00","addtime":"2019-05-24 11:44","status_name":"成功"},{"id":"279","userid":"316","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"20000044","num":"158.00","addtime":"2019-05-24 11:41","status_name":"成功"},{"id":"84","userid":"20000044","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"316","num":"158.00","addtime":"2019-05-24 11:41","status_name":"成功"},{"id":"278","userid":"20000044","coin_ename":"GRC","add_subtract":"-","type_name":"提币","other_id":"316","num":"1.00","addtime":"2019-05-24 11:14","status_name":"成功"},{"id":"83","userid":"316","coin_ename":"GRC","add_subtract":"+","type_name":"充币","other_id":"20000044","num":"1.00","addtime":"2019-05-24 11:14","status_name":"成功"}]
         * total : 4
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            /**
             * id : 282
             * userid : 314
             * coin_ename : GRC
             * add_subtract : -
             * type_name : 提币
             * other_id : 315
             * num : 111.00
             * addtime : 2019-05-24 16:27
             * status_name : 成功
             */

            private String id;
            private String userid;
            private String coin_ename;
            private String add_subtract;
            private String type_name;
            private String other_id;
            private String num;
            private String addtime;
            private String status_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getCoin_ename() {
                return coin_ename;
            }

            public void setCoin_ename(String coin_ename) {
                this.coin_ename = coin_ename;
            }

            public String getAdd_subtract() {
                return add_subtract;
            }

            public void setAdd_subtract(String add_subtract) {
                this.add_subtract = add_subtract;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getOther_id() {
                return other_id;
            }

            public void setOther_id(String other_id) {
                this.other_id = other_id;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.userid);
                dest.writeString(this.coin_ename);
                dest.writeString(this.add_subtract);
                dest.writeString(this.type_name);
                dest.writeString(this.other_id);
                dest.writeString(this.num);
                dest.writeString(this.addtime);
                dest.writeString(this.status_name);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.id = in.readString();
                this.userid = in.readString();
                this.coin_ename = in.readString();
                this.add_subtract = in.readString();
                this.type_name = in.readString();
                this.other_id = in.readString();
                this.num = in.readString();
                this.addtime = in.readString();
                this.status_name = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.total);
            dest.writeTypedList(this.list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.total = in.readInt();
            this.list = in.createTypedArrayList(ListBean.CREATOR);
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

    public BillList() {
    }

    protected BillList(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<BillList> CREATOR = new Creator<BillList>() {
        @Override
        public BillList createFromParcel(Parcel source) {
            return new BillList(source);
        }

        @Override
        public BillList[] newArray(int size) {
            return new BillList[size];
        }
    };
}
