package com.madaex.exchange.ui.buy.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

/**
 * 项目：  madaexchange
 * 类名：  DealInfo.java
 * 时间：  2018/9/5 11:19
 * 描述：  ${TODO}
 */

public class DealInfo extends CommonBaseBean {

    /**
     * status : 1
     * data : {"one_xnb":"0.00000000","one_xnbd":"0.00000000","two_xnb":"0.00000000","two_xnbd":"0.00000000"}
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
         * one_xnb : 0.00000000
         * one_xnbd : 0.00000000
         * two_xnb : 0.00000000
         * two_xnbd : 0.00000000
         */

        private String one_xnb;
        private String one_xnbd;
        private String two_xnb;
        private String two_xnbd;

        public String getOne_xnb() {
            return one_xnb;
        }

        public void setOne_xnb(String one_xnb) {
            this.one_xnb = one_xnb;
        }

        public String getOne_xnbd() {
            return one_xnbd;
        }

        public void setOne_xnbd(String one_xnbd) {
            this.one_xnbd = one_xnbd;
        }

        public String getTwo_xnb() {
            return two_xnb;
        }

        public void setTwo_xnb(String two_xnb) {
            this.two_xnb = two_xnb;
        }

        public String getTwo_xnbd() {
            return two_xnbd;
        }

        public void setTwo_xnbd(String two_xnbd) {
            this.two_xnbd = two_xnbd;
        }
    }
}
