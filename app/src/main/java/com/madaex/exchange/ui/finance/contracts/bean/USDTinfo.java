package com.madaex.exchange.ui.finance.contracts.bean;

/**
 * 项目：  sun
 * 类名：  Coininfo.java
 * 时间：  2020/1/9 16:51
 * 描述：
 */
public class USDTinfo {
    /**
     * status : 1
     * data : {"assets":{"usdt":20000,"rmb":140000},"con_usable_assets":"10000.00000000","con_frozen_assets":"10000.00000000","lock_assets":0,"market_name":"alsc_usdt"}
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
         * assets : {"usdt":20000,"rmb":140000}
         * con_usable_assets : 10000.00000000
         * con_frozen_assets : 10000.00000000
         * lock_assets : 0
         * market_name : alsc_usdt
         */

        private AssetsBean assets;
        private String con_usable_assets;
        private String con_frozen_assets;
        private int lock_assets;
        private String market_name;

        public AssetsBean getAssets() {
            return assets;
        }

        public void setAssets(AssetsBean assets) {
            this.assets = assets;
        }

        public String getCon_usable_assets() {
            return con_usable_assets;
        }

        public void setCon_usable_assets(String con_usable_assets) {
            this.con_usable_assets = con_usable_assets;
        }

        public String getCon_frozen_assets() {
            return con_frozen_assets;
        }

        public void setCon_frozen_assets(String con_frozen_assets) {
            this.con_frozen_assets = con_frozen_assets;
        }

        public int getLock_assets() {
            return lock_assets;
        }

        public void setLock_assets(int lock_assets) {
            this.lock_assets = lock_assets;
        }

        public String getMarket_name() {
            return market_name;
        }

        public void setMarket_name(String market_name) {
            this.market_name = market_name;
        }

        public static class AssetsBean {
            /**
             * usdt : 20000
             * rmb : 140000
             */

            private String usdt;
            private String rmb;

            public String getUsdt() {
                return usdt;
            }

            public void setUsdt(String usdt) {
                this.usdt = usdt;
            }

            public String getRmb() {
                return rmb;
            }

            public void setRmb(String rmb) {
                this.rmb = rmb;
            }
        }
    }
}
