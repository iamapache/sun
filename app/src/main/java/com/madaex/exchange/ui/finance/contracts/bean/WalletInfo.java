package com.madaex.exchange.ui.finance.contracts.bean;

/**
 * 项目：  sun
 * 类名：  WalletInfo.java
 * 时间：  2020/1/9 16:43
 * 描述：
 */
public class WalletInfo {
    /**
     * status : 1
     * data : {"gen_wallet_id":259,"con_wallet_id":61,"coin_name":"usdt","gen_assets":"0.00000000","con_assets":"0.00000000"}
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
         * gen_wallet_id : 259
         * con_wallet_id : 61
         * coin_name : usdt
         * gen_assets : 0.00000000
         * con_assets : 0.00000000
         */

        private int gen_wallet_id;
        private int con_wallet_id;
        private String coin_name;
        private String gen_assets;
        private String con_assets;

        public int getGen_wallet_id() {
            return gen_wallet_id;
        }

        public void setGen_wallet_id(int gen_wallet_id) {
            this.gen_wallet_id = gen_wallet_id;
        }

        public int getCon_wallet_id() {
            return con_wallet_id;
        }

        public void setCon_wallet_id(int con_wallet_id) {
            this.con_wallet_id = con_wallet_id;
        }

        public String getCoin_name() {
            return coin_name;
        }

        public void setCoin_name(String coin_name) {
            this.coin_name = coin_name;
        }

        public String getGen_assets() {
            return gen_assets;
        }

        public void setGen_assets(String gen_assets) {
            this.gen_assets = gen_assets;
        }

        public String getCon_assets() {
            return con_assets;
        }

        public void setCon_assets(String con_assets) {
            this.con_assets = con_assets;
        }
    }
}
