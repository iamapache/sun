package com.madaex.exchange.ui.finance.address.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.address.bean.WalletAddress;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  AddressContract.java
 * 时间：  2018/8/30 18:08
 * 描述：  ${TODO}
 */

public class AddressContract {
    public interface View extends BaseContract.BaseView {
        void nodata(String msg);
        void requestError(String msg);

        void requestSuccess(WalletAddress commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<AddressContract.View> {
        void getData(Map map);
    }
}
