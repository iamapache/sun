package com.madaex.exchange.ui.finance.vote.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.vote.bean.NOWVOTE;
import com.madaex.exchange.ui.finance.vote.bean.VoteCoin;
import com.madaex.exchange.ui.finance.vote.bean.issue;

import java.util.List;
import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  VoteCoinContract.java
 * 时间：  2018/9/10 16:46
 * 描述：  ${TODO}
 */

public class VoteCoinContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<VoteCoin.DataBean> commonBean);
        void nodata(String msg);
        void requestError(String msg);
        void requestSuccess(String commonBean);
        void sendViewSuccess(Asset msg);

        void sendViewSuccess(issue commonBean);

        void sendViewSuccess(NOWVOTE commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<VoteCoinContract.View> {
        void getData(Map body);

        void getData2(Map body);


        void getGRC(Map body);

        void go_issue(Map body);
        void NOWVOTE(Map body);
    }
}
