package com.madaex.exchange.ui.finance.bean;

import java.util.List;

/**
 * 项目：  sun
 * 类名：  Recharge.java
 * 时间：  2020/4/8 12:54
 * 描述：
 */
public class Recharge {
    /**
     * status : 1
     * data : {"is_arr":1,"address":[{"pro":"ERC20","address":"0xa60d4efad12cd4187d2323a12cb129539d98ffa4"},{"pro":"OMNI","address":"15EAFm6pe8BNAHiYQcsLyz28vVjhn3DwTd"}]}
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
         * is_arr : 1
         * address : [{"pro":"ERC20","address":"0xa60d4efad12cd4187d2323a12cb129539d98ffa4"},{"pro":"OMNI","address":"15EAFm6pe8BNAHiYQcsLyz28vVjhn3DwTd"}]
         */
        private String company_addr;
        private int is_arr;
        private List<AddressBean> address;

        public String getCompany_addr() {
            return company_addr;
        }

        public void setCompany_addr(String company_addr) {
            this.company_addr = company_addr;
        }

        public int getIs_arr() {
            return is_arr;
        }

        public void setIs_arr(int is_arr) {
            this.is_arr = is_arr;
        }

        public List<AddressBean> getAddress() {
            return address;
        }

        public void setAddress(List<AddressBean> address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * pro : ERC20
             * address : 0xa60d4efad12cd4187d2323a12cb129539d98ffa4
             */

            private String pro;
            private String address;

            public String getPro() {
                return pro;
            }

            public void setPro(String pro) {
                this.pro = pro;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
