package com.madaex.exchange.ui.market.bean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  TitleBean.java
 * 时间：  2018/10/25 12:12
 * 描述：  ${TODO}
 */

public class TitleBean {

    /**
     * status : 1
     * data : ["自选","GRC","ETH","USDT"]
     */

    private int status;
    private List<String> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
