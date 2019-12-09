package com.madaex.exchange.ui.finance.c2c.bean;

/**
 * 项目：  madaexchange
 * 类名：  TransationDetail.java
 * 时间：  2018/10/18 16:29
 * 描述：  ${TODO}
 */

public class TransationDetail {


    /**
     * status : 1
     * data : {"id":"62","username":"5d64d944f5e1dd6aecb34d458c1267f8","coinname":"CNY","addtime":"2018-10-18 11:48:41","endtime":"2018-10-18 11:48:41","num":"2.0000","mum":"1.9960","fee":"0.0040","status":"确认成功"}
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
         * id : 62
         * username : 5d64d944f5e1dd6aecb34d458c1267f8
         * coinname : CNY
         * addtime : 2018-10-18 11:48:41
         * endtime : 2018-10-18 11:48:41
         * num : 2.0000
         * mum : 1.9960
         * fee : 0.0040
         * status : 确认成功
         */

        private String id;
        private String username;
        private String coinname;
        private String addtime;
        private String endtime;
        private String num;
        private String mum;
        private String fee;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getMum() {
            return mum;
        }

        public void setMum(String mum) {
            this.mum = mum;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
