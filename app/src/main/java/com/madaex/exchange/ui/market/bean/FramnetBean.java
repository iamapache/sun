package com.madaex.exchange.ui.market.bean;

/**
 * 项目：  madaexchange
 * 类名：  Position.java
 * 时间：  2018/12/4 13:57
 * 描述：  ${TODO}
 */
public class FramnetBean {

    private String one_xnb;
    private String two_xnb;
    private String market_type;
    private String msg = "";

    public String getOne_xnb() {
        return one_xnb;
    }

    public void setOne_xnb(String one_xnb) {
        this.one_xnb = one_xnb;
    }

    public String getTwo_xnb() {
        return two_xnb;
    }

    public void setTwo_xnb(String two_xnb) {
        this.two_xnb = two_xnb;
    }

    public String getMarket_type() {
        return market_type;
    }

    public void setMarket_type(String market_type) {
        this.market_type = market_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
