package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  KLineContract.java
 * 时间：  2018/9/14 14:04
 * 描述：  ${TODO}
 */

public class KLineContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);

        void requestError(String msg);


    }

    public interface Presenter extends BaseContract.BasePresenter<KLineContract.View> {
        void getData(Map map);
        void cancel();
    }
}
