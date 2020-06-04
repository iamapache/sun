package com.madaex.exchange.ui.finance.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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
     * data : {"list":[{"coin_ename":"USDT","type_name":"币币转合约","other_id":"","num":"1000.00000","mum_a":"4000.00000","addtime":"2020-06-03 18:19:56","status_name":"待处理","state":0,"remark":"币币转合约","id":27146,"add_subtract":"-"}],"total":1}
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
         * list : [{"coin_ename":"USDT","type_name":"币币转合约","other_id":"","num":"1000.00000","mum_a":"4000.00000","addtime":"2020-06-03 18:19:56","status_name":"待处理","state":0,"remark":"币币转合约","id":27146,"add_subtract":"-"}]
         * total : 1
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
             * coin_ename : USDT
             * type_name : 币币转合约
             * other_id :
             * num : 1000.00000
             * mum_a : 4000.00000
             * addtime : 2020-06-03 18:19:56
             * status_name : 待处理
             * state : 0
             * remark : 币币转合约
             * id : 27146
             * add_subtract : -
             */

            private String coin_ename;
            private String type_name;
            private String other_id;
            private String num;
            private String mum_a;
            private String addtime;
            private String status_name;
            private int state;
            private String remark;
            private int id;
            private String add_subtract;

            public String getCoin_ename() {
                return coin_ename;
            }

            public void setCoin_ename(String coin_ename) {
                this.coin_ename = coin_ename;
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

            public String getMum_a() {
                return mum_a;
            }

            public void setMum_a(String mum_a) {
                this.mum_a = mum_a;
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

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAdd_subtract() {
                return add_subtract;
            }

            public void setAdd_subtract(String add_subtract) {
                this.add_subtract = add_subtract;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.coin_ename);
                dest.writeString(this.type_name);
                dest.writeString(this.other_id);
                dest.writeString(this.num);
                dest.writeString(this.mum_a);
                dest.writeString(this.addtime);
                dest.writeString(this.status_name);
                dest.writeInt(this.state);
                dest.writeString(this.remark);
                dest.writeInt(this.id);
                dest.writeString(this.add_subtract);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.coin_ename = in.readString();
                this.type_name = in.readString();
                this.other_id = in.readString();
                this.num = in.readString();
                this.mum_a = in.readString();
                this.addtime = in.readString();
                this.status_name = in.readString();
                this.state = in.readInt();
                this.remark = in.readString();
                this.id = in.readInt();
                this.add_subtract = in.readString();
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
            dest.writeList(this.list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.total = in.readInt();
            this.list = new ArrayList<ListBean>();
            in.readList(this.list, ListBean.class.getClassLoader());
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

    public BillList() {
    }

    protected BillList(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
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
