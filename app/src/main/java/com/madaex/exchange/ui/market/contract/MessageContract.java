package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.bank.contract.Bank;

import java.util.List;
import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  MessageContract.java
 * 时间：  2018/8/31 11:39
 * 描述：  ${TODO}
 */

public class MessageContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<Bank.DataBean> bean);

        void requestError(String msg);


    }

    public interface Presenter extends BaseContract.BasePresenter<MessageContract.View> {
        void getData(Map body);

    }
}
