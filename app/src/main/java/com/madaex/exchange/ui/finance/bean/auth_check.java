package com.madaex.exchange.ui.finance.bean;

/**
 * 项目：  sun
 * 类名：  auth_check.java
 * 时间：  2020/4/28 16:04
 * 描述：
 */
public class auth_check {
    /**
     * status : 1
     * data : {"is_auth_check":true,"is_auth":0,"message":"为了您的资产安全，请实名认证！"}
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
         * is_auth_check : true
         * is_auth : 0
         * message : 为了您的资产安全，请实名认证！
         */

        private boolean is_auth_check;
        private int is_auth;
        private String message;

        public boolean isIs_auth_check() {
            return is_auth_check;
        }

        public void setIs_auth_check(boolean is_auth_check) {
            this.is_auth_check = is_auth_check;
        }

        public int getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(int is_auth) {
            this.is_auth = is_auth;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
