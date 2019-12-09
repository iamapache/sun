package com.madaex.exchange.ui.finance.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.bean.SellerCoin;
import com.madaex.exchange.ui.finance.bean.TransaList;

/**
 * 项目：  madaexchange
 * 类名：  SellerCoinContract.java
 * 时间：  2018/8/30 15:54
 * 描述：  ${TODO}
 */

public class SellerCoinContract {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void requestSuccess(SellerCoin commonBean);

        void requestSuccess(TransaList commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<SellerCoinContract.View> {
        void getData(String str);

        void getCashCoin(String str);
    }
}
