package com.madaex.exchange.ui.market.bean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  EntrustDetail.java
 * 时间：  2018/10/19 10:32
 * 描述：  ${TODO}
 */

public class EntrustDetail {

    /**
     * status : 1
     * data : {"no_deal":"8.3742","deal":[{"num":"0.0130","price":"22.0000","mum":"0.2860"},{"num":"0.0130","price":"22.0000","mum":"0.2860"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.0999","price":"22.0000","mum":"2.1978"},{"num":"0.0999","price":"22.0000","mum":"2.1978"}]}
     */

    private int status;
    private DataBean data;

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

    public static class DataBean {
        /**
         * no_deal : 8.3742
         * deal : [{"num":"0.0130","price":"22.0000","mum":"0.2860"},{"num":"0.0130","price":"22.0000","mum":"0.2860"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.6000","price":"22.0000","mum":"13.2000"},{"num":"0.0999","price":"22.0000","mum":"2.1978"},{"num":"0.0999","price":"22.0000","mum":"2.1978"}]
         */

        private String no_deal;
        private List<DealBean> deal;

        public String getNo_deal() {
            return no_deal;
        }

        public void setNo_deal(String no_deal) {
            this.no_deal = no_deal;
        }

        public List<DealBean> getDeal() {
            return deal;
        }

        public void setDeal(List<DealBean> deal) {
            this.deal = deal;
        }

        public static class DealBean {
            /**
             * num : 0.0130
             * price : 22.0000
             * mum : 0.2860
             */

            private String num;
            private String price;
            private String mum;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getMum() {
                return mum;
            }

            public void setMum(String mum) {
                this.mum = mum;
            }
        }
    }
}
