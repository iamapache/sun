package com.madaex.exchange.ui.finance.transfer.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.transfer.fragment.NCOutFragment;
import com.madaex.exchange.ui.finance.transfer.fragment.NCRecordFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  sunn
 * 类名：  TransferActivity.java
 * 时间：  2019/5/22 17:47
 * 描述：  ${TODO}
 */
public class TransferActivity extends BaseNetActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfers;
    }

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();


    @Override
    protected void initInjector() {
        NCOutFragment fragment1 = NCOutFragment.newInstance(ConstantUrl.ENTRUSTCURRENT);
        NCRecordFragment fragment2 = NCRecordFragment.newInstance(1);
        NCRecordFragment fragment3 = NCRecordFragment.newInstance(2);
        mViewList.add(fragment1);
        mViewList.add(fragment2);
        mViewList.add(fragment3);
        mTitleList.add(getString(R.string.Transfer));
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
