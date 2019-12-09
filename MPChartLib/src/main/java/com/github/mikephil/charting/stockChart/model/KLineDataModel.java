package com.github.mikephil.charting.stockChart.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/8.
 */
public class KLineDataModel implements Serializable {
    public String m_szInstrumentID;// 合约ID

    //时间戳
    private Long dateMills = 0L;
    private double high;// 最高价
    private double low;// 最低价
    private double open;// 开盘价
    private double close;// 收盘价
    private double volume;// 成交量
    private double ma5;
    private double ma10;
    private double ma30;
    public Long getDateMills() {
        return dateMills;
    }

    public void setDateMills(Long dateMills) {
        this.dateMills = dateMills;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }


    public double getMa30() {
        return ma30;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }
}
