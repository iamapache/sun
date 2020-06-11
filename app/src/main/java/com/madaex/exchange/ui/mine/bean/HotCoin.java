package com.madaex.exchange.ui.mine.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  HotCoin.java
 * 时间：  2020/1/6 16:31
 * 描述：
 */
public class HotCoin {
    /**
     * status : 1
     * msg : 成功
     * data : [{"change":"-3.21%","new_price1":"0.00004065","status":0,"name":"WTC/BTC"},{"change":"0%","new_price1":"0.00001967","status":0,"name":"HTT/BTC"},{"change":"-0.04%","new_price1":"0.094221","status":0,"name":"USDD/USDT"},{"change":"0%","new_price1":"0.000386","status":0,"name":"USDD/ETH"},{"change":"0%","new_price1":"0.00000965","status":0,"name":"USDD/BTC"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * change : -3.21%
         * new_price1 : 0.00004065
         * status : 0
         * name : WTC/BTC
         */

        private String change;
        private String new_price1;
        private int status;
        private String name;

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getNew_price1() {
            return new_price1;
        }

        public void setNew_price1(String new_price1) {
            this.new_price1 = new_price1;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
