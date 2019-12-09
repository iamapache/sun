package com.madaex.exchange.common.base;

/**
 * 项目：  frame
 * 类名：  BaseBean.java
 * 时间：  2018/6/28 14:50
 * 描述：  ${TODO}
 */
public class BaseBean<T> {

    private int status;
    private T data;
    /**
     * result : false
     * message : 服务端忙碌
     */

    private boolean result;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "error:'" + '\'' +
                ", data:" + data +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
