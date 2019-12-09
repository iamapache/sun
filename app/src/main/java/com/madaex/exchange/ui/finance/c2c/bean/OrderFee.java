package com.madaex.exchange.ui.finance.c2c.bean;

/**
 * 项目：  sun
 * 类名：  OrderFee.java
 * 时间：  2019/5/14 17:03
 * 描述：  ${TODO}
 */
public class OrderFee {


    /**
     * status : 1
     * data : {"goods_min":"100","order_sell_fee":"0.005","order_buy_fee":"0","trade_sell_fee":"0.003","trade_buy_fee":"0.003","recharge_fee":"0.005","cash_fee":"0.005","goods_buy_price_min":"0.95","goods_buy_price_max":"1","goods_sell_price_min":"0.97","goods_sell_price_max":"1","snrc_bs":null}
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
         * goods_min : 100
         * order_sell_fee : 0.005
         * order_buy_fee : 0
         * trade_sell_fee : 0.003
         * trade_buy_fee : 0.003
         * recharge_fee : 0.005
         * cash_fee : 0.005
         * goods_buy_price_min : 0.95
         * goods_buy_price_max : 1
         * goods_sell_price_min : 0.97
         * goods_sell_price_max : 1
         * snrc_bs : null
         */

        private String goods_min;
        private String order_sell_fee;
        private String order_buy_fee;
        private String trade_sell_fee;
        private String trade_buy_fee;
        private String recharge_fee;
        private String cash_fee;
        private String goods_buy_price_min;
        private String goods_buy_price_max;
        private String goods_sell_price_min;
        private String goods_sell_price_max;
        private String snrc_bs;

        public String getGoods_min() {
            return goods_min;
        }

        public void setGoods_min(String goods_min) {
            this.goods_min = goods_min;
        }

        public String getOrder_sell_fee() {
            return order_sell_fee;
        }

        public void setOrder_sell_fee(String order_sell_fee) {
            this.order_sell_fee = order_sell_fee;
        }

        public String getOrder_buy_fee() {
            return order_buy_fee;
        }

        public void setOrder_buy_fee(String order_buy_fee) {
            this.order_buy_fee = order_buy_fee;
        }

        public String getTrade_sell_fee() {
            return trade_sell_fee;
        }

        public void setTrade_sell_fee(String trade_sell_fee) {
            this.trade_sell_fee = trade_sell_fee;
        }

        public String getTrade_buy_fee() {
            return trade_buy_fee;
        }

        public void setTrade_buy_fee(String trade_buy_fee) {
            this.trade_buy_fee = trade_buy_fee;
        }

        public String getRecharge_fee() {
            return recharge_fee;
        }

        public void setRecharge_fee(String recharge_fee) {
            this.recharge_fee = recharge_fee;
        }

        public String getCash_fee() {
            return cash_fee;
        }

        public void setCash_fee(String cash_fee) {
            this.cash_fee = cash_fee;
        }

        public String getGoods_buy_price_min() {
            return goods_buy_price_min;
        }

        public void setGoods_buy_price_min(String goods_buy_price_min) {
            this.goods_buy_price_min = goods_buy_price_min;
        }

        public String getGoods_buy_price_max() {
            return goods_buy_price_max;
        }

        public void setGoods_buy_price_max(String goods_buy_price_max) {
            this.goods_buy_price_max = goods_buy_price_max;
        }

        public String getGoods_sell_price_min() {
            return goods_sell_price_min;
        }

        public void setGoods_sell_price_min(String goods_sell_price_min) {
            this.goods_sell_price_min = goods_sell_price_min;
        }

        public String getGoods_sell_price_max() {
            return goods_sell_price_max;
        }

        public void setGoods_sell_price_max(String goods_sell_price_max) {
            this.goods_sell_price_max = goods_sell_price_max;
        }

        public String getSnrc_bs() {
            return snrc_bs;
        }

        public void setSnrc_bs(String snrc_bs) {
            this.snrc_bs = snrc_bs;
        }
    }
}
