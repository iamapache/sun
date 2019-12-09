package com.madaex.exchange.ui.finance.c2c.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseFragment;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目：  sun
 * 类名：  PlatformEntrustFragment.java
 * 时间：  2019/5/9 14:34
 * 描述：  ${TODO}
 */
public class MyEntrustFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myentrust;
    }

    public static MyEntrustFragment newInstance(String string) {
        MyEntrustFragment fragment = null;
        if (fragment == null) {
            fragment = new MyEntrustFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ENTRUSTCURRENT, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void attachView() {

    }

    @Override
    protected void initView() {
        EntrustOneFragment fragment1 = EntrustOneFragment.newInstance("GRC");
        EntrustTwoFragment fragment2 = EntrustTwoFragment.newInstance("GRC",1);
        EntrustTwoFragment fragment3 = EntrustTwoFragment.newInstance("GRC",0);
        EntrustTwoFragment fragment4 = EntrustTwoFragment.newInstance("GRC",2);
        mViewList.add(fragment1);
        mViewList.add(fragment2);
        mViewList.add(fragment3);
        mViewList.add(fragment4);
        mTitleList.add(getString(R.string.Processing));
        mTitleList.add(getString(R.string.Receipts));
        mTitleList.add(getString(R.string.unpaid));
        mTitleList.add(getString(R.string.Completed));
        TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getChildFragmentManager(), mViewList
                , mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.buy) {
            mPager.setCurrentItem(2);
        }else if (event != null && event.getCode() == Constants.sell) {
            mPager.setCurrentItem(1);
        }
    }

    @Override
    protected void initDatas() {

    }
}
