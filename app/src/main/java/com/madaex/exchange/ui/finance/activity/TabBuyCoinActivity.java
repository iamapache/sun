package com.madaex.exchange.ui.finance.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.bean.Recharge;
import com.madaex.exchange.ui.finance.bean.auth_check;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  exchange
 * 类名：  BuyCoinActivity.java
 * 时间：  2018/8/22 18:09
 * 描述：  ${TODO}
 */

public class TabBuyCoinActivity extends BaseNetActivity<AssetPresenter> implements AssetContract.View {

    @BindView(R.id.stl)
    SlidingTabLayout mStl;
    @BindView(R.id.vp)
    NoScrollViewPager mVp;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tabbuycoin;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }


    @Override
    @SuppressLint("StringFormatInvalid")
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_CASH_RECHARGE);
        params.put("wallet_type",getIntent().getStringExtra("wallet_type"));
        params.put("xnb", getIntent().getStringExtra("xnb"));
        mPresenter.cash_recharge(DataUtil.sign2(params));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
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

    @Override
    public void requestRecharge(Recharge commonBean) {

        for (int j = 0; j < commonBean.getData().getAddress().size(); j++) {

            mTitleList.add(commonBean.getData().getAddress().get(j).getPro());
            if(commonBean.getData().getIs_arr()==1){
                mFragments.add(BuyCoinActivity.newInstance(false,commonBean.getData().getAddress().get(j).getPro(),commonBean.getData().getAddress().get(j).getAddress()));
            }else {
                mFragments.add(BuyCoinActivity.newInstance(true,getIntent().getStringExtra("xnb_name"),commonBean.getData().getAddress().get(j).getAddress()));
            }
        }
        if(commonBean.getData().getIs_arr()==1){
            mStl.setVisibility(View.VISIBLE);
        }else {
            mStl.setVisibility(View.GONE);
        }
        TitleStatePagerAdapter mAdapter = new TitleStatePagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
//        mVp.setAdapter(mAdapter);
//        mVp.setCurrentItem(1);
//        mStl.setViewPager(mVp);

        mVp.setAdapter(mAdapter);
        mStl.setViewPager(mVp);
    }

    @Override
    public void requestSuccess(auth_check commonBean) {

    }
}
