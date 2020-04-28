package com.madaex.exchange.ui.finance.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.bean.Recharge;
import com.madaex.exchange.ui.finance.bean.auth_check;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  AssetContract.java
 * 时间：  2018/8/30 15:29
 * 描述：  ${TODO}
 */

public class AssetContract {

    public interface View extends BaseContract.BaseView {
        void nodata(String msg);
        void requestError(String msg);

        void requestSuccess(Asset commonBean);

        void requestRecharge(Recharge commonBean);

        void requestSuccess(auth_check commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<AssetContract.View> {
        void getData(Map body);

        void cash_recharge(Map body);


        void user_auth_check(Map body);
    }
}
