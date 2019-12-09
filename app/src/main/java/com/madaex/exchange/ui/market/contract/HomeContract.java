package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.bean.TitleBean;

import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  HomeContract.java
 * 时间：  2018/9/13 16:00
 * 描述：  ${TODO}
 */

public class HomeContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<Home> msg);

        void requestError(String msg);

        void requestSuccess(String msg);

        void SuccessTitle(TitleBean msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<HomeContract.View> {
        void getTitleData(String str);
        void getData(String str);
        void cancel();
        void collection(String str);
    }
}
