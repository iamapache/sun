package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.buy.bean.Trades;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  HistoryDetailContract.java
 * 时间：  2018/9/20 15:42
 * 描述：  ${TODO}
 */

public class HistoryDetailContract {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void sendtransationSuccess(Trades msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<HistoryDetailContract.View> {
        void getData(Map body);
    }
}
