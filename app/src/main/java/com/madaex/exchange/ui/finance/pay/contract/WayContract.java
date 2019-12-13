package com.madaex.exchange.ui.finance.pay.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.pay.bean.Payway;

import java.util.ArrayList;
import java.util.Map;

/**
 * 项目：  sun
 * 类名：  WayContract.java
 * 时间：  2019/5/13 18:36
 * 描述：  ${TODO}
 */
public class WayContract {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void requestSuccess(String commonBean);

        void requestSuccess(Payway commonBean);

        void requestPayWaySuccess(String commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<WayContract.View> {
        void getData(Map body);
        void submit(Map body);
        void delete(Map body);
        void getPayWay(Map body);
        void edit(Map body);
        void saveUserHeadImage(Map body, ArrayList<String> pathList);
    }
}
