package com.madaex.exchange.ui.finance.c2c.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;
import com.madaex.exchange.ui.finance.c2c.bean.MyEntrust;

import java.util.ArrayList;

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
        void getData(String str);
        void cancel(String str);

        void getData2(String str);

        void order_pay(String str);
        void order_confirm(String str);

        void saveUserHeadImage(String str, ArrayList<String> pathList);
    }
}
