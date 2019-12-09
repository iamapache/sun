package com.madaex.exchange.ui.finance.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  TransaList.java
 * 时间：  2018/8/31 10:18
 * 描述：  ${TODO}
 */

public class TransaList extends CommonBaseBean {

    /**
     * status : 1
     * data : [{"id":"60","num":"100.00","addtime":"2018年08月30日12:18","status":"处理中","type":"买入GRC","number":"GRC100.00","state":1},{"id":"61","num":"100.00","addtime":"2018年08月31日15:59","status":"处理中","type":"买入GRC","number":"GRC100.00","state":1},{"id":"63","num":"100.00","addtime":"2018年09月04日17:02","status":"处理中","type":"买入GRC","number":"GRC100.00","state":1}]
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 60
         * num : 100.00
         * addtime : 2018年08月30日12:18
         * status : 处理中
         * type : 买入GRC
         * number : GRC100.00
         * state : 1
         */

        private String id;
        private String num;
        private String addtime;
        private String status;
        private String username;
        private String coinname;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCoinname() {
            return coinname;
        }

        public void setCoinname(String coinname) {
            this.coinname = coinname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
