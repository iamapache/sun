package com.madaex.exchange.ui.finance.c2c.activity;

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

public class C2CTransationDetailActivity extends BaseNetActivity<C2CTransationPresenter> implements C2CTransationContract.View {

    @BindView(R.id.transmoney)
    TextView mTransmoney;
    @BindView(R.id.transstatus)
    TextView mTransstatus;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.bank_addr)
    TextView mBankAddr;
    @BindView(R.id.bank_card)
    TextView mBankCard;
    @BindView(R.id.tradeno)
    TextView mTradeno;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2ctransation_detail;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FINANCE_PAYMENT_INFORMATION);
        params.put("id", getIntent().getStringExtra("id"));
        mPresenter.getc2cDetail(DataUtil.sign(params));
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

    }

    @Override
    public void requestError(String msg) {
ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestSuccess(C2CTransationDetail commonBean) {
        mTransmoney.setText("￥  " + commonBean.getData().getNum());
        mTransstatus.setText(commonBean.getData().getStatus());
        mName.setText(commonBean.getData().getRecetruename());
        mBankAddr.setText(commonBean.getData().getRece_bank_addr());
        mBankCard.setText(commonBean.getData().getRece_bank_card());
        mTradeno.setText(commonBean.getData().getTradeno());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
