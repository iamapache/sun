package com.madaex.exchange.ui.mine.bean;

/**
 * 项目：  sun
 * 类名：  Urlbean.java
 * 时间：  2020/3/12 16:01
 * 描述：
 */
public class Urlbean {

    /**
     * status : 1
     * data : {"url":"http://chat.uoou.net:9099/index.php?p=quickstart&sp=2"}
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
         * url : http://chat.uoou.net:9099/index.php?p=quickstart&sp=2
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
