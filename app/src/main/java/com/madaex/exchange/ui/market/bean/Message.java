package com.madaex.exchange.ui.market.bean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  Message.java
 * 时间：  2018/8/31 11:39
 * 描述：  ${TODO}
 */

public class Message {

    /**
     * status : 1
     * data : [{"is_read":0,"id":10,"add_time":"2018-11-15 10:27:27","title":"sssss"},{"is_read":0,"id":8,"add_time":"2018-11-05 09:39:43","title":"标题888"},{"is_read":0,"id":7,"add_time":"2018-11-05 09:39:28","title":"标题777"},{"is_read":0,"id":6,"add_time":"2018-11-05 09:39:43","title":"标题6"},{"is_read":0,"id":5,"add_time":"2018-11-05 09:39:43","title":"标题5"},{"is_read":0,"id":4,"add_time":"2018-11-05 09:39:43","title":"标题4"},{"is_read":0,"id":3,"add_time":"2018-11-05 09:39:43","title":"标题3"},{"is_read":0,"id":2,"add_time":"2018-11-05 09:39:43","title":"这是标题2"},{"is_read":0,"id":1,"add_time":"2018-11-05 09:39:43","title":"这是标题1"}]
     * message : 操作成功
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * is_read : 0
         * id : 10
         * add_time : 2018-11-15 10:27:27
         * title : sssss
         */

        private int is_read;
        private int id;
        private String add_time;
        private String title;

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
