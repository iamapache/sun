package com.madaex.exchange.ui.common;

/**
 * 项目：  sun
 * 类名：  SBData.java
 * 时间：  2020/1/6 17:51
 * 描述：
 */
public class SBData {

    /**
     * code : 0
     * msg : 余额不足
     * data :
     * url : javascript:history.back(-1);
     * wait : 3
     */

    private int code;
    private String msg;
    private String url;
    private int wait;
    /**
     * status : 0
     * message : 可用 余额不足
     */

    private int status;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public int getStatus() {
        return status;
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
