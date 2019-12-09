package com.madaex.exchange.ui.market.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  TransactionList.java
 * 时间：  2018/8/31 14:55
 * 描述：  ${TODO}
 */

public class TransactionList extends CommonBaseBean {
    /**
     * status : 1
     * data : [{"id":"60","num":"100.00","addtime":"2018年08月30日12:18","status":"处理中","type":"买入GRC","number":"GRC100.00","state":1}]
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
        public boolean isShow = false;
        private String id;
        private String num;
        private String addtime;
        private String status;
        private String type;
        private String number;
        private int state;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
