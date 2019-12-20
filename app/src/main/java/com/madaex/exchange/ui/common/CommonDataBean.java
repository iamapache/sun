package com.madaex.exchange.ui.common;

/**
 * 项目：  sun
 * 类名：  CommonDataBean.java
 * 时间：  2019/12/20 17:50
 * 描述：
 */
public class CommonDataBean {

    /**
     * status : 1
     * data : {"token":"saV1qK-npt2zfXjZfqevsIN0eGiz4KNmmn2nZLKwj5mx24mqr83Mow","user_id":"20004308"}
     */

    private int status;
    private DataBean data;

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

    public static class DataBean {
        /**
         * token : saV1qK-npt2zfXjZfqevsIN0eGiz4KNmmn2nZLKwj5mx24mqr83Mow
         * user_id : 20004308
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
