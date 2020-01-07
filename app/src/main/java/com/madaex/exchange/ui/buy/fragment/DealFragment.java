package com.madaex.exchange.ui.buy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.buy.presenter.CoinPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.activity.DealActivity;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.fragment.EntrustCurrentFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class DealFragment extends BaseNetLazyFragment<CoinPresenter> implements CoinContract.View, TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    Unbinder unbinder;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();

    private String one_xnb = "";
    private String two_xnb = "";

    public static DealFragment newInstance(int string) {
        DealFragment fragment = null;
        if (fragment == null) {
            fragment = new DealFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_deal;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;

        one_xnb = SPUtils.getString(Constants.ONE_XNB, "BAT");
        two_xnb = SPUtils.getString(Constants.TWO_XNB, "ETH");
        mToolbarTitleTv.setText(one_xnb + "/" + two_xnb);
        int str = (int) getArguments().get(Constants.ARGS);
        BuyFragment fragment1 = BuyFragment.newInstance(ConstantUrl.TRANS_TYPE_BUY, one_xnb, two_xnb);
        BuyFragment fragment2 = BuyFragment.newInstance(ConstantUrl.TRANS_TYPE_SELLER, one_xnb, two_xnb);
        EntrustCurrentFragment fragment3 = EntrustCurrentFragment.newInstance(ConstantUrl.ENTRUSTCURRENT, one_xnb, two_xnb);
        EntrustCurrentFragment fragment4 = EntrustCurrentFragment.newInstance(ConstantUrl.ENTRUSTHISTORY, one_xnb, two_xnb);
        mViewList.add(fragment1);
        mViewList.add(fragment2);
        mViewList.add(fragment3);
        mViewList.add(fragment4);
        mTitleList.add(getString(R.string.item_buy));
        mTitleList.add(getString(R.string.item_seller));
        mTitleList.add(getString(R.string.Orders));
        mTitleList.add(getString(R.string.History));
        TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getChildFragmentManager(), mViewList
                , mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(str);
        mPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }
    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll, R.id.toolbar_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                break;
            case R.id.toolbar_right_btn_ll:
                Intent intent = new Intent(mContext, DealActivity.class);
                intent.putExtra("market", one_xnb + "_" + two_xnb);
                intent.putExtra("one_xnb", one_xnb);
                intent.putExtra("two_xnb", two_xnb);
                startActivity(intent);
                break;
            case R.id.toolbar_title_tv:
                FragmentManager fm = getChildFragmentManager();
                CoinListFrament editNameDialog = CoinListFrament.newInstance(Constants.DEAL, one_xnb, two_xnb);
                editNameDialog.show(fm, "fragment_bottom_dialog");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.DEAL) {
            String coin = event.getMsg();
            mToolbarTitleTv.setText(coin);
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];
        }
    }


    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void sendMsgSuccess(final CoinList msg) {
    }

    @Override
    public void sendDealSuccess(String msg) {

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {

    }


    @Override
    public void sendMsgSuccess(DealInfo msg) {

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
