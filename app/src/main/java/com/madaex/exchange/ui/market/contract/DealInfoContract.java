package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.market.bean.Home;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  DealInfoContract.java
 * 时间：  2018/9/6 16:19
 * 描述：  ${TODO}
 */

public class DealInfoContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);

        void requestError(String msg);

        void sendMsgSuccess(CoinList msg);
        void requestDetailSuccess(Home baseBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<DealInfoContract.View> {
        void getData(Map body);
        void getJavaLineDetail(Map map);
        void collection(Map body);
    }
}
