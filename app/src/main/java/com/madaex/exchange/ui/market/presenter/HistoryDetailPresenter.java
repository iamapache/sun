package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.market.contract.HistoryDetailContract;

import java.util.Map;

import javax.inject.Inject;

/**
 * 项目：  madaexchange
 * 类名：  HistoryDetailPresenter.java
 * 时间：  2018/9/20 15:42
 * 描述：  ${TODO}
 */

public class HistoryDetailPresenter extends RxPresenter<HistoryDetailContract.View> implements HistoryDetailContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public HistoryDetailPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
    }
}
