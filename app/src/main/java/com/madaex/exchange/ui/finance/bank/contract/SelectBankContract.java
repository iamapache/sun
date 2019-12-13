package com.madaex.exchange.ui.finance.bank.contract;

import com.madaex.exchange.common.base.BaseContract;

import java.util.List;
import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  SelectBankContract.java
 * 时间：  2018/9/28 11:30
 * 描述：  ${TODO}
 */

public class SelectBankContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<SelectBank.DataBean> bean);

        void requestError(String msg);

        void nodata(String msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<SelectBankContract.View> {
        void getData(Map map);

    }
}
