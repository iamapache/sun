package com.madaex.exchange.ui.finance.c2c.contract;


import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.c2c.bean.PlatformEntrust;
import com.madaex.exchange.ui.mine.bean.User;

import java.util.Map;

/**
 * 项目：  sun
 * 类名：  PlatformEntrustContract.java
 * 时间：  2019/5/9 14:57
 * 描述：  ${TODO}
 */
public class PlatformEntrustContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);

        void requestError(String msg);

        void sendMsgSuccess(PlatformEntrust.DataBean msg);

        void requestSuccess(User user);
    }

    public interface Presenter extends BaseContract.BasePresenter<PlatformEntrustContract.View> {
        void getData(Map map);

        void load(Map body);
    }
}
