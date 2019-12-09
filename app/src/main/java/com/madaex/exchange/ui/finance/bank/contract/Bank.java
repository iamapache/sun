package com.madaex.exchange.ui.finance.bank.contract;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  Bank.java
 * 时间：  2018/8/29 16:08
 * 描述：  ${TODO}
 */

public class Bank extends CommonBaseBean {

    /**
     * status : 1
     * data : [{"id":"11","name":"收*","bank":"浦发银行","bankcard":"***************6236"}]
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
         * id : 11
         * name : 收*
         * bank : 浦发银行
         * bankcard : ***************6236
         */

        private String id;
        private String name;
        private String bank;
        private String bankcard;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }
    }
}
