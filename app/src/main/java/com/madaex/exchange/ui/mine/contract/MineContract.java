package com.madaex.exchange.ui.mine.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.ui.mine.bean.update;

/**
 * 项目：  madaexchange
 * 类名：  MineContract.java
 * 时间：  2018/8/29 15:06
 * 描述：  ${TODO}
 */

public class MineContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(User user);
        void nodata(String msg);
        void requestError(String msg);
        void requestupdate(update bean);
    }

    public interface Presenter extends BaseContract.BasePresenter<MineContract.View> {
        void getData(String str);

        void update(String str);

        void load(String msg);
    }
}
