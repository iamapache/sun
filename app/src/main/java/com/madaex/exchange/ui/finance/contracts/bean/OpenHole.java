package com.madaex.exchange.ui.finance.contracts.bean;

/**
 * 项目：  sun
 * 类名：  OpenHole.java
 * 时间：  2020/7/7 11:13
 * 描述：
 */
public class OpenHole {
    /**
     * status : 1
     * data : {"is_open":"false  "}
     * message : 操作成功
     */

    private int status;
    private DataBean data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * is_open : false
         */

        private boolean is_open;

        public boolean getIs_open() {
            return is_open;
        }

        public void setIs_open(boolean is_open) {
            this.is_open = is_open;
        }
    }
}
