package com.madaex.exchange.ui.finance.pay.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  PlatActivity.java
 * 时间：  2019/5/15 10:38
 * 描述：  ${TODO}
 */
public class PlatActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plat;
    }

    @Override
    protected void initInjector() {
        PlatOutFragment fragment1 = PlatOutFragment.newInstance(1);
        PlatOutFragment fragment11 = PlatOutFragment.newInstance(2);
        PlatRecordFragment fragment2 = PlatRecordFragment.newInstance(1);
        PlatRecordFragment fragment3 = PlatRecordFragment.newInstance(2);
        mViewList.add(fragment1);
        mViewList.add(fragment11);
        mViewList.add(fragment2);
        mViewList.add(fragment3);
        mTitleList.add(getString(R.string.toGRC));
        mTitleList.add(getString(R.string.tojd));
        mTitleList.add(getString(R.string.Transferr));
        mTitleList.add(getString(R.string.shift));
        TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getSupportFragmentManager(), mViewList
                , mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_btn_ll:
                break;
        }
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
