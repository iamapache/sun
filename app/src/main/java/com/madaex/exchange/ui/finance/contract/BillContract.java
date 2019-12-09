package com.madaex.exchange.ui.finance.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.bean.BillList;

/**
 * 项目：  madaexchange
 * 类名：  BillContract.java
 * 时间：  2018/9/4 17:58
 * 描述：  ${TODO}
 */

public class BillContract {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void requestSuccess(BillList commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<BillContract.View> {
        void getData(String str);
    }
}
