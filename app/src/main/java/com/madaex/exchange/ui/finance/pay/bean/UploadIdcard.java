package com.madaex.exchange.ui.finance.pay.bean;

/**
 * 项目：  sun
 * 类名：  UploadIdcard.java
 * 时间：  2020/6/13 18:05
 * 描述：
 */
public class UploadIdcard {
    /**
     * data : {"url":"Uploads/idcardImg/20200612/7c46c02f177717d6ca661fb83f61e42b.jpg"}
     * message : 操作成功
     * status : 1
     */

    private DataBean data;
    private String message;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * url : Uploads/idcardImg/20200612/7c46c02f177717d6ca661fb83f61e42b.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
