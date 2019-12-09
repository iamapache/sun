package com.madaex.exchange.ui.buy.bean;

/**
 * 项目：  madaexchange
 * 类名：  Ticker.java
 * 时间：  2018/9/30 16:25
 * 描述：  ${TODO}
 */

public class Ticker extends BaseSocket{

    /**
     * date : 1538295819366
     * channel : aeGRC_ticker
     * dataType : ticker
     * ticker : {"vol":"7816355.84","high":"7.6","low":"6.8","last":"7.32","buy":"7.32","sell":"7.35"}
     */

    private TickerBean ticker;


    public TickerBean getTicker() {
        return ticker;
    }

    public void setTicker(TickerBean ticker) {
        this.ticker = ticker;
    }

    public static class TickerBean {
        /**
         * vol : 7816355.84
         * high : 7.6
         * low : 6.8
         * last : 7.32
         * buy : 7.32
         * sell : 7.35
         */

        private String vol;
        private String high;
        private String low;
        private String last;
        private String buy;
        private String sell;

        public String getVol() {
            return vol;
        }

        public void setVol(String vol) {
            this.vol = vol;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getBuy() {
            return buy;
        }

        public void setBuy(String buy) {
            this.buy = buy;
        }

        public String getSell() {
            return sell;
        }

        public void setSell(String sell) {
            this.sell = sell;
        }
    }
}
