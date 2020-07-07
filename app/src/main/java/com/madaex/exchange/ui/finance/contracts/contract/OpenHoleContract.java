package com.madaex.exchange.ui.finance.contracts.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.contracts.bean.OpenHoleDestail;

import java.util.Map;

/**
 * 项目：  sun
 * 类名：  OpenHoleContract.java
 * 时间：  2020/7/7 11:28
 * 描述：
 */
public class OpenHoleContract {
    public interface View extends BaseContract.BaseView {
        void nodata(String msg);

        void requestError(String msg);

        void requestSuccess(String commonBean);

        void requestSuccess(OpenHoleDestail commonBean);

    }

    public interface Presenter extends BaseContract.BasePresenter<OpenHoleContract.View> {
        void getData(Map body);

        void transfer(Map body);

    }
}
