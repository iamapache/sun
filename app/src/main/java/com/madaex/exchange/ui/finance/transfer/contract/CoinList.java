package com.madaex.exchange.ui.finance.transfer.contract;

import java.util.List;

/**
 * 项目：  sunn
 * 类名：  CoinList.java
 * 时间：  2019/5/27 11:21
 * 描述：  ${TODO}
 */
public class CoinList {

    /**
     * status : 1
     * data : ["GRC","SNRC","ETH","BAT","OMG","SNT","AE","ICX","ZRX","GNT"]
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
