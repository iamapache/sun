package com.madaex.exchange.ui.finance.contracts.activity;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;

/**
 * 项目：  sun
 * 类名：  ContractActivity.java
 * 时间：  2020/1/7 9:25
 * 描述：
 */
public class ContractActivity extends  BaseNetActivity<AssetPresenter> implements AssetContract.View  {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(Asset commonBean) {

    }
}
