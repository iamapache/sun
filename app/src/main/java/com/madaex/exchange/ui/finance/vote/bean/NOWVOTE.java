package com.madaex.exchange.ui.finance.vote.bean;

/**
 * 项目：  sun
 * 类名：  NOWVOTE.java
 * 时间：  2020/4/16 14:06
 * 描述：
 */
public class NOWVOTE {
    /**
     * status : 1
     * data : {"id":11,"coinname":"RMCC","title":"RMCC","status":1,"img":"/Uploads/coin/20200102070230740ab17f5eb-3b74-4e20-9d2b-d0b35740da65.png","zhichi":50000,"fandui":0,"zongji":0,"bili":0,"votecoin":"","assumnum":1,"total":91000000,"addtime":0,"user":"罗小明","url":"http://www.btc.com","white_paper":"0","team_info":"0","instruct":"0","moble":"13022122051","userid":0,"code_url":"http://www.btc.com","total_amount":910000000,"exchanges":450000000,"plan":"1:1:1:50:5:5:10","coin_id":0,"num":320,"vote_num":50000,"vote_unit":"usdt"}
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
         * id : 11
         * coinname : RMCC
         * title : RMCC
         * status : 1
         * img : /Uploads/coin/20200102070230740ab17f5eb-3b74-4e20-9d2b-d0b35740da65.png
         * zhichi : 50000
         * fandui : 0
         * zongji : 0
         * bili : 0
         * votecoin :
         * assumnum : 1
         * total : 91000000
         * addtime : 0
         * user : 罗小明
         * url : http://www.btc.com
         * white_paper : 0
         * team_info : 0
         * instruct : 0
         * moble : 13022122051
         * userid : 0
         * code_url : http://www.btc.com
         * total_amount : 910000000
         * exchanges : 450000000
         * plan : 1:1:1:50:5:5:10
         * coin_id : 0
         * num : 320
         * vote_num : 50000
         * vote_unit : usdt
         */

        private int id;
        private String coinname;
        private String title;
        private int status;
        private String img;
        private int zhichi;
        private int fandui;
        private int zongji;
        private int bili;
        private String votecoin;
        private int assumnum;
        private int total;
        private int addtime;
        private String user;
        private String url;
        private String white_paper;
        private String team_info;
        private String instruct;
        private String moble;
        private int userid;
        private String code_url;
        private int total_amount;
        private int exchanges;
        private String plan;
        private int coin_id;
        private int num;
        private int vote_num;
        private String vote_unit;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getZhichi() {
            return zhichi;
        }

        public void setZhichi(int zhichi) {
            this.zhichi = zhichi;
        }

        public int getFandui() {
            return fandui;
        }

        public void setFandui(int fandui) {
            this.fandui = fandui;
        }

        public int getZongji() {
            return zongji;
        }

        public void setZongji(int zongji) {
            this.zongji = zongji;
        }

        public int getBili() {
            return bili;
        }

        public void setBili(int bili) {
            this.bili = bili;
        }

        public String getVotecoin() {
            return votecoin;
        }

        public void setVotecoin(String votecoin) {
            this.votecoin = votecoin;
        }

        public int getAssumnum() {
            return assumnum;
        }

        public void setAssumnum(int assumnum) {
            this.assumnum = assumnum;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWhite_paper() {
            return white_paper;
        }

        public void setWhite_paper(String white_paper) {
            this.white_paper = white_paper;
        }

        public String getTeam_info() {
            return team_info;
        }

        public void setTeam_info(String team_info) {
            this.team_info = team_info;
        }

        public String getInstruct() {
            return instruct;
        }

        public void setInstruct(String instruct) {
            this.instruct = instruct;
        }

        public String getMoble() {
            return moble;
        }

        public void setMoble(String moble) {
            this.moble = moble;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public int getExchanges() {
            return exchanges;
        }

        public void setExchanges(int exchanges) {
            this.exchanges = exchanges;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public int getCoin_id() {
            return coin_id;
        }

        public void setCoin_id(int coin_id) {
            this.coin_id = coin_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getVote_num() {
            return vote_num;
        }

        public void setVote_num(int vote_num) {
            this.vote_num = vote_num;
        }

        public String getVote_unit() {
            return vote_unit;
        }

        public void setVote_unit(String vote_unit) {
            this.vote_unit = vote_unit;
        }
    }
}
