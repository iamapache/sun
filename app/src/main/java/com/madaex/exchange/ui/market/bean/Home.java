package com.madaex.exchange.ui.market.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 项目：  madaexchange
 * 类名：  Home.java
 * 时间：  2018/9/13 16:00
 * 描述：  ${TODO}
 */

public class Home   implements Parcelable {

    /**
     * result : true
     * data : [{"high":23.89,"currentype":"qtum","coinImageUrl":"","low":21.8,"volumn":967695.45,"currentPrice":22,"exchangeType":"GRC","collection":"","riseRate":"0.95%","sellRmb":22},{"high":23.59,"currentype":"omg","coinImageUrl":"","low":21.19,"volumn":1917.78,"currentPrice":21.68,"exchangeType":"GRC","collection":"","riseRate":"1.45%","sellRmb":21.68},{"high":15.3,"currentype":"hc","coinImageUrl":"","low":13.7,"volumn":4082678.09,"currentPrice":14.123,"exchangeType":"GRC","collection":"","riseRate":"0.95%","sellRmb":14.123},{"high":0.044989,"currentype":"doge","coinImageUrl":"","low":0.042961,"volumn":1018458350,"currentPrice":0.04395,"exchangeType":"GRC","collection":"","riseRate":"-0.73%","sellRmb":0.04395},{"high":1532.31,"currentype":"eth","coinImageUrl":"","low":1351,"volumn":878699.929,"currentPrice":1372.6,"exchangeType":"GRC","collection":"","riseRate":"4.61%","sellRmb":1372.6},{"high":0.482,"currentype":"ada","coinImageUrl":"","low":0.435,"volumn":9.14684448E7,"currentPrice":0.438,"exchangeType":"GRC","collection":"","riseRate":"3.52%","sellRmb":0.438},{"high":3.8,"currentype":"zrx","coinImageUrl":"","low":3.521,"volumn":2464.5,"currentPrice":3.58,"exchangeType":"GRC","collection":"","riseRate":"0.56%","sellRmb":3.58},{"high":4.34,"currentype":"icx","coinImageUrl":"","low":4.01,"volumn":3625.8,"currentPrice":4.03,"exchangeType":"GRC","collection":"","riseRate":"4.73%","sellRmb":4.03},{"high":0.94,"currentype":"gnt","coinImageUrl":"","low":0.848,"volumn":16445.7,"currentPrice":0.848,"exchangeType":"GRC","collection":"","riseRate":"9.69%","sellRmb":0.848},{"high":395.87,"currentype":"ltc","coinImageUrl":"","low":354.21,"volumn":148373.504,"currentPrice":356.25,"exchangeType":"GRC","collection":"","riseRate":"4.25%","sellRmb":356.25},{"high":1.431,"currentype":"xlm","coinImageUrl":"","low":1.361,"volumn":121695.3,"currentPrice":1.361,"exchangeType":"GRC","collection":"","riseRate":"1.38%","sellRmb":1.361},{"high":3152.3,"currentype":"bch","coinImageUrl":"","low":2870,"volumn":50659.39,"currentPrice":2923.32,"exchangeType":"GRC","collection":"","riseRate":"0.57%","sellRmb":2923.32},{"high":1367,"currentype":"dash","coinImageUrl":"","low":1251.3,"volumn":207733.369,"currentPrice":1278.66,"exchangeType":"GRC","collection":"","riseRate":"2.57%","sellRmb":1278.66},{"high":37.48,"currentype":"eos","coinImageUrl":"","low":33,"volumn":1.71182726E7,"currentPrice":33.737,"exchangeType":"GRC","collection":"","riseRate":"1.94%","sellRmb":33.737},{"high":6.88,"currentype":"ae","coinImageUrl":"","low":6.21,"volumn":4618429.41,"currentPrice":6.39,"exchangeType":"GRC","collection":"","riseRate":"0.00%","sellRmb":6.39},{"high":77.59,"currentype":"etc","coinImageUrl":"","low":70.56,"volumn":901835.63,"currentPrice":72.37,"exchangeType":"GRC","collection":"","riseRate":"1.82%","sellRmb":72.37},{"high":1.142,"currentype":"bat","coinImageUrl":"","low":0.989,"volumn":28947.9,"currentPrice":0.989,"exchangeType":"GRC","collection":"","riseRate":"3.79%","sellRmb":0.989},{"high":125.35,"currentype":"neo","coinImageUrl":"","low":114,"volumn":30925.63,"currentPrice":115.67,"exchangeType":"GRC","collection":"","riseRate":"3.50%","sellRmb":115.67},{"high":1.95,"currentype":"xrp","coinImageUrl":"","low":1.8741,"volumn":11586989,"currentPrice":1.91,"exchangeType":"GRC","collection":"","riseRate":"-0.97%","sellRmb":1.91},{"high":45034.88,"currentype":"ETH","coinImageUrl":"","low":43500,"volumn":8320.834,"currentPrice":43763.06,"exchangeType":"GRC","collection":"","riseRate":"-0.17%","sellRmb":43763.06},{"high":0.2446,"currentype":"snt","coinImageUrl":"","low":0.22,"volumn":340544.6,"currentPrice":0.2201,"exchangeType":"GRC","collection":"","riseRate":"1.30%","sellRmb":0.2201},{"high":0.642,"currentype":"xem","coinImageUrl":"","low":0.582,"volumn":14965,"currentPrice":0.582,"exchangeType":"GRC","collection":"","riseRate":"3.00%","sellRmb":0.582}]
     * message : SUCCESS
     */

