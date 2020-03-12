package com.madaex.exchange.ui.mine.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.market.bean.HomeData;
import com.madaex.exchange.ui.mine.bean.BannerData;
import com.madaex.exchange.ui.mine.bean.HotCoin;
import com.madaex.exchange.ui.mine.bean.NoticeData;
import com.madaex.exchange.ui.mine.bean.Urlbean;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  MineContract.java
 * 时间：  2018/8/29 15:06
 * 描述：  ${TODO}
 */

public class PageHomeContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(BannerData user);
        void nodata(String msg);
        void requestError(String msg);
        void requestupdate(NoticeData bean);
        void requestHotcoin(HotCoin bean);

        void requestHotcoin(HomeData commonBean);

        void requestSuccess(String s);

        void requestService(Urlbean commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<PageHomeContract.View> {
        void getData(Map body);

        void update(Map body);

        void load(Map msg);
        void getMartketList(Map msg);

        void collection(Map body);

        void getService(Map body);

    }
}
