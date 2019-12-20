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
     * data : {"is_merchants":0,"username":"18320814644","name":"","has_paypassword":"0","idcardauth":0,"vip":0,"userid":"20004308","invit":""}
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
         * is_merchants : 0
         * username : 18320814644
         * name :
         * has_paypassword : 0
         * idcardauth : 0
         * vip : 0
         * userid : 20004308
         * invit :
         */

        private int is_merchants;
        private String username;
        private String name;
        private String has_paypassword;
        private int idcardauth;
        private int vip;
        private String userid;
        private String invit;

        public int getIs_merchants() {
            return is_merchants;
        }

        public void setIs_merchants(int is_merchants) {
            this.is_merchants = is_merchants;
        }

        public String getUsername() {
            return username;
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

        public int getIdcardauth() {
            return idcardauth;
        }

        public void setIdcardauth(int idcardauth) {
            this.idcardauth = idcardauth;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getInvit() {
            return invit;
        }

        public void setInvit(String invit) {
            this.invit = invit;
        }
    }
}
