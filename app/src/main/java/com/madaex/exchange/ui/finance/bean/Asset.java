package com.madaex.exchange.ui.finance.bean;

import com.madaex.exchange.ui.common.CommonBaseBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  Asset.java
 * 时间：  2018/8/30 15:28
 * 描述：  ${TODO}
 */

public class Asset extends CommonBaseBean {

    /**
     * data : {"assets":{"usdt":"0.00","dollar":"--"},"xnb_list":[{"frozen":"0.000000","assets":"0.00","avail":"0.00","xnb":"usdt","coin_id":10,"xnb_name":"USDT","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"alsc","coin_id":12,"xnb_name":"ALSC","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"eth","coin_id":3,"xnb_name":"ETH","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":1,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"bat","coin_id":4,"xnb_name":"BAT","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"omg","coin_id":5,"xnb_name":"OMG","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"snt","coin_id":6,"xnb_name":"SNT","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"icx","coin_id":7,"xnb_name":"ICX","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"zrx","coin_id":8,"xnb_name":"ZRX","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"gnt","coin_id":9,"xnb_name":"GNT","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":1,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"btc","coin_id":11,"xnb_name":"BTC","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"qtum","coin_id":13,"xnb_name":"QTUM","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"alsc","coin_id":12,"xnb_name":"ALSC","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"}],"coin_hot":[{"value":"0.00","name":"ALSC"},{"value":"0.00","name":"ETH"},{"value":"0.00","name":"BAT"},{"value":"0.00","name":"其他"}],"exchange_coin":"usdt"}
     * message : 操作成功
     */

    private DataBean data;
    private String message;

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
         * assets : {"usdt":"0.00","dollar":"--"}
         * xnb_list : [{"frozen":"0.000000","assets":"0.00","avail":"0.00","xnb":"usdt","coin_id":10,"xnb_name":"USDT","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"alsc","coin_id":12,"xnb_name":"ALSC","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"eth","coin_id":3,"xnb_name":"ETH","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":1,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"bat","coin_id":4,"xnb_name":"BAT","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"omg","coin_id":5,"xnb_name":"OMG","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"snt","coin_id":6,"xnb_name":"SNT","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"icx","coin_id":7,"xnb_name":"ICX","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"zrx","coin_id":8,"xnb_name":"ZRX","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"gnt","coin_id":9,"xnb_name":"GNT","address":"0x7f4cadf0fed98361f451d15bdf7843e6bb3da69f","is_recharge":1,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"btc","coin_id":11,"xnb_name":"BTC","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"qtum","coin_id":13,"xnb_name":"QTUM","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"},{"frozen":"0.000000","assets":"0.00","avail":"0.000000","xnb":"alsc","coin_id":12,"xnb_name":"ALSC","address":"0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56","is_recharge":0,"is_support_cash":1,"type":"qbb"}]
         * coin_hot : [{"value":"0.00","name":"ALSC"},{"value":"0.00","name":"ETH"},{"value":"0.00","name":"BAT"},{"value":"0.00","name":"其他"}]
         * exchange_coin : usdt
         */

        private AssetsBean assets;
        private String exchange_coin;
        private List<XnbListBean> xnb_list;
        private List<CoinHotBean> coin_hot;
        private GenAssetsBean gen_assets;
        public AssetsBean getAssets() {
            return assets;
        }

        public void setAssets(AssetsBean assets) {
            this.assets = assets;
        }

        public String getExchange_coin() {
            return exchange_coin;
        }

        public void setExchange_coin(String exchange_coin) {
            this.exchange_coin = exchange_coin;
        }

        public List<XnbListBean> getXnb_list() {
            return xnb_list;
        }

        public void setXnb_list(List<XnbListBean> xnb_list) {
            this.xnb_list = xnb_list;
        }

        public List<CoinHotBean> getCoin_hot() {
            return coin_hot;
        }

        public void setCoin_hot(List<CoinHotBean> coin_hot) {
            this.coin_hot = coin_hot;
        }

        public GenAssetsBean getGen_assets() {
            return gen_assets;
        }

        public void setGen_assets(GenAssetsBean gen_assets) {
            this.gen_assets = gen_assets;
        }

        public static class AssetsBean {
            /**
             * usdt : 0.00
             * dollar : --
             */

            private String usdt;
            private String rmb;

            public String getUsdt() {
                return usdt;
            }

            public String getRmb() {
                return rmb;
            }

            public void setRmb(String rmb) {
                this.rmb = rmb;
            }

            public void setUsdt(String usdt) {
                this.usdt = usdt;
            }
        }
        public static class GenAssetsBean {
            /**
             * usdt : 0.00
             * dollar : --
             */

            private String usdt;
            private String rmb;

            public String getUsdt() {
                return usdt;
            }

            public String getRmb() {
                return rmb;
            }

            public void setRmb(String rmb) {
                this.rmb = rmb;
            }

            public void setUsdt(String usdt) {
                this.usdt = usdt;
            }
        }
        public static class XnbListBean {
            /**
             * frozen : 0.000000
             * assets : 0.00
             * avail : 0.00
             * xnb : usdt
             * coin_id : 10
             * xnb_name : USDT
             * address : 0xfa2B3E25e1A0Cf6E5cAef3Cb9E33657B3A7cbe56
             * is_recharge : 0
             * is_support_cash : 1
             * type : qbb
             */

            private String frozen;
            private String assets;
            private String avail;
            private String xnb;
            private int coin_id;
            private String xnb_name;
            private String address;
            private int is_recharge;
            private int is_support_cash;
            private String type;

            public String getFrozen() {
                return frozen;
            }

            public void setFrozen(String frozen) {
                this.frozen = frozen;
            }

            public String getAssets() {
                return assets;
            }

            public void setAssets(String assets) {
                this.assets = assets;
            }

            public String getAvail() {
                return avail;
            }

            public void setAvail(String avail) {
                this.avail = avail;
            }

            public String getXnb() {
                return xnb;
            }

            public void setXnb(String xnb) {
                this.xnb = xnb;
            }

            public int getCoin_id() {
                return coin_id;
            }

            public void setCoin_id(int coin_id) {
                this.coin_id = coin_id;
            }

            public String getXnb_name() {
                return xnb_name;
            }

            public void setXnb_name(String xnb_name) {
                this.xnb_name = xnb_name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getIs_recharge() {
                return is_recharge;
            }

            public void setIs_recharge(int is_recharge) {
                this.is_recharge = is_recharge;
            }

            public int getIs_support_cash() {
                return is_support_cash;
            }

            public void setIs_support_cash(int is_support_cash) {
                this.is_support_cash = is_support_cash;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class CoinHotBean {
            /**
             * value : 0.00
             * name : ALSC
             */

            private String value;
            private String name;
            private String precent;
            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public String getPrecent() {
                return precent;
            }

            public void setPrecent(String precent) {
                this.precent = precent;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
