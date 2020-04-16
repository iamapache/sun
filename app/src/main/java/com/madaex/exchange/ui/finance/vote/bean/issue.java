package com.madaex.exchange.ui.finance.vote.bean;

/**
 * 项目：  sun
 * 类名：  issue.java
 * 时间：  2020/4/16 13:57
 * 描述：
 */
public class issue {
    /**
     * status : 1
     * data : {"id":3,"coinname":"eth","deal":1,"num":1212121,"buycoin":"eth","price":"11.0000","issue_price":"11.0000","img":"/Uploads/coin/202003071013716365ae02c4eb0662848.png","percent":"0.0001%","qc_number":"0.00"}
     * message : 操作成功
     */

    private int status;
    private DataBean data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * id : 3
         * coinname : eth
         * deal : 1
         * num : 1212121
         * buycoin : eth
         * price : 11.0000
         * issue_price : 11.0000
         * img : /Uploads/coin/202003071013716365ae02c4eb0662848.png
         * percent : 0.0001%
         * qc_number : 0.00
         */

        private int id;
        private String coinname;
        private int deal;
        private int num;
        private String buycoin;
        private String price;
        private String issue_price;
        private String img;
        private String percent;
        private String qc_number;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoinname() {
            return coinname;
        }

        public void setCoinname(String coinname) {
            this.coinname = coinname;
        }

        public int getDeal() {
            return deal;
        }

        public void setDeal(int deal) {
            this.deal = deal;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getBuycoin() {
            return buycoin;
        }

        public void setBuycoin(String buycoin) {
            this.buycoin = buycoin;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIssue_price() {
            return issue_price;
        }

        public void setIssue_price(String issue_price) {
            this.issue_price = issue_price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getQc_number() {
            return qc_number;
        }

        public void setQc_number(String qc_number) {
            this.qc_number = qc_number;
        }
    }
}
