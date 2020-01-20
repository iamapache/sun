package com.madaex.exchange.ui.finance.contracts.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  sun
 * 类名：  ContractAsset.java
 * 时间：  2020/1/9 15:06
 * 描述：
 */
public class ContractAsset implements Parcelable {
    /**
     * status : 1
     * data : {"assetsArr":{"usdt":111304,"rmb":779128},"xnb_list":[[{"assets":"91304.00","wallet_id":60,"xnb_name":"ALSC","protocol_identifier":"ERC20","show_protocol":true,"icon":"/Uploads/coin/5cdcc1944c2d2.png","market_id":58},{"assets":"20000.00","wallet_id":61,"xnb_name":"USDT","show_protocol":false,"protocol_identifier":"OMNI","icon":"/Uploads/coin/5cdcc1944c2d2.png","market_id":58}]]}
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

    public static class DataBean implements Parcelable {
        /**
         * assetsArr : {"usdt":111304,"rmb":779128}
         * xnb_list : [[{"assets":"91304.00","wallet_id":60,"xnb_name":"ALSC","protocol_identifier":"ERC20","show_protocol":true,"icon":"/Uploads/coin/5cdcc1944c2d2.png","market_id":58},{"assets":"20000.00","wallet_id":61,"xnb_name":"USDT","show_protocol":false,"protocol_identifier":"OMNI","icon":"/Uploads/coin/5cdcc1944c2d2.png","market_id":58}]]
         */

        private AssetsArrBean assetsArr;
        private List<List<XnbListBean>> xnb_list;

        public AssetsArrBean getAssetsArr() {
            return assetsArr;
        }

        public void setAssetsArr(AssetsArrBean assetsArr) {
            this.assetsArr = assetsArr;
        }

        public List<List<XnbListBean>> getXnb_list() {
            return xnb_list;
        }

        public void setXnb_list(List<List<XnbListBean>> xnb_list) {
            this.xnb_list = xnb_list;
        }

        public static class AssetsArrBean implements Parcelable {
            /**
             * usdt : 111304
             * rmb : 779128
             */

            private BigDecimal usdt;
            private BigDecimal rmb;

            public BigDecimal getUsdt() {
                return usdt;
            }

            public void setUsdt(BigDecimal usdt) {
                this.usdt = usdt;
            }

            public BigDecimal getRmb() {
                return rmb;
            }

            public void setRmb(BigDecimal rmb) {
                this.rmb = rmb;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeSerializable(this.usdt);
                dest.writeSerializable(this.rmb);
            }

            public AssetsArrBean() {
            }

            protected AssetsArrBean(Parcel in) {
                this.usdt = (BigDecimal) in.readSerializable();
                this.rmb = (BigDecimal) in.readSerializable();
            }

            public static final Creator<AssetsArrBean> CREATOR = new Creator<AssetsArrBean>() {
                @Override
                public AssetsArrBean createFromParcel(Parcel source) {
                    return new AssetsArrBean(source);
                }

                @Override
                public AssetsArrBean[] newArray(int size) {
                    return new AssetsArrBean[size];
                }
            };
        }

        public static class XnbListBean implements Parcelable {
            /**
             * assets : 91304.00
             * wallet_id : 60
             * xnb_name : ALSC
             * protocol_identifier : ERC20
             * show_protocol : true
             * icon : /Uploads/coin/5cdcc1944c2d2.png
             * market_id : 58
             */

            private String assets;
            private int wallet_id;
            private String xnb_name;
            private String protocol_identifier;
            private boolean show_protocol;
            private String icon;
            private int market_id;

            public String getAssets() {
                return assets;
            }

            public void setAssets(String assets) {
                this.assets = assets;
            }

            public int getWallet_id() {
                return wallet_id;
            }

            public void setWallet_id(int wallet_id) {
                this.wallet_id = wallet_id;
            }

            public String getXnb_name() {
                return xnb_name;
            }

            public void setXnb_name(String xnb_name) {
                this.xnb_name = xnb_name;
            }

            public String getProtocol_identifier() {
                return protocol_identifier;
            }

            public void setProtocol_identifier(String protocol_identifier) {
                this.protocol_identifier = protocol_identifier;
            }

            public boolean isShow_protocol() {
                return show_protocol;
            }

            public void setShow_protocol(boolean show_protocol) {
                this.show_protocol = show_protocol;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getMarket_id() {
                return market_id;
            }

            public void setMarket_id(int market_id) {
                this.market_id = market_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.assets);
                dest.writeInt(this.wallet_id);
                dest.writeString(this.xnb_name);
                dest.writeString(this.protocol_identifier);
                dest.writeByte(this.show_protocol ? (byte) 1 : (byte) 0);
                dest.writeString(this.icon);
                dest.writeInt(this.market_id);
            }

            public XnbListBean() {
            }

            protected XnbListBean(Parcel in) {
                this.assets = in.readString();
                this.wallet_id = in.readInt();
                this.xnb_name = in.readString();
                this.protocol_identifier = in.readString();
                this.show_protocol = in.readByte() != 0;
                this.icon = in.readString();
                this.market_id = in.readInt();
            }

            public static final Parcelable.Creator<XnbListBean> CREATOR = new Parcelable.Creator<XnbListBean>() {
                @Override
                public XnbListBean createFromParcel(Parcel source) {
                    return new XnbListBean(source);
                }

                @Override
                public XnbListBean[] newArray(int size) {
                    return new XnbListBean[size];
                }
            };
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.assetsArr, flags);
            dest.writeList(this.xnb_list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.assetsArr = in.readParcelable(AssetsArrBean.class.getClassLoader());
            this.xnb_list = new ArrayList<List<XnbListBean>>();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    public ContractAsset() {
    }

    protected ContractAsset(Parcel in) {
        this.status = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<ContractAsset> CREATOR = new Parcelable.Creator<ContractAsset>() {
        @Override
        public ContractAsset createFromParcel(Parcel source) {
            return new ContractAsset(source);
        }

        @Override
        public ContractAsset[] newArray(int size) {
            return new ContractAsset[size];
        }
    };
}
