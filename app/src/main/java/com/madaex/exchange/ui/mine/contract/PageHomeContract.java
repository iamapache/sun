package com.madaex.exchange.ui.mine.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.ui.mine.bean.update;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  MineContract.java
 * 时间：  2018/8/29 15:06
 * 描述：  ${TODO}
 */

public class PageHomeContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(User user);
        void nodata(String msg);
        void requestError(String msg);
        void requestupdate(update bean);
    }

    public interface Presenter extends BaseContract.BasePresenter<PageHomeContract.View> {
        void getData(Map body);

        void update(Map body);

        void load(Map msg);
    }
}
