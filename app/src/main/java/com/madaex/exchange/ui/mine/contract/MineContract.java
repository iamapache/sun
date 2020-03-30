package com.madaex.exchange.ui.mine.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.mine.bean.Urlbean;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.ui.mine.bean.update;

import java.util.Map;

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
        void requestMessageCountSuccess(String msg);
        void requestupdate(update bean);void requestService(Urlbean commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<MineContract.View> {
        void getData(Map body);
        void getMessageCount(Map body);
        void update(Map body);
        void getService(Map body);
        void load(String msg);

    }
}
