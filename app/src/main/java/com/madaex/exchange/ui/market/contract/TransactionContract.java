package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.market.bean.TransactionList;

import java.util.List;
import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  TransactionContract.java
 * 时间：  2018/8/31 14:55
 * 描述：  ${TODO}
 */

public class TransactionContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<TransactionList.DataBean> commonBean);

        void requestError(String msg);



    }

    public interface Presenter extends BaseContract.BasePresenter<TransactionContract.View> {
        void getData(Map body);
    }
}
