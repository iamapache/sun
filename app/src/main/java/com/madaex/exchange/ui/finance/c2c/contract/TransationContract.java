package com.madaex.exchange.ui.finance.c2c.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.c2c.bean.OrderFee;
import com.madaex.exchange.ui.finance.c2c.bean.TransationInfo;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  TransationContract.java
 * 时间：  2018/9/25 15:31
 * 描述：  ${TODO}
 */

public class TransationContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);

        void requestError(String msg);
        void nodata(String msg);
        void sendViewSuccess(TransationInfo msg);
        void sendViewSuccess(OrderFee msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<TransationContract.View> {
        void getData(Map map);
        void getView(Map map);
    }
}
