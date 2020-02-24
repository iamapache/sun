package com.madaex.exchange.ui.buy.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.market.bean.Home;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  DealContract.java
 * 时间：  2018/9/5 9:46
 * 描述：  ${TODO}
 */

public class DealContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);

        void requestError(String msg);

        void sendMsgSuccess(DealInfo msg);
        void requestDetailSuccess(Home baseBean);

        void requestToken(String baseBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<DealContract.View> {
        void getData(Map str);
        void getMsgInfo(Map str);
        void getJavaLineDetail(Map map);
        void getToken(Map map);
    }
}
