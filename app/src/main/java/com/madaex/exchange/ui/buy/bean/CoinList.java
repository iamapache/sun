package com.madaex.exchange.ui.buy.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  CoinList.java
 * 时间：  2018/9/5 12:13
 * 描述：  ${TODO}
 */


public class CoinList extends CommonBaseBean implements Parcelable {

    /**
     * status : 1
     * data : [{"title":"GRC","list":[{"name":"LCC/GRC","img":""},{"name":"HC/GRC","img":""},{"name":"DOGE/GRC","img":""},{"name":"LLT/GRC","img":""},{"name":"BTS/GRC","img":""},{"name":"LK/GRC","img":""},{"name":"ETC/GRC","img":""},{"name":"ETH/GRC","img":""},{"name":"LVT/GRC","img":""},{"name":"EOS/GRC","img":""},{"name":"LTC/GRC","img":""},{"name":"TYZ/GRC","img":""},{"name":"DGC/GRC","img":""},{"name":"BTN/GRC","img":""},{"name":"BCC/GRC","img":""},{"name":"CAN/GRC","img":""},{"name":"WKC/GRC","img":""},{"name":"BIC/GRC","img":""},{"name":"ETH/GRC","img":""}]},{"title":"ETH","list":[{"name":"WKC/ETH","img":""},{"name":"BIC/ETH","img":""},{"name":"GRC/ETH","img":""}]}]
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
         * title : GRC
         * list : [{"name":"LCC/GRC","img":""},{"name":"HC/GRC","img":""},{"name":"DOGE/GRC","img":""},{"name":"LLT/GRC","img":""},{"name":"BTS/GRC","img":""},{"name":"LK/GRC","img":""},{"name":"ETC/GRC","img":""},{"name":"ETH/GRC","img":""},{"name":"LVT/GRC","img":""},{"name":"EOS/GRC","img":""},{"name":"LTC/GRC","img":""},{"name":"TYZ/GRC","img":""},{"name":"DGC/GRC","img":""},{"name":"BTN/GRC","img":""},{"name":"BCC/GRC","img":""},{"name":"CAN/GRC","img":""},{"name":"WKC/GRC","img":""},{"name":"BIC/GRC","img":""},{"name":"ETH/GRC","img":""}]
         */

        private String title;
        private ArrayList<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<ListBean> getList() {
            return list;
        }

        public void setList(ArrayList<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable {
            /**
             * name : LCC/GRC
             * img :
             */

            private String name;
            private String img;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.name);
                dest.writeString(this.img);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.name = in.readString();
                this.img = in.readString();
            }

            public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
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
            dest.writeString(this.title);
            dest.writeTypedList(this.list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.title = in.readString();
            this.list = in.createTypedArrayList(ListBean.CREATOR);
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

    public CoinList() {
    }

    protected CoinList(Parcel in) {
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Parcelable.Creator<CoinList> CREATOR = new Parcelable.Creator<CoinList>() {
        @Override
        public CoinList createFromParcel(Parcel source) {
            return new CoinList(source);
        }

        @Override
        public CoinList[] newArray(int size) {
            return new CoinList[size];
        }
    };
}
