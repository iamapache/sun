package com.madaex.exchange.ui.buy.bean;

/**
 * @author Administrator
 * @date 2018/6/20
 */

public class Event<T extends Object> {
    private int code = 0;
    private T data = null;
    private String msg = "";
    private String heyue = "0";
    public int getCode() {
        return code;
    }

    public String getHeyue() {
        return heyue;
    }

    public void setHeyue(String heyue) {
        this.heyue = heyue;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
