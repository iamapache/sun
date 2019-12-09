package com.madaex.exchange.ui.finance.c2c.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  EntractDetail.java
 * 时间：  2019/5/15 17:33
 * 描述：  ${TODO}
 */
public class EntractDetail {

    /**
     * status : 1
     * data : {"goods":{"id":"41","coin_ename":"GRC","num":"100.00","deal_num":"0.00","price":"9.00"},"order":[{"id":"31","coin_ename":"GRC","num":"100.00","price":"9.00","total":"900.00","addtime":"2019-05-15 16:32"}]}
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
         * goods : {"id":"41","coin_ename":"GRC","num":"100.00","deal_num":"0.00","price":"9.00"}
         * order : [{"id":"31","coin_ename":"GRC","num":"100.00","price":"9.00","total":"900.00","addtime":"2019-05-15 16:32"}]
         */

        private GoodsBean goods;
        private List<OrderBean> order;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class GoodsBean {
            /**
             * id : 41
             * coin_ename : GRC
             * num : 100.00
             * deal_num : 0.00
             * price : 9.00
             */

            private String id;
            private String coin_ename;
            private String num;
            private String deal_num;
            private String price;

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
        }

        public static class OrderBean {
            /**
             * id : 31
             * coin_ename : GRC
             * num : 100.00
             * price : 9.00
             * total : 900.00
             * addtime : 2019-05-15 16:32
             */

            private String id;
            private String coin_ename;
            private String num;
            private String price;
            private String total;
            private String addtime;

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

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
