package com.madaex.exchange.ui.market.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  HistoryDetail2.java
 * 时间：  2020/1/4 16:38
 * 描述：
 */
public class HistoryDetail2 {

    /**
     * asks : [[0.5,4202],[0.200001,300],[0.1149,100],[0.1147,500],[0.1144,100],[0.1142,2000],[0.005,5000],[0.001328,10000],[0.00129,100000]]
     * bids : [[5,10],[1,3000],[0.6,3000],[0.452,9000],[0.1144,88597.6],[0.0045,10000]]
     * channel : alscusdt_depth
     * dataType : depth
     * timestamp : 1578127050
     */

    private String channel;
    private String dataType;
    private int timestamp;
    private List<List<Double>> asks;
    private List<List<Integer>> bids;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<List<Double>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<Double>> asks) {
        this.asks = asks;
    }

    public List<List<Integer>> getBids() {
        return bids;
    }

    public void setBids(List<List<Integer>> bids) {
        this.bids = bids;
    }
}
