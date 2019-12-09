package com.madaex.exchange.ui.market.bean;

/**
 * 项目：  madaexchange
 * 类名：  LineDetail.java
 * 时间：  2018/10/25 14:35
 * 描述：  ${TODO}
 */

public class LineDetail {

    /**
     * status : 1
     * data : {"volumn":"389244.309","currentype":"eth","coinImageUrl":"http://112.74.74.238:89/Upload/coin/5bab3b1f6e01b.png","currentPrice":"1386.99","high":"1410.0","collection":1,"riseRate":"-0.07%","exchangeType":"GRC","sellRmb":"1386.99","low":"1377.0"}
     */

    private int status;
    private Home data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Home getData() {
        return data;
    }

    public void setData(Home data) {
        this.data = data;
    }

}
