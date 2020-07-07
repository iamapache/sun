package com.madaex.exchange.ui.finance.contracts.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  OpenHoleDestail.java
 * 时间：  2020/7/7 11:29
 * 描述：
 */
public class OpenHoleDestail {

    /**
     * status : 1
     * data : {"difference":"-58020.81","new_black_num":"27999.95","black_total":"58020.81","zr_total":"0.00","zc_total":"0.00","arr_black":[{"coin_name":"USDT","num":"-27999.95","type_name":"转入黑洞"},{"coin_name":"USDT","num":"-4207.38","type_name":"转入黑洞"},{"coin_name":"USDT","num":"-25793.48","type_name":"转入黑洞"},{"coin_name":"USDT","num":"-20.00","type_name":"转入黑洞"}]}
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
         * difference : -58020.81
         * new_black_num : 27999.95
         * black_total : 58020.81
         * zr_total : 0.00
         * zc_total : 0.00
         * arr_black : [{"coin_name":"USDT","num":"-27999.95","type_name":"转入黑洞"},{"coin_name":"USDT","num":"-4207.38","type_name":"转入黑洞"},{"coin_name":"USDT","num":"-25793.48","type_name":"转入黑洞"},{"coin_name":"USDT","num":"-20.00","type_name":"转入黑洞"}]
         */

        private String difference;
        private String new_black_num;
        private String black_total;
        private String zr_total;
        private String zc_total;
        private List<ArrBlackBean> arr_black;

        public String getDifference() {
            return difference;
        }

        public void setDifference(String difference) {
            this.difference = difference;
        }

        public String getNew_black_num() {
            return new_black_num;
        }

        public void setNew_black_num(String new_black_num) {
            this.new_black_num = new_black_num;
        }

        public String getBlack_total() {
            return black_total;
        }

        public void setBlack_total(String black_total) {
            this.black_total = black_total;
        }

        public String getZr_total() {
            return zr_total;
        }

        public void setZr_total(String zr_total) {
            this.zr_total = zr_total;
        }

        public String getZc_total() {
            return zc_total;
        }

        public void setZc_total(String zc_total) {
            this.zc_total = zc_total;
        }

        public List<ArrBlackBean> getArr_black() {
            return arr_black;
        }

        public void setArr_black(List<ArrBlackBean> arr_black) {
            this.arr_black = arr_black;
        }

        public static class ArrBlackBean {
            /**
             * coin_name : USDT
             * num : -27999.95
             * type_name : 转入黑洞
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
    }
}
