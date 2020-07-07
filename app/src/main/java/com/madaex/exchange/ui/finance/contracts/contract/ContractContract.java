package com.madaex.exchange.ui.finance.contracts.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.OpenHole;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  AssetContract.java
 * 时间：  2018/8/30 15:29
 * 描述：  ${TODO}
 */

public class ContractContract {

    public interface View extends BaseContract.BaseView {
        void nodata(String msg);

        void requestError(String msg);

        void requestSuccess(String commonBean);

        void requestSuccess(ContractAsset commonBean);

        void requestSuccess(WalletInfo commonBean);

        void requestSuccess(USDTinfo commonBean);

        void requestSuccess(AlscInfo commonBean);

        void requestSuccess(Bills commonBean);
        void requestSuccess(OpenHole commonBean);
        void requestErrorcontract(String s);
    }

    public interface Presenter extends BaseContract.BasePresenter<ContractContract.View> {
        void getData(Map body);

        void bills(Map body);

        void getUSDTinfo(Map body);

        void getAlscInfo(Map body);

        void open_contract(Map body);

        void hua(Map body);

        void wallet_info(Map body);

        void gethuaRecord(Map body);


        void shifangseller(Map body);


        void isOpenHole(Map body);
    }
}