        /**
         * high : 23.89
         * currentype : qtum
         * coinImageUrl :
         * low : 21.8
         * volumn : 967695.45
         * currentPrice : 22
         * exchangeType : GRC
         * collection :
         * riseRate : 0.95%
         * sellRmb : 22
         */
public boolean isShow=false;
        private String high;
        private String currentype;
        private String coinImageUrl;
        private String low;
        private String volumn;
        private String currentPrice;
        private String exchangeType;
        private int collection;
        private String riseRate;
        private String sellRmb;
    private int status=3;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getSellRmb() {
        return sellRmb;
    }

    public void setSellRmb(String sellRmb) {
        this.sellRmb = sellRmb;
    }

    public String getCurrentype() {
            return currentype;
        }

        public void setCurrentype(String currentype) {
            this.currentype = currentype;
        }

        public String getCoinImageUrl() {
            return coinImageUrl;
        }

        public void setCoinImageUrl(String coinImageUrl) {
            this.coinImageUrl = coinImageUrl;
        }


        public String getVolumn() {
            return volumn;
        }

        public void setVolumn(String volumn) {
            this.volumn = volumn;
        }


        public String getExchangeType() {
            return exchangeType;
        }

        public void setExchangeType(String exchangeType) {
            this.exchangeType = exchangeType;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public String getRiseRate() {
            return riseRate;
        }

        public void setRiseRate(String riseRate) {
            this.riseRate = riseRate;
        }


    public Home() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        dest.writeString(this.high);
        dest.writeString(this.currentype);
        dest.writeString(this.coinImageUrl);
        dest.writeString(this.low);
        dest.writeString(this.volumn);
        dest.writeString(this.currentPrice);
        dest.writeString(this.exchangeType);
        dest.writeInt(this.collection);
        dest.writeString(this.riseRate);
        dest.writeString(this.sellRmb);
    }

    protected Home(Parcel in) {
        this.isShow = in.readByte() != 0;
        this.high = in.readString();
        this.currentype = in.readString();
        this.coinImageUrl = in.readString();
        this.low = in.readString();
        this.volumn = in.readString();
        this.currentPrice = in.readString();
        this.exchangeType = in.readString();
        this.collection = in.readInt();
        this.riseRate = in.readString();
        this.sellRmb = in.readString();
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel source) {
            return new Home(source);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };
}
