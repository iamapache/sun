package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.market.bean.CoinDetail;

/**
 * 项目：  madaexchange
 * 类名：  CoinDetailContract.java
 * 时间：  2018/10/22 10:56
 * 描述：  ${TODO}
 */

public class CoinDetailContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(CoinDetail bean);

        void requestError(String msg);


    }

    public interface Presenter extends BaseContract.BasePresenter<CoinDetailContract.View> {
        void getData(String str);

    }
}
