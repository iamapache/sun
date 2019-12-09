package com.madaex.exchange.ui.mine.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

/**
 * 项目：  madaexchange
 * 类名：  User.java
 * 时间：  2018/8/29 15:14
 * 描述：  ${TODO}
 */

public class User extends CommonBaseBean {

    /**
     * data : {"username":"18320814644","name":"","has_paypassword":"0","has_bank":"0"}
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
         * username : 18320814644
         * name :
         * has_paypassword : 0
         * has_bank : 0
         */

        private String username;
        private String name;
        private String has_paypassword;
        private String has_bank;
        private String userid;
        private String activity_url;
        private String is_merchants;

        public String getIs_merchants() {
            return is_merchants;
        }

        public void setIs_merchants(String is_merchants) {
            this.is_merchants = is_merchants;
        }

        public String getUsername() {
            return username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getActivity_url() {
            return activity_url;
        }

        public void setActivity_url(String activity_url) {
            this.activity_url = activity_url;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHas_paypassword() {
            return has_paypassword;
        }

        public void setHas_paypassword(String has_paypassword) {
            this.has_paypassword = has_paypassword;
        }

        public String getHas_bank() {
            return has_bank;
        }

        public void setHas_bank(String has_bank) {
            this.has_bank = has_bank;
        }
    }
}
