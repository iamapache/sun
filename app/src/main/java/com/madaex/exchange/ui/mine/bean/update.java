package com.madaex.exchange.ui.mine.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

/**
 * 项目：  madaexchange
 * 类名：  update.java
 * 时间：  2018/10/22 17:25
 * 描述：  ${TODO}
 */

public class update extends CommonBaseBean {

    /**
     * status : 1
     * data : {"is_update":"1","port_sn":"9e304d4e8df1b74cfa009913198428ab","url":"https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk","log":"my friend"}
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * is_update : 1
         * port_sn : 9e304d4e8df1b74cfa009913198428ab
         * url : https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/sample-debug.apk
         * log : my friend
         */

        private String is_update;
        private String port_sn;
        private String url;
        private String log;
        private String title;
        public String getIs_update() {
            return is_update;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setIs_update(String is_update) {
            this.is_update = is_update;
        }

        public String getPort_sn() {
            return port_sn;
        }

        public void setPort_sn(String port_sn) {
            this.port_sn = port_sn;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }
}
