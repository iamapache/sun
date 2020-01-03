package com.madaex.exchange.ui.common;

/**
 * 项目：  madaexchange
 * 类名：  CommonBean.java
 * 时间：  2018/8/28 15:01
 * 描述：  ${TODO}
 */

public class CommonBean {
    private int status;
    private CommonDataBean.DataBean data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CommonDataBean.DataBean getData() {
        return data;
    }

    public void setData(CommonDataBean.DataBean data) {
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
         * token : saV1qK-npt2zfXjZfqevsIN0eGiz0Mljmn2nZLKwmdyy23Wpsae2ow
         * user_id : 20004285
         */

        private String token;
        private String user_id;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
