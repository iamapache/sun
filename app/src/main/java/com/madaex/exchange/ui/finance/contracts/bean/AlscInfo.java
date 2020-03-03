package com.madaex.exchange.ui.finance.contracts.bean;

import java.math.BigDecimal;

/**
 * 项目：  sun
 * 类名：  AlscInfo.java
 * 时间：  2020/1/9 17:02
 * 描述：
 */
public class AlscInfo {
    /**
     * status : 1
     * data : {"assets":{"alsc":93280,"rmb":652960},"con_usable_assets":"10000.00000000","con_frozen_assets":"10000.00000000","lock_assets":0,"today":{"alsc":100,"usdt":466.4},"tomorrow":{"alsc":100,"usdt":466.4},"market_name":"alsc_usdt"}
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
         * assets : {"alsc":93280,"rmb":652960}
         * con_usable_assets : 10000.00000000
         * con_frozen_assets : 10000.00000000
         * lock_assets : 0
         * today : {"alsc":100,"usdt":466.4}
         * tomorrow : {"alsc":100,"usdt":466.4}
         * market_name : alsc_usdt
         */

        private AssetsBean assets;
        private String con_usable_assets;
        private String con_frozen_assets;
        private String lock_assets;
        private TodayBean today;
        private TomorrowBean tomorrow;
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

        public String getLock_assets() {
            return lock_assets;
        }

        public void setLock_assets(String lock_assets) {
            this.lock_assets = lock_assets;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public TomorrowBean getTomorrow() {
            return tomorrow;
        }

        public void setTomorrow(TomorrowBean tomorrow) {
            this.tomorrow = tomorrow;
        }

        public String getMarket_name() {
            return market_name;
        }

        public void setMarket_name(String market_name) {
            this.market_name = market_name;
        }

        public static class AssetsBean {
            /**
             * alsc : 93280
             * rmb : 652960
             */

            private BigDecimal alsc;
            private BigDecimal rmb;
            private BigDecimal usdt;
            public BigDecimal getAlsc() {
                return alsc;
            }

            public BigDecimal getUsdt() {
                return usdt;
            }

            public void setUsdt(BigDecimal usdt) {
                this.usdt = usdt;
            }

            public void setAlsc(BigDecimal alsc) {
                this.alsc = alsc;
            }

            public BigDecimal getRmb() {
                return rmb;
            }

            public void setRmb(BigDecimal rmb) {
                this.rmb = rmb;
            }
        }

        public static class TodayBean {
            /**
             * alsc : 100
             * usdt : 466.4
             */

            private String alsc;
            private double usdt;

            public String getAlsc() {
                return alsc;
            }

            public void setAlsc(String alsc) {
                this.alsc = alsc;
            }

            public double getUsdt() {
                return usdt;
            }

            public void setUsdt(double usdt) {
                this.usdt = usdt;
            }
        }

        public static class TomorrowBean {
            /**
             * alsc : 100
             * usdt : 466.4
             */

            private String alsc;
            private double usdt;

            public String getAlsc() {
                return alsc;
            }

            public void setAlsc(String alsc) {
                this.alsc = alsc;
            }

            public double getUsdt() {
                return usdt;
            }

            public void setUsdt(double usdt) {
                this.usdt = usdt;
            }
        }
    }
}
