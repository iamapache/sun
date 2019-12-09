package com.madaex.exchange.ui.finance.c2c.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  EntrusTwo.java
 * 时间：  2019/5/14 20:17
 * 描述：  ${TODO}
 */
public class EntrusTwo implements Parcelable {

    /**
     * status : 1
     * data : {"goods_list":[{"id":"31","coin_ename":"GRC","num":"100.00","price":"9.00","total":"900.00","user_id":"55","to_id":"314","type":"2","status":"0","order_no":"GRC87386689","addtime":"2019-05-15 16:32:15","total_num":"100.00","type_name":"卖","status_name":"待支付","head_left":null,"buy_sell_id":"55","payment_list":[{"type":"1","account":"","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcafec78bc.jpg","bank":"","name":"","truename":""},{"type":"2","account":"18320814644","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcb9eded15.jpg","bank":"","name":"","truename":""}]},{"id":"28","coin_ename":"GRC","num":"100.00","price":"33.00","total":"3300.00","user_id":"55","to_id":"314","type":"2","status":"0","order_no":"GRC56638926","addtime":"2019-05-15 11:35:26","total_num":"100.00","type_name":"卖","status_name":"待支付","head_left":null,"buy_sell_id":"55","payment_list":[{"type":"1","account":"","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcafec78bc.jpg","bank":"","name":"","truename":""},{"type":"2","account":"18320814644","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcb9eded15.jpg","bank":"","name":"","truename":""}]}],"totalpage":1}
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
         * goods_list : [{"id":"31","coin_ename":"GRC","num":"100.00","price":"9.00","total":"900.00","user_id":"55","to_id":"314","type":"2","status":"0","order_no":"GRC87386689","addtime":"2019-05-15 16:32:15","total_num":"100.00","type_name":"卖","status_name":"待支付","head_left":null,"buy_sell_id":"55","payment_list":[{"type":"1","account":"","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcafec78bc.jpg","bank":"","name":"","truename":""},{"type":"2","account":"18320814644","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcb9eded15.jpg","bank":"","name":"","truename":""}]},{"id":"28","coin_ename":"GRC","num":"100.00","price":"33.00","total":"3300.00","user_id":"55","to_id":"314","type":"2","status":"0","order_no":"GRC56638926","addtime":"2019-05-15 11:35:26","total_num":"100.00","type_name":"卖","status_name":"待支付","head_left":null,"buy_sell_id":"55","payment_list":[{"type":"1","account":"","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcafec78bc.jpg","bank":"","name":"","truename":""},{"type":"2","account":"18320814644","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcb9eded15.jpg","bank":"","name":"","truename":""}]}]
         * totalpage : 1
         */

