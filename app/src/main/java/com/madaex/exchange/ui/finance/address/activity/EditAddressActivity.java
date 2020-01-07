package com.madaex.exchange.ui.finance.address.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  AddressListActivity.java
 * 时间：  2018/8/23 10:22
 * 描述：  ${TODO}
 */

public  class EditAddressActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View {
    @BindView(R.id.tv_addr)
    TextView mTvAddr;
    @BindView(R.id.branch)
    EditText mBranch;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editaddress;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        String xnb_name = getIntent().getStringExtra("xnb_name");
        mTitleView.setText(getString(R.string.paymentaddress)+ xnb_name);
        mTvAddr.setText( getIntent().getStringExtra("address"));
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

    @OnClick({R.id.submit, R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    private void validate() {
        if (TextUtils.isEmpty(mBranch.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_phone));
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_SAVE_COIN_ADDRESS);
        params.put("xnb", getIntent().getStringExtra("xnb"));
        params.put("id", getIntent().getStringExtra("id"));
        params.put("name", mBranch.getText().toString().trim());
        params.put("addr", mTvAddr.getText().toString().trim());
        params.put(ConstantUrl.TYPE, getIntent().getStringExtra(ConstantUrl.TYPE));
        mPresenter.getData(DataUtil.sign2(params));
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void requestSuccess2(CommonDataBean.DataBean data) {

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
    public void sendMsgSuccess(String msg) {

    }
}
