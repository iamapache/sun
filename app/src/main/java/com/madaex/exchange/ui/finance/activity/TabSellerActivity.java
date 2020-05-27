package com.madaex.exchange.ui.finance.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.bean.SellerCoin;
import com.madaex.exchange.ui.finance.bean.TransaList;
import com.madaex.exchange.ui.finance.contract.SellerCoinContract;
import com.madaex.exchange.ui.finance.presenter.SellerCoinPresenter;

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

public class TabSellerActivity extends BaseNetActivity<SellerCoinPresenter> implements SellerCoinContract.View {

    @BindView(R.id.stl)
    SlidingTabLayout mStl;
    @BindView(R.id.vp)
    NoScrollViewPager mVp;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tabseller;
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
        String xnb_name = getIntent().getStringExtra("xnb_name");
        mTitleView.setText(getString(R.string.fu) + xnb_name);
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_CASH_COIN);
        params.put("wallet_type",getIntent().getStringExtra("wallet_type"));
        params.put("coin_id", getIntent().getStringExtra("coin_id"));
        mPresenter.cash_recharge(DataUtil.sign2(params));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_tv_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_tv_ll:
                Intent intent = getIntent();
                intent.setClass(mContext, TransaListActivity.class);
                intent.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_SELLER);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void requestError(String msg) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void requestSuccess(SellerCoin commonBean) {
        for (int j = 0; j < commonBean.getData().getPro_arr().size(); j++) {

            mTitleList.add(commonBean.getData().getPro_arr().get(j));
            if(commonBean.getData().getIs_arr()==1){
                mFragments.add(SellerCoinActivity.newInstance(false,commonBean.getData().getPro_arr().get(j),commonBean));
            }else {
                mFragments.add(SellerCoinActivity.newInstance(true,commonBean.getData().getPro_arr().get(j),commonBean));
            }
        }
        if(commonBean.getData().getIs_arr()==1){
            mStl.setVisibility(View.VISIBLE);
        }else {
            mStl.setVisibility(View.GONE);
        }
        TitleStatePagerAdapter mAdapter = new TitleStatePagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);

        mVp.setAdapter(mAdapter);
        mStl.setViewPager(mVp);
    }

    @Override
    public void requestSuccess(TransaList commonBean) {

    }

}
