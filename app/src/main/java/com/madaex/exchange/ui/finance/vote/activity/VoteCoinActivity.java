package com.madaex.exchange.ui.finance.vote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.ui.adapter.TitlePagerAdapter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.vote.fragment.RankingFragment;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  VoteCoinActivity.java
 * 时间：  2018/8/30 15:05
 * 描述：  ${TODO}
 */

public class VoteCoinActivity extends BaseNetActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.ll_bg)
    LinearLayout mLlBg;
    @BindView(R.id.transtype)
    TextView mTranstype;
    @BindView(R.id.transstatus)
    TextView mTransstatus;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_votecoin;
    }

    @Override
    protected void initInjector() {

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
    protected void initView() {
        int type = getIntent().getIntExtra("id", 1);
        if (type == 1) {
            mToolbarTitleTv.setText(getString(R.string.finance_vote));
            mLlBg.setBackgroundResource(R.mipmap.bg_votecoin);
            mTranstype.setText(getString(R.string.finance_vote));
            mTransstatus.setText(R.string.voterule);
        } else {
            mToolbarTitleTv.setText(getString(R.string.finance_offering));
            mTranstype.setText(getString(R.string.finance_offering));
            mLlBg.setBackgroundResource(R.mipmap.icon_public);
            mTransstatus.setText(R.string.offeringrule);
        }
        //0 -投票上币  1 -参与公募
        RankingFragment fragment1 = RankingFragment.newInstance(0, type);
        RankingFragment fragment2 = RankingFragment.newInstance(1, type);
        mViewList.add(fragment1);
        mViewList.add(fragment2);
        mTitleList.add(getString(R.string.top));
        mTitleList.add(getString(R.string.take));
        TitlePagerAdapter adapter = new TitlePagerAdapter(getSupportFragmentManager(), mViewList
                , mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

        mTransstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    Intent intent = new Intent(mContext, LinkWebViewActivity.class);
                    intent.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.voterule));
                    intent.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.vote);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LinkWebViewActivity.class);
                    intent.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.offeringrule));
                    intent.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.fundraise);
                    startActivity(intent);
                }
            }
        });
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
}
