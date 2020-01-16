package com.madaex.exchange.ui.buy.bean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  DealBean.java
 * 时间：  2018/9/28 10:11
 * 描述：  ${TODO}
 */

public class DealBean2 {

    /**
     * asks : [[4.5599,2428.2],[4.56,1892.7],[4.5606,7546.9],[4.5608,6563.5],[4.5609,4381.3],[4.561,597.4],[4.5612,189.2],[4.5613,527.3],[4.5618,5835.4],[4.562,2516.7],[4.5623,5637.8],[4.5625,4598.6],[4.5627,6394.9],[4.5629,718.1],[4.563,6200.3],[4.5631,1496.3],[4.5632,4489.9],[4.5634,314.2],[4.5635,949.1],[4.5636,3057.3]]
     * bids : [[4.5619,7851.1],[4.5618,2965.8],[4.561,4284.9],[4.5608,7332.7],[4.5607,6857.9],[4.5605,534.4],[4.5602,1452.1],[4.5601,3071.5],[4.5598,3423.4],[4.559,4795.4],[4.5589,3957.6],[4.5586,1613.6],[4.5584,5305.6],[4.5583,2415.2],[4.5582,695.2],[4.5581,2724],[4.5578,5633.5],[4.5577,3781.3],[4.5576,8161.3]]
     * channel : alscusdt_depth
     * dataType : depth
     * timestamp : 1579085550
     */

    private String channel;
    private String dataType;
    private int timestamp;
    private List<List<Float>> asks;
    private List<List<Float>> bids;

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

    public List<List<Float>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<Float>> asks) {
        this.asks = asks;
    }

    public List<List<Float>> getBids() {
        return bids;
    }

    public void setBids(List<List<Float>> bids) {
        this.bids = bids;
    }
}
