package com.madaex.exchange.ui.finance.c2c.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  PlatformEntrust.java
 * 时间：  2019/5/14 12:07
 * 描述：  ${TODO}
 */
public class PlatformEntrust {

    /**
     * status : 1
     * data : {"goods_list":[{"id":"34","user_id":"316","coin_ename":"GRC","num":"100.00","status":"0","deal_num":"0.00","price":"2.00","type":"2","type_name":"卖","coinImageUrl":"http://192.168.2.117/Uploads/coin/5c04d4f869049.png"},{"id":"27","user_id":"316","coin_ename":"GRC","num":"100.00","status":"0","deal_num":"0.00","price":"2.00","type":"2","type_name":"卖","coinImageUrl":"http://192.168.2.117/Uploads/coin/5c04d4f869049.png"},{"id":"26","user_id":"316","coin_ename":"GRC","num":"500.00","status":"0","deal_num":"0.00","price":"1.00","type":"2","type_name":"卖","coinImageUrl":"http://192.168.2.117/Uploads/coin/5c04d4f869049.png"}],"totalpage":2}
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

    public static class DataBean {
        /**
         * goods_list : [{"id":"34","user_id":"316","coin_ename":"GRC","num":"100.00","status":"0","deal_num":"0.00","price":"2.00","type":"2","type_name":"卖","coinImageUrl":"http://192.168.2.117/Uploads/coin/5c04d4f869049.png"},{"id":"27","user_id":"316","coin_ename":"GRC","num":"100.00","status":"0","deal_num":"0.00","price":"2.00","type":"2","type_name":"卖","coinImageUrl":"http://192.168.2.117/Uploads/coin/5c04d4f869049.png"},{"id":"26","user_id":"316","coin_ename":"GRC","num":"500.00","status":"0","deal_num":"0.00","price":"1.00","type":"2","type_name":"卖","coinImageUrl":"http://192.168.2.117/Uploads/coin/5c04d4f869049.png"}]
         * totalpage : 2
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

        public static class GoodsListBean {
            /**
             * id : 34
             * user_id : 316
             * coin_ename : GRC
             * num : 100.00
             * status : 0
             * deal_num : 0.00
             * price : 2.00
             * type : 2
             * type_name : 卖
             * coinImageUrl : http://192.168.2.117/Uploads/coin/5c04d4f869049.png
             */

            private String id;
            private String user_id;
            private String coin_ename;
            private String num;
            private String status;
            private String deal_num;
            private String price;
            private String type;
            private String type_name;
            private String coinImageUrl;
            private String num_min;

            public String getNum_min() {
                return num_min;
            }

            public void setNum_min(String num_min) {
                this.num_min = num_min;
            }

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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDeal_num() {
                return deal_num;
            }

            public void setDeal_num(String deal_num) {
                this.deal_num = deal_num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getCoinImageUrl() {
                return coinImageUrl;
            }

            public void setCoinImageUrl(String coinImageUrl) {
                this.coinImageUrl = coinImageUrl;
            }
        }
    }
}
