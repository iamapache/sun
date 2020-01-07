package com.madaex.exchange.ui.mine.bean;

/**
 * 项目：  sun
 * 类名：  NoticeData.java
 * 时间：  2020/1/6 15:49
 * 描述：
 */
public class NoticeData {
    /**
     * status : 1
     * data : {"txt":"上线活动充值赠送","url":"alsc.uoou.net/web/index/help.html?typeId=53"}
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
         * txt : 上线活动充值赠送
         * url : alsc.uoou.net/web/index/help.html?typeId=53
         */

        private String txt;
        private String url;

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
