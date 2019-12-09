package com.madaex.exchange.ui.market.bean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  HomeData.java
 * 时间：  2018/10/25 12:25
 * 描述：  ${TODO}
 */

public class HomeData {

    /**
     * status : 1
     * data : [{"volumn":"389095.873","currentype":"eth","coinImageUrl":"http://112.74.74.238:89/Upload/coin/5bab3b1f6e01b.png","currentPrice":"389095.873","high":"1410.0","collection":1,"riseRate":"-0.09%","exchangeType":"GRC","sellRmb":"1386.7","low":"1377.0"}]
     */

    private int status;
    private List<Home> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Home> getData() {
        return data;
    }

    public void setData(List<Home> data) {
        this.data = data;
    }

}
