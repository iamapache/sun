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
     * data : {"assets":{"cny":"243942.31","dollar":"35873.87"},"xnb_list":[{"frozen":"0.00","assets":"9988.70","avail":"9988.70","xnb":"GRC","xnb_name":"GRC","address":"8867999552a4a2ba923bb485ceabf983","type":"rmb"},{"frozen":"0.0000","assets":"222800.00","avail":"5.0000","xnb":"ETH","xnb_name":"ETH","address":"ETH1","type":"qbb"},{"frozen":"0.0000","assets":"8321.77","avail":"5.7000","xnb":"eth","xnb_name":"ETH","address":"eth1","type":"qbb"},{"frozen":"0.0000","assets":"2831.84","avail":"8.0000","xnb":"ltc","xnb_name":"LTC","address":"ltc1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"eos","xnb_name":"EOS","address":"eos1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"qtum","xnb_name":"QTUM","address":"qtum1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"xrp","xnb_name":"XRP","address":"9faeabd8ab4f25da1c4f08dd1839ab5b","type":"rgb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"dash","xnb_name":"DASH","address":"dash1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"bat","xnb_name":"BAT","address":"bat1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"doge","xnb_name":"DOGE","address":"doge1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"neo","xnb_name":"NEO","address":"neo1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"omg","xnb_name":"OMG","address":"omg1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"snt","xnb_name":"SNT","address":"snt1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"ae","xnb_name":"AE","address":"ae1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"icx","xnb_name":"ICX","address":"icx1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"zrx","xnb_name":"ZRX","address":"zrx1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"xlm","xnb_name":"XLM","address":"xlm1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"etc","xnb_name":"ETC","address":"etc1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"gnt","xnb_name":"GNT","address":"gnt1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"xem","xnb_name":"XEM","address":"xem1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"ada","xnb_name":"ADA","address":"ada1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"syngy","xnb_name":"SYNGY","address":"","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"rmcc","xnb_name":"RMCC","address":"","type":"qbb"}],"coin_hot":[{"value":"222800.00","name":"ETH"},{"value":"8321.77","name":"ETH"},{"value":"9988.70","name":"GRC"},{"value":"2831.84","name":"其他"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * assets : {"cny":"243942.31","dollar":"35873.87"}
         * xnb_list : [{"frozen":"0.00","assets":"9988.70","avail":"9988.70","xnb":"GRC","xnb_name":"GRC","address":"8867999552a4a2ba923bb485ceabf983","type":"rmb"},{"frozen":"0.0000","assets":"222800.00","avail":"5.0000","xnb":"ETH","xnb_name":"ETH","address":"ETH1","type":"qbb"},{"frozen":"0.0000","assets":"8321.77","avail":"5.7000","xnb":"eth","xnb_name":"ETH","address":"eth1","type":"qbb"},{"frozen":"0.0000","assets":"2831.84","avail":"8.0000","xnb":"ltc","xnb_name":"LTC","address":"ltc1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"eos","xnb_name":"EOS","address":"eos1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"qtum","xnb_name":"QTUM","address":"qtum1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"xrp","xnb_name":"XRP","address":"9faeabd8ab4f25da1c4f08dd1839ab5b","type":"rgb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"dash","xnb_name":"DASH","address":"dash1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"bat","xnb_name":"BAT","address":"bat1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"doge","xnb_name":"DOGE","address":"doge1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"neo","xnb_name":"NEO","address":"neo1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"omg","xnb_name":"OMG","address":"omg1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"snt","xnb_name":"SNT","address":"snt1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"ae","xnb_name":"AE","address":"ae1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"icx","xnb_name":"ICX","address":"icx1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"zrx","xnb_name":"ZRX","address":"zrx1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"xlm","xnb_name":"XLM","address":"xlm1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"etc","xnb_name":"ETC","address":"etc1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"gnt","xnb_name":"GNT","address":"gnt1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"xem","xnb_name":"XEM","address":"xem1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"ada","xnb_name":"ADA","address":"ada1","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"syngy","xnb_name":"SYNGY","address":"","type":"qbb"},{"frozen":"0.0000","assets":"0.00","avail":"0.0000","xnb":"rmcc","xnb_name":"RMCC","address":"","type":"qbb"}]
         * coin_hot : [{"value":"222800.00","name":"ETH"},{"value":"8321.77","name":"ETH"},{"value":"9988.70","name":"GRC"},{"value":"2831.84","name":"其他"}]
         */

        private AssetsBean assets;
        private List<XnbListBean> xnb_list;
        private List<CoinHotBean> coin_hot;

        public AssetsBean getAssets() {
            return assets;
        }

        public void setAssets(AssetsBean assets) {
            this.assets = assets;
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

        public static class AssetsBean {
            /**
             * cny : 243942.31
             * dollar : 35873.87
             */

            private String cny;
            private String dollar;

            public String getCny() {
                return cny;
            }

            public void setCny(String cny) {
                this.cny = cny;
            }

            public String getDollar() {
                return dollar;
            }

            public void setDollar(String dollar) {
                this.dollar = dollar;
            }
        }

        public static class XnbListBean {
            /**
             * frozen : 0.00
             * assets : 9988.70
             * avail : 9988.70
             * xnb : GRC
             * xnb_name : GRC
             * address : 8867999552a4a2ba923bb485ceabf983
             * type : rmb
             */
            private int is_recharge;
            private String frozen;
            private String assets;
            private String avail;
            private String xnb;
            private String xnb_name;
            private String address;
            private String type;
            private String is_support_cash;
            public int getIs_recharge() {
                return is_recharge;
            }

            public String getIs_support_cash() {
                return is_support_cash;
            }

            public void setIs_support_cash(String is_support_cash) {
                this.is_support_cash = is_support_cash;
            }

            public void setIs_recharge(int is_recharge) {
                this.is_recharge = is_recharge;
            }

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class CoinHotBean {
            /**
             * value : 222800.00
             * name : ETH
             */

            private String value;
            private String name;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
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