        private int totalpage;
        private List<GoodsListBean> goods_list;

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean implements Parcelable {
            /**
             * id : 31
             * coin_ename : GRC
             * num : 100.00
             * price : 9.00
             * total : 900.00
             * user_id : 55
             * to_id : 314
             * type : 2
             * status : 0
             * order_no : GRC87386689
             * addtime : 2019-05-15 16:32:15
             * total_num : 100.00
             * type_name : 卖
             * status_name : 待支付
             * head_left : null
             * buy_sell_id : 55
             * payment_list : [{"type":"1","account":"","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcafec78bc.jpg","bank":"","name":"","truename":""},{"type":"2","account":"18320814644","img":"http://18.162.61.91/Uploads/alipay/2019-05-15/5cdbcb9eded15.jpg","bank":"","name":"","truename":""}]
             */

            private String id;
            private String coin_ename;
            private String num;
            private String price;
            private String total;
            private String user_id;
            private String to_id;
            private String type;
            private String status;
            private String order_no;
            private String addtime;
            private String total_num;
            private String type_name;
            private String status_name;
            private String buy_sell_id;
            private String head_right;
            private String head_left;
            private List<PaymentListBean> payment_list;
            private String remark_num;
            public String getHead_right() {
                return head_right;
            }

            public String getRemark_num() {
                return remark_num;
            }

            public void setRemark_num(String remark_num) {
                this.remark_num = remark_num;
            }

            public void setHead_right(String head_right) {
                this.head_right = head_right;
            }

            public String getHead_left() {
                return head_left;
            }

            public void setHead_left(String head_left) {
                this.head_left = head_left;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCoin_ename() {
                return coin_ename;
            }

            public void setCoin_ename(String coin_ename) {
                this.coin_ename = coin_ename;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getTo_id() {
                return to_id;
            }

            public void setTo_id(String to_id) {
                this.to_id = to_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getTotal_num() {
                return total_num;
            }

            public void setTotal_num(String total_num) {
                this.total_num = total_num;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }


            public String getBuy_sell_id() {
                return buy_sell_id;
            }

            public void setBuy_sell_id(String buy_sell_id) {
                this.buy_sell_id = buy_sell_id;
            }

            public List<PaymentListBean> getPayment_list() {
                return payment_list;
            }

            public void setPayment_list(List<PaymentListBean> payment_list) {
                this.payment_list = payment_list;
            }

            public static class PaymentListBean implements Parcelable {

                /**
                 * type : 1
                 * account :
                 * img : http://18.162.61.91/Uploads/wechat/2019-05-15/5cdbcafec78bc.jpg
                 * bank :
                 * name :
                 * truename :
                 */

                private String type;
                private String account;
                private String img;
                private String bank;
                private String name;
                private String truename;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTruename() {
                    return truename;
                }

                public void setTruename(String truename) {
                    this.truename = truename;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.type);
                    dest.writeString(this.account);
                    dest.writeString(this.img);
                    dest.writeString(this.bank);
                    dest.writeString(this.name);
                    dest.writeString(this.truename);
                }

                public PaymentListBean() {
                }

                protected PaymentListBean(Parcel in) {
                    this.type = in.readString();
                    this.account = in.readString();
                    this.img = in.readString();
                    this.bank = in.readString();
                    this.name = in.readString();
                    this.truename = in.readString();
                }

                public static final Creator<PaymentListBean> CREATOR = new Creator<PaymentListBean>() {
                    @Override
                    public PaymentListBean createFromParcel(Parcel source) {
                        return new PaymentListBean(source);
                    }

                    @Override
                    public PaymentListBean[] newArray(int size) {
                        return new PaymentListBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.coin_ename);
                dest.writeString(this.num);
                dest.writeString(this.price);
                dest.writeString(this.total);
                dest.writeString(this.user_id);
                dest.writeString(this.to_id);
                dest.writeString(this.type);
                dest.writeString(this.status);
                dest.writeString(this.order_no);
                dest.writeString(this.addtime);
                dest.writeString(this.total_num);
                dest.writeString(this.type_name);
                dest.writeString(this.status_name);
                dest.writeString(this.buy_sell_id);
                dest.writeTypedList(this.payment_list);
            }

            public GoodsListBean() {
            }

            protected GoodsListBean(Parcel in) {
                this.id = in.readString();
                this.coin_ename = in.readString();
                this.num = in.readString();
                this.price = in.readString();
                this.total = in.readString();
                this.user_id = in.readString();
                this.to_id = in.readString();
                this.type = in.readString();
                this.status = in.readString();
                this.order_no = in.readString();
                this.addtime = in.readString();
                this.total_num = in.readString();
                this.type_name = in.readString();
                this.status_name = in.readString();
                this.buy_sell_id = in.readString();
                this.payment_list = in.createTypedArrayList(PaymentListBean.CREATOR);
            }

            public static final Parcelable.Creator<GoodsListBean> CREATOR = new Parcelable.Creator<GoodsListBean>() {
                @Override
                public GoodsListBean createFromParcel(Parcel source) {
                    return new GoodsListBean(source);
                }

                @Override
                public GoodsListBean[] newArray(int size) {
                    return new GoodsListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.totalpage);
            dest.writeTypedList(this.goods_list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.totalpage = in.readInt();
            this.goods_list = in.createTypedArrayList(GoodsListBean.CREATOR);
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
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public EntrusTwo() {
    }

    protected EntrusTwo(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<EntrusTwo> CREATOR = new Parcelable.Creator<EntrusTwo>() {
        @Override
        public EntrusTwo createFromParcel(Parcel source) {
            return new EntrusTwo(source);
        }

        @Override
        public EntrusTwo[] newArray(int size) {
            return new EntrusTwo[size];
        }
    };
}
