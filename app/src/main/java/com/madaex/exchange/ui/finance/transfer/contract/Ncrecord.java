package com.madaex.exchange.ui.finance.transfer.contract;

import java.util.List;

/**
 * 项目：  sunn
 * 类名：  Ncrecord.java
 * 时间：  2019/5/23 15:36
 * 描述：  ${TODO}
 */
public class Ncrecord {

    /**
     * status : 1
     * data : {"list":[{"addtime":"2019-05-24 14:32:45","coinname":"转出 GRC","num":"数量 GRC100.00","user_info":"转入账户 315"},{"addtime":"2019-05-23 17:53:45","coinname":"转出 GRC","num":"数量 GRC100.00","user_info":"转入账户 315"}],"totalpage":1}
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
         * list : [{"addtime":"2019-05-24 14:32:45","coinname":"转出 GRC","num":"数量 GRC100.00","user_info":"转入账户 315"},{"addtime":"2019-05-23 17:53:45","coinname":"转出 GRC","num":"数量 GRC100.00","user_info":"转入账户 315"}]
         * totalpage : 1
         */

        private int totalpage;
        private List<ListBean> list;

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * addtime : 2019-05-24 14:32:45
             * coinname : 转出 GRC
             * num : 数量 GRC100.00
             * user_info : 转入账户 315
             */

            private String addtime;
            private String coinname;
            private String num;
            private String user_info;

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getCoinname() {
                return coinname;
            }

            public void setCoinname(String coinname) {
                this.coinname = coinname;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getUser_info() {
                return user_info;
            }

            public void setUser_info(String user_info) {
                this.user_info = user_info;
            }
        }
    }
}
