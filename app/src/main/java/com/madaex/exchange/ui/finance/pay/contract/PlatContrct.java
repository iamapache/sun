package com.madaex.exchange.ui.finance.pay.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;

import java.util.Map;

/**
 * 项目：  sun
 * 类名：  PlatContrct.java
 * 时间：  2019/5/15 10:44
 * 描述：  ${TODO}
 */
public class PlatContrct {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void requestSuccess(String commonBean);

        void requestSuccess(PlatRecord commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<PlatContrct.View> {
        void getData(Map body);
        void submit(Map body);

    }
}
