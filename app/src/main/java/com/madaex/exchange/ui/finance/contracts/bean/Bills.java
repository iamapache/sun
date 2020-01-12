package com.madaex.exchange.ui.finance.contracts.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  Bills.java
 * 时间：  2020/1/9 17:04
 * 描述：
 */
public class Bills {
    /**
     * status : 1
     * data : {"finance_types":[{"id":5,"name":"币币转合约"},{"id":6,"name":"合约转币币"},{"id":7,"name":"交易买入"},{"id":8,"name":"交易买出"},{"id":11,"name":"交易释放"}],"coin_list":[{"id":3,"name":"eth"},{"id":4,"name":"bat"},{"id":5,"name":"omg"},{"id":6,"name":"snt"},{"id":7,"name":"icx"},{"id":8,"name":"zrx"},{"id":9,"name":"gnt"},{"id":10,"name":"usdt"},{"id":11,"name":"btc"},{"id":12,"name":"alsc"},{"id":13,"name":"qtum"}],"list":[],"total_pages":0}
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
         * finance_types : [{"id":5,"name":"币币转合约"},{"id":6,"name":"合约转币币"},{"id":7,"name":"交易买入"},{"id":8,"name":"交易买出"},{"id":11,"name":"交易释放"}]
         * coin_list : [{"id":3,"name":"eth"},{"id":4,"name":"bat"},{"id":5,"name":"omg"},{"id":6,"name":"snt"},{"id":7,"name":"icx"},{"id":8,"name":"zrx"},{"id":9,"name":"gnt"},{"id":10,"name":"usdt"},{"id":11,"name":"btc"},{"id":12,"name":"alsc"},{"id":13,"name":"qtum"}]
         * list : []
         * total_pages : 0
         */

        private int total_pages;
        private List<FinanceTypesBean> finance_types;
        private List<CoinListBean> coin_list;
        private List<ItemBean> list;

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public List<FinanceTypesBean> getFinance_types() {
            return finance_types;
        }

        public void setFinance_types(List<FinanceTypesBean> finance_types) {
            this.finance_types = finance_types;
        }

        public List<CoinListBean> getCoin_list() {
            return coin_list;
        }

        public void setCoin_list(List<CoinListBean> coin_list) {
            this.coin_list = coin_list;
        }

        public List<ItemBean> getList() {
            return list;
        }

        public void setList(List<ItemBean> list) {
            this.list = list;
        }
        public static class ItemBean {
            /**
             * id : 5
             * name : 币币转合约
             */

            private String coin_name;
            private String num;
            private String type_name;

            public String getCoin_name() {
                return coin_name;
            }

            public void setCoin_name(String coin_name) {
                this.coin_name = coin_name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }
        public static class FinanceTypesBean {
            /**
             * id : 5
             * name : 币币转合约
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CoinListBean {
            /**
             * id : 3
             * name : eth
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
