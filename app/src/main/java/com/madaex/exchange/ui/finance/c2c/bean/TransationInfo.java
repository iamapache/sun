package com.madaex.exchange.ui.finance.c2c.bean;

/**
 * 项目：  madaexchange
 * 类名：  TransationInfo.java
 * 时间：  2018/9/25 15:40
 * 描述：  ${TODO}
 */

public class TransationInfo {

    /**
     * status : 1
     * data : {"sell_rate":"0.99","buy_rate":"1","cny":"0.00000000"}
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
         * sell_rate : 0.99
         * buy_rate : 1
         * cny : 0.00000000
         */

        private String sell_rate;
        private String buy_rate;
        private String cny;

        public String getSell_rate() {
            return sell_rate;
        }

        public void setSell_rate(String sell_rate) {
            this.sell_rate = sell_rate;
        }

        public String getBuy_rate() {
            return buy_rate;
        }

        public void setBuy_rate(String buy_rate) {
            this.buy_rate = buy_rate;
        }

        public String getCny() {
            return cny;
        }

        public void setCny(String cny) {
            this.cny = cny;
        }
    }
}
