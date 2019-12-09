package com.madaex.exchange.ui.finance.pay.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.pay.bean.Payway;

import java.util.ArrayList;

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
        void getData(String str);
        void submit(String str);
        void delete(String str);
        void getPayWay(String str);
        void edit(String str);
        void saveUserHeadImage(String str, ArrayList<String> pathList);
    }
}
