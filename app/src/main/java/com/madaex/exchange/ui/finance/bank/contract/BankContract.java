package com.madaex.exchange.ui.finance.bank.contract;

import com.madaex.exchange.common.base.BaseContract;

import java.util.List;
import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  BankContract.java
 * 时间：  2018/8/29 14:57
 * 描述：  ${TODO}
 */

public class BankContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<Bank.DataBean> bean);
        void nodata(String msg);
        void requestError(String msg);

        void deleteSuccess(String msg);

        void deleteError(String msg);

    }

    public interface Presenter extends BaseContract.BasePresenter<BankContract.View> {
        void getData(Map map);

        void delete(Map map);
    }
}
