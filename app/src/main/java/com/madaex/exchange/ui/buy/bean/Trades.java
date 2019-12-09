package com.madaex.exchange.ui.buy.bean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  Designates.java
 * 时间：  2018/9/18 17:55
 * 描述：  ${TODO}
 */

public class Trades {
    /**
     * dataType : trades
     * data : [{"amount":"7.974","price":"0.008795","tid":26773933,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"4.266","price":"0.008795","tid":26773934,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"2.000","price":"0.008795","tid":26773935,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"0.141","price":"0.008794","tid":26773936,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"0.076","price":"0.008794","tid":26773937,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"0.162","price":"0.008794","tid":26773938,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"0.015","price":"0.008794","tid":26773939,"date":1538040111,"type":"sell","trade_type":"ask"},{"amount":"0.315","price":"0.008794","tid":26773940,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.138","price":"0.008794","tid":26773941,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.153","price":"0.008794","tid":26773942,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.259","price":"0.008794","tid":26773943,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.011","price":"0.008794","tid":26773944,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.357","price":"0.008794","tid":26773945,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.266","price":"0.008794","tid":26773946,"date":1538040112,"type":"sell","trade_type":"ask"},{"amount":"0.106","price":"0.008793","tid":26773951,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.068","price":"0.008793","tid":26773952,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.108","price":"0.008793","tid":26773953,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.039","price":"0.008793","tid":26773954,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.016","price":"0.008793","tid":26773955,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.066","price":"0.008793","tid":26773956,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.018","price":"0.008793","tid":26773957,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.001","price":"0.008793","tid":26773958,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.204","price":"0.008793","tid":26773959,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.010","price":"0.008793","tid":26773960,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.104","price":"0.008793","tid":26773961,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.506","price":"0.0088","tid":26773962,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.255","price":"0.0088","tid":26773963,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.245","price":"0.0088","tid":26773964,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.761","price":"0.0088","tid":26773965,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.451","price":"0.0088","tid":26773966,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.377","price":"0.0088","tid":26773967,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.128","price":"0.0088","tid":26773968,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.312","price":"0.0088","tid":26773969,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.496","price":"0.0088","tid":26773970,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.403","price":"0.0088","tid":26773972,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.629","price":"0.0088","tid":26773973,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.202","price":"0.0088","tid":26773974,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.124","price":"0.0088","tid":26773975,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.431","price":"0.0088","tid":26773976,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.648","price":"0.0088","tid":26773977,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.046","price":"0.0088","tid":26773978,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.526","price":"0.0088","tid":26773979,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.101","price":"0.0088","tid":26773980,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.341","price":"0.0088","tid":26773981,"date":1538040113,"type":"buy","trade_type":"bid"},{"amount":"0.564","price":"0.0088","tid":26773982,"date":1538040113,"type":"buy","trade_type":"bid"}]
     * channel : ltcETH_trades
     */

    private String dataType;
    private String channel;
    private List<DataBean> data;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 7.974
         * price : 0.008795
         * tid : 26773933
         * date : 1538040111
         * type : sell
         * trade_type : ask
         */

        private String amount;
        private String price;
        private int tid;
        private int date;
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

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
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
