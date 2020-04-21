package com.madaex.exchange.ui.common;

/**
 * 项目：  sun
 * 类名：  DataBean.java
 * 时间：  2020/1/9 22:56
 * 描述：
 */
public class DataBean {
    /**
     * status : 1
     * data : []
     * message : 操作成功
     */

    private int status;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
