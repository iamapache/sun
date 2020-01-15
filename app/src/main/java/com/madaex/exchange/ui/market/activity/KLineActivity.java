package com.madaex.exchange.ui.market.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.market.fragment.KLineFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  KLineActivity.java
 * 时间：  2018/9/12 9:01
 * 描述：  ${TODO}
 */

public class KLineActivity extends BaseNetActivity {

    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    private int position = 5;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_kline;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        mTitleList.add(getString(R.string.onef));
//        mTitleList.add(getString(R.string.onethreef));
        mTitleList.add(getString(R.string.ONEfiveff));
        mTitleList.add(getString(R.string.onefivef));
        mTitleList.add(getString(R.string.threef));
        mTitleList.add(getString(R.string.onehours));
//        mTitleList.add(getString(R.string.twohours));
        mTitleList.add(getString(R.string.fourhours));
//        mTitleList.add(getString(R.string.fivehours));
//        mTitleList.add(getString(R.string.twothhour));
        mTitleList.add(getString(R.string.onedays));
//        mTitleList.add(getString(R.string.threedays));
        mTitleList.add(getString(R.string.oneweeks));
        position = getIntent().getIntExtra("position", 3);
        Logger.i("<==>encode:" + position);
        for (int j = 0; j < mTitleList.size(); j++) {
            if (j == 0) {
                KLineFragment fragment1 = KLineFragment.newInstance("1min", true);
                mViewList.add(fragment1);
            } else if (j == 1) {
                KLineFragment fragment1 = KLineFragment.newInstance("5min", true);
                mViewList.add(fragment1);
            }else if (j == 2) {
                KLineFragment fragment1 = KLineFragment.newInstance("15min", true);
                mViewList.add(fragment1);
            }else if (j == 3) {
                KLineFragment fragment1 = KLineFragment.newInstance("30min", true);
                mViewList.add(fragment1);
            } else if (j == 4) {
                KLineFragment fragment1 = KLineFragment.newInstance("1hour", true);
                mViewList.add(fragment1);
            } else if (j == 5) {
                KLineFragment fragment1 = KLineFragment.newInstance("4hour", true);
                mViewList.add(fragment1);
            } else if (j == 6) {
                KLineFragment fragment1 = KLineFragment.newInstance("1day", true);
                mViewList.add(fragment1);
            }else if (j == 7) {
                KLineFragment fragment1 = KLineFragment.newInstance("1week", true);
                mViewList.add(fragment1);
            }
        }
        TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getSupportFragmentManager(), mViewList
                , mTitleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    protected void initDatas() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.nofullscreen)
    public void onViewClicked() {
        finish();
    }
}
