package com.madaex.exchange.ui.mine.presenter;

import android.content.Context;

import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.mine.contract.PageHomeContract;

import java.util.Map;

import javax.inject.Inject;

/**
 * 项目：  sun
 * 类名：  HomePresenter.java
 * 时间：  2019/12/12 17:26
 * 描述：  ${TODO}
 */
public class PageHomePresenter extends RxPresenter<PageHomeContract.View> implements PageHomeContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public PageHomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {

    }

    @Override
    public void update(Map body) {

    }

    @Override
    public void load(Map body) {

    }
}
