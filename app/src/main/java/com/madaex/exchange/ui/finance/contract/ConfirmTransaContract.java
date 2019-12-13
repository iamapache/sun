package com.madaex.exchange.ui.finance.contract;

import com.madaex.exchange.common.base.BaseContract;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  ConfirmTransaContract.java
 * 时间：  2018/9/30 15:07
 * 描述：  ${TODO}
 */

public class ConfirmTransaContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);

        void requestError(String msg);
        void requestMsgError(String msg);
        void sendMsgSuccess(String msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<ConfirmTransaContract.View> {
        void getData(Map body);
        void getMsgCode(Map body);
    }
}
