package com.madaex.exchange.ui.finance.c2c.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;
import com.madaex.exchange.ui.finance.c2c.bean.MyEntrust;

import java.util.ArrayList;
import java.util.Map;

/**
 * 项目：  sun
 * 类名：  EntrustContract.java
 * 时间：  2019/5/14 11:39
 * 描述：  ${TODO}
 */
public class MyEntrustContract {

    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);
        void requestSuccess(MyEntrust msg);

        void requestSuccess(EntrusTwo msg);

        void requestError(String msg);
        void nodata(String msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<MyEntrustContract.View> {
        void getData(Map map);
        void cancel(Map map);

        void getData2(Map map);

        void order_pay(Map map);
        void order_confirm(Map map);

        void saveUserHeadImage(Map body, ArrayList<String> pathList);
    }
}
