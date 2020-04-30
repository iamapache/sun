package com.madaex.exchange.ui.mine.bean;

import java.math.BigDecimal;
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
     * data : [{"name":"ALSC/USDT","change":"-4.13%","new_price1":5.4983,"status":0},{"name":"ETH/USDT","change":"1.51%","new_price1":139.29,"status":1},{"name":"BAT/USDT","change":"-0.42%","new_price1":0.1908,"status":0},{"name":"OMG/USDT","change":"0.23%","new_price1":0.6497,"status":1},{"name":"SNT/USDT","change":"-0.03%","new_price1":0.009127,"status":0}]
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
         * name : ALSC/USDT
         * change : -4.13%
         * new_price1 : 5.4983
         * status : 0
         */

        private String name;
        private String change;
        private BigDecimal new_price1;
        private int status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public BigDecimal getNew_price1() {
            return new_price1;
        }

        public void setNew_price1(BigDecimal new_price1) {
            this.new_price1 = new_price1;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
