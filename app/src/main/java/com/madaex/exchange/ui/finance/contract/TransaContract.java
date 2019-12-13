package com.madaex.exchange.ui.finance.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.bean.TransaList;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  TransaContract.java
 * 时间：  2018/9/4 17:28
 * 描述：  ${TODO}
 */

public class TransaContract {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void requestSuccess(TransaList commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<TransaContract.View> {
        void getData(Map body);
    }
}
