package com.madaex.exchange.ui.market.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.contract.DealInfoContract;
import com.madaex.exchange.ui.market.presenter.DealInfoPresenter;

import java.util.TreeMap;

import butterknife.BindView;

/**
 * 项目：  madaexchange
 * 类名：  CoinInfoFragment.java
 * 时间：  2018/11/30 16:52
 * 描述：  ${TODO}
 */
public class CoinInfoFragment extends BaseNetLazyFragment<DealInfoPresenter> implements DealInfoContract.View {
    @BindView(R.id.currentype)
    TextView mCurrentype;
    @BindView(R.id.exchangeType)
    TextView mExchangeType;
    @BindView(R.id.sellRmb)
    TextView mSellRmb;
    @BindView(R.id.rate)
    TextView mRate;
    @BindView(R.id.hight)
    TextView mHight;
    @BindView(R.id.volume)
    TextView mVolume;
    @BindView(R.id.low)
    TextView mLow;
    private String one_xnb = "";
    private String two_xnb = "";

    public static CoinInfoFragment newInstance(String string) {
        CoinInfoFragment fragment = null;
        if (fragment == null) {
            fragment = new CoinInfoFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        String str = getArguments().getString(Constants.ARGS);
        one_xnb = str.split("/")[0];
        two_xnb = str.split("/")[1];
        isVisible = false;
        isPrepared = false;
        TreeMap params2 = new TreeMap<>();
        params2.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL);
        params2.put("market", one_xnb + "_" + "qc");
        mPresenter.getJavaLineDetail(params2);
    }
    public interface FragmentInteraction {
        void process(int collection);
    }
    private FragmentInteraction listterner;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof FragmentInteraction) {
            listterner = (FragmentInteraction)activity; // 2.2 获取到宿主activity并赋值
        } else{
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listterner = null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coininfo;
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
    public void requestSuccess(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void sendMsgSuccess(CoinList msg) {

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {
        listterner.process(baseBean.getCollection());
        mCurrentype.setText(baseBean.getCurrentype().toUpperCase() + "  " + getString(R.string.currentprice));
        mExchangeType.setText( "GRC  " + baseBean.getCurrentPrice());
        mSellRmb.setText("￥" + baseBean.getSellRmb());
        mRate.setText(baseBean.getRiseRate());
        mHight.setText(baseBean.getHigh() + "");
        mVolume.setText(baseBean.getVolumn().toString() + "");
        mLow.setText(baseBean.getLow() + "");
        if (baseBean.getRiseRate().contains("-")) {
            mExchangeType.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mSellRmb.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mRate.setTextColor(mContext.getResources().getColor(R.color.common_green));
        } else {
            mExchangeType.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mSellRmb.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mRate.setTextColor(mContext.getResources().getColor(R.color.common_red));
        }
    }
}
