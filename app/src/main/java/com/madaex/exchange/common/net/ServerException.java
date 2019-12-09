package com.madaex.exchange.common.net;

/**
 * 项目：  loan
 * 包名：  com.app.loan.transformer
 * 类名：  ServerException.java
 * 作者：  彭决
 * 时间：  2017/9/27 11:03
 * 描述：  ${TODO}
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(String message, int code) {
        this.message=message;
        this.code = code;
    }
}
