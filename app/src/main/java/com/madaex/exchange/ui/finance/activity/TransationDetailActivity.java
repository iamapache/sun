package com.madaex.exchange.ui.finance.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransation;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransationDetail;
import com.madaex.exchange.ui.finance.c2c.bean.TransationDetail;
import com.madaex.exchange.ui.finance.c2c.contract.C2CTransationContract;
import com.madaex.exchange.ui.finance.c2c.presenter.C2CTransationPresenter;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  C2CTransationListActivity.java
 * 时间：  2018/8/30 12:24
 * 描述：  ${TODO}
 */

public class TransationDetailActivity extends BaseNetActivity<C2CTransationPresenter> implements C2CTransationContract.View {


    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.toaccount)
    TextView mToaccount;
    @BindView(R.id.fee)
    TextView mFee;
    @BindView(R.id.submissiontime)
    TextView mSubmissiontime;
    @BindView(R.id.chulitime)
    TextView mChulitime;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.chulistaus)
    TextView mChulistaus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asset_transation_detail;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
    }

    String type;

    @Override
    protected void initDatas() {
        String xnb_name = getIntent().getStringExtra("xnb_name");
        type = getIntent().getStringExtra(ConstantUrl.TYPE);
        mTitleView.setText(getString(R.string.Paymoneyrecord) + xnb_name);
        if (type.equals(ConstantUrl.TRANS_TYPE_SELLER)) {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_CASH_INFO);
            params.put("id", getIntent().getStringExtra("id"));
            params.put("wallet_type", getIntent().getStringExtra("wallet_type"));
            mPresenter.getDetail(DataUtil.sign(params));
        } else {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_RECHARGE_INFO);
            params.put("id", getIntent().getStringExtra("id"));
            params.put("wallet_type", getIntent().getStringExtra("wallet_type"));
            mPresenter.getDetail(DataUtil.sign(params));
        }

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
    public void requestSuccess(List<C2CTransation.DataBean> commonBean) {

    }

    @Override
    public void requestSuccess2(List<C2CTransation.DataBean> commonBean) {

    }

    @Override
    public void requestSuccess3(TransationDetail commonBean) {
        if (type.equals(ConstantUrl.TRANS_TYPE_SELLER)) {
            mNumber.setText("-" + commonBean.getData().getNum());
        } else {
            mNumber.setText("+" + commonBean.getData().getNum());
        }

        mAddress.setText(commonBean.getData().getUsername());
        mToaccount.setText(commonBean.getData().getMum() + commonBean.getData().getCoinname());
        mFee.setText(commonBean.getData().getFee() + commonBean.getData().getCoinname());
        mSubmissiontime.setText(commonBean.getData().getAddtime());
        mChulitime.setText(commonBean.getData().getEndtime());
        mChulistaus.setText(commonBean.getData().getStatus());
        if(commonBean.getData().getState()==0){
            mChulistaus.setTextColor(getResources().getColor(R.color.chuli));
        }else if(commonBean.getData().getState()==1){
            mChulistaus.setTextColor(getResources().getColor(R.color.depth_buy_line));
        }else if(commonBean.getData().getState()==3){
            mChulistaus.setTextColor(getResources().getColor(R.color.shibai));
        }
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestSuccess(C2CTransationDetail commonBean) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
