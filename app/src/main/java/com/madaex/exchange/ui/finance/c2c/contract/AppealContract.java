package com.madaex.exchange.ui.finance.c2c.contract;

import com.madaex.exchange.common.base.BaseContract;

import java.util.Map;

/**
 * 项目：  sun
 * 类名：  AppealContract.java
 * 时间：  2019/5/16 10:58
 * 描述：  ${TODO}
 */
public class AppealContract {
    public interface View extends BaseContract.BaseView {
        void nodata(String msg);
        void requestError(String msg);
        void deleteSuccess(String msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<AppealContract.View> {
        void getData(Map map);
    }
}
