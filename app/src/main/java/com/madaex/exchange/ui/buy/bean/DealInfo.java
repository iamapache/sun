package com.madaex.exchange.ui.buy.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

/**
 * 项目：  madaexchange
 * 类名：  DealInfo.java
 * 时间：  2018/9/5 11:19
 * 描述：  ${TODO}
 */

public class DealInfo extends CommonBaseBean {

    /**
     * data : {"one_xnb":"59585.556400","two_xnb":"0.000000","trade_sell_fee":"0.003","trade_buy_fee":"0.003","rise_once":"0.00001000","buy_price_lock":0,"sell_price_lock":1,"buy_num_lock":0,"sell_num_lock":1,"buy_price_change":0.1,"sell_price_change":-0.09999999999999998,"buy_cancel_order":0,"sell_cancel_order":1}
     * message : 成功
     */

    private DataBean data;
    private String message;

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

    public static class DataBean {
        /**
         * one_xnb : 59585.556400
         * two_xnb : 0.000000
         * trade_sell_fee : 0.003
         * trade_buy_fee : 0.003
         * rise_once : 0.00001000
         * buy_price_lock : 0
         * sell_price_lock : 1
         * buy_num_lock : 0
         * sell_num_lock : 1
         * buy_price_change : 0.1
         * sell_price_change : -0.09999999999999998
         * buy_cancel_order : 0
         * sell_cancel_order : 1
         */

        private String one_xnb;
        private String two_xnb;
        private String trade_sell_fee;
        private String trade_buy_fee;
        private String rise_once;
        private int buy_price_lock;
        private int sell_price_lock;
        private int buy_num_lock;
        private int sell_num_lock;
        private double buy_price_change;
        private double sell_price_change;
        private int buy_cancel_order;
        private int sell_cancel_order;

        public String getOne_xnb() {
            return one_xnb;
        }

        public void setOne_xnb(String one_xnb) {
            this.one_xnb = one_xnb;
        }

        public String getTwo_xnb() {
            return two_xnb;
        }

        public void setTwo_xnb(String two_xnb) {
            this.two_xnb = two_xnb;
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

        public String getRise_once() {
            return rise_once;
        }

        public void setRise_once(String rise_once) {
            this.rise_once = rise_once;
        }

        public int getBuy_price_lock() {
            return buy_price_lock;
        }

        public void setBuy_price_lock(int buy_price_lock) {
            this.buy_price_lock = buy_price_lock;
        }

        public int getSell_price_lock() {
            return sell_price_lock;
        }

        public void setSell_price_lock(int sell_price_lock) {
            this.sell_price_lock = sell_price_lock;
        }

        public int getBuy_num_lock() {
            return buy_num_lock;
        }

        public void setBuy_num_lock(int buy_num_lock) {
            this.buy_num_lock = buy_num_lock;
        }

        public int getSell_num_lock() {
            return sell_num_lock;
        }

        public void setSell_num_lock(int sell_num_lock) {
            this.sell_num_lock = sell_num_lock;
        }

        public double getBuy_price_change() {
            return buy_price_change;
        }

        public void setBuy_price_change(double buy_price_change) {
            this.buy_price_change = buy_price_change;
        }

        public double getSell_price_change() {
            return sell_price_change;
        }

        public void setSell_price_change(double sell_price_change) {
            this.sell_price_change = sell_price_change;
        }

        public int getBuy_cancel_order() {
            return buy_cancel_order;
        }

        public void setBuy_cancel_order(int buy_cancel_order) {
            this.buy_cancel_order = buy_cancel_order;
        }

        public int getSell_cancel_order() {
            return sell_cancel_order;
        }

        public void setSell_cancel_order(int sell_cancel_order) {
            this.sell_cancel_order = sell_cancel_order;
        }
    }
}
