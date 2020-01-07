package com.madaex.exchange.ui.market.bean;

import com.madaex.exchange.ui.buy.bean.BaseSocket;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  HistoryDetail.java
 * 时间：  2018/9/30 11:29
 * 描述：  ${TODO}
 */

public class HistoryDetail extends BaseSocket {

    /**
     * dataType : trades
     * data : [{"amount":"0.01","price":"7.26","tid":8922877,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.01","price":"7.26","tid":8922878,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.07","price":"7.26","tid":8922879,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.04","price":"7.26","tid":8922880,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.01","price":"7.26","tid":8922881,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.01","price":"7.26","tid":8922882,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.09","price":"7.26","tid":8922883,"date":1538278130,"type":"sell","trade_type":"ask"},{"amount":"0.04","price":"7.26","tid":8922884,"date":1538278130,"type":"sell","trade_type":"ask"}]
     * channel : aeGRC_trades
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 0.01
         * price : 7.26
         * tid : 8922877
         * date : 1538278130
         * type : sell
         * trade_type : ask
         */

        private String amount;
        private String price;
        private Long tid;
        private String date;
        private String type;
        private String trade_type;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Long getTid() {
            return tid;
        }

        public void setTid(Long tid) {
            this.tid = tid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
