package com.madaex.exchange.ui.common;

/**
 * 项目：  sun
 * 类名：  SB2Data.java
 * 时间：  2020/2/14 16:28
 * 描述：
 */
public class SB2Data {
    private int status;
    private String data;
    private String msg;
    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
