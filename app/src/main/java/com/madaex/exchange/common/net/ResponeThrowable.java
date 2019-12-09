package com.madaex.exchange.common.net;

/**
 * 项目：  loan
 * 包名：  com.app.loan.transformer
 * 类名：  ResponeThrowable.java
 * 作者：  彭决
 * 时间：  2017/9/27 11:01
 * 描述：  ${TODO}
 */

public class ResponeThrowable extends Exception {
    public int code;
    public String message;

    public ResponeThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
