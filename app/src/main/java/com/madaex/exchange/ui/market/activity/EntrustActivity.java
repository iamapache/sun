package com.madaex.exchange.ui.market.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.ui.adapter.TitlePagerAdapter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.fragment.EntrustCurrentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  EntrustActivity.java
 * 时间：  2018/9/4 16:09
 * 描述：  ${TODO}
 */

public class EntrustActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();
    private String one_xnb = "ETH";
    private String two_xnb = "GRC";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_entrust;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {


       String one_xnb = getIntent().getStringExtra(Constants.ONE_XNB);
        String two_xnb = getIntent().getStringExtra(Constants.TWO_XNB);
        mToolbarTitleTv.setText(one_xnb+"/"+two_xnb);
        EntrustCurrentFragment fragment3 = EntrustCurrentFragment.newInstance(ConstantUrl.ENTRUSTCURRENT,one_xnb,two_xnb);
        EntrustCurrentFragment fragment4 = EntrustCurrentFragment.newInstance(ConstantUrl.ENTRUSTHISTORY,one_xnb,two_xnb);
        mViewList.add(fragment3);
        mViewList.add(fragment4);
        mTitleList.add(getString(R.string.Orders));
        mTitleList.add(getString(R.string.History));
        TitlePagerAdapter adapter = new TitlePagerAdapter(getSupportFragmentManager(), mViewList, mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }
}
