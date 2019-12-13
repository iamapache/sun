package com.madaex.exchange.ui.finance.pay.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  PlatRecord.java
 * 时间：  2019/5/17 14:43
 * 描述：  ${TODO}
 */
public class PlatRecord {


    /**
     * status : 1
     * data : [{"id":"231","userid":"352","coinname":"0.05","num_a":"10.00000000","num_b":"10.00000000","num":-0.05000000000000071,"fee":"0.00000000","type":"4","name":"金数云转入","nameid":"0","remark":"eth1557458655581942","mum_a":"10.05000000","mum_b":"10.00000000","mum":"20.05000000","move":"","addtime":"1557458655","status":"1"},{"id":"234","userid":"352","coinname":"2","num_a":"0.00000000","num_b":"0.00000000","num":-2,"fee":"0.00000000","type":"4","name":"金数云转入","nameid":"0","remark":"23","mum_a":"2.00000000","mum_b":"0.00000000","mum":"2.00000000","move":"cny","addtime":"1557991409","status":"1"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 231
         * userid : 352
         * coinname : 0.05
         * num_a : 10.00000000
         * num_b : 10.00000000
         * num : -0.05000000000000071
         * fee : 0.00000000
         * type : 4
         * name : 金数云转入
         * nameid : 0
         * remark : eth1557458655581942
         * mum_a : 10.05000000
         * mum_b : 10.00000000
         * mum : 20.05000000
         * move :
         * addtime : 1557458655
         * status : 1
         */
        private String id;
        private String userid;
        private String coinname;
        private String num_a;
        private String num_b;
        private double num;
        private String fee;
        private String type;
        private String name;
        private String nameid;
        private String remark;
        private String mum_a;
        private String mum_b;
        private String mum;
        private String move;
        private String addtime;
        private String status;
        private String address;
        public String getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getCoinname() {
            return coinname;
        }

        public void setCoinname(String coinname) {
            this.coinname = coinname;
        }

        public String getNum_a() {
            return num_a;
        }

        public void setNum_a(String num_a) {
            this.num_a = num_a;
        }

        public String getNum_b() {
            return num_b;
        }

        public void setNum_b(String num_b) {
            this.num_b = num_b;
        }

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameid() {
            return nameid;
        }

        public void setNameid(String nameid) {
            this.nameid = nameid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMum_a() {
            return mum_a;
        }

        public void setMum_a(String mum_a) {
            this.mum_a = mum_a;
        }

        public String getMum_b() {
            return mum_b;
        }

        public void setMum_b(String mum_b) {
            this.mum_b = mum_b;
        }

        public String getMum() {
            return mum;
        }

        public void setMum(String mum) {
            this.mum = mum;
        }

        public String getMove() {
            return move;
        }

        public void setMove(String move) {
            this.move = move;
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
