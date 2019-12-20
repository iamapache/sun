package com.madaex.exchange.ui.finance.address.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.TimerText;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;

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

public class AddAddressActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View, TimerText.OnTimerListener {
    @BindView(R.id.addr)
    EditText mAddr;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.getcode)
    TimerText mGetcode;
    private String verify_encode="";
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

        String xnb_name = getIntent().getStringExtra("xnb_name");
        mTitleView.setText(getString(R.string.paymentaddress) + xnb_name);

        mGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetcode.setOnTimerListener(AddAddressActivity.this);
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.VERIFY_SEND_CODE);
                params.put("type", "3");
                if(SPUtils.getBoolean(Constants.ISMOBILE, true)){
                    params.put("code_type", "mobile");
                    params.put("mobile", SPUtils.getString(Constants.MOBILE, ""));
                }else {
                    params.put("code_type", "email");
                    params.put("email", SPUtils.getString(Constants.MOBILE, ""));
                }
                mPresenter.getMsgCode(DataUtil.sign(params));
            }
        });
    }

    @Override
    protected void initDatas() {

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
        if(msg.equals("-1")){
            ToastUtils.showToast(getString(R.string.Completepassword));
            startActivity(new Intent(mContext, TransactionPasswordActivity.class));
        }else {
            ToastUtils.showToast(msg);
        }
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({ R.id.submit, R.id.toolbar_left_btn_ll})
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
        if (TextUtils.isEmpty(mAddr.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_phone));
            return;
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_pwd));
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_msg_code));
            return;
        }
        if (TextUtils.isEmpty(mCode.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_msg_code));
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_INSERT_COIN_ADDRESS);
        params.put("xnb", getIntent().getStringExtra("xnb"));
        params.put("addr", mAddr.getText().toString().trim());
        params.put("name", mName.getText().toString().trim());
        params.put("verify_code", mCode.getText().toString().trim());
        params.put("verify_encode", verify_encode);
        params.put(ConstantUrl.TYPE, getIntent().getStringExtra(ConstantUrl.TYPE));
        params.put("paypassword", mPassword.getText().toString().trim());
        mPresenter.getData(DataUtil.sign2(params));
    }

    @Override
    public void sendMsgSuccess(String msg) {
        mGetcode.reset();
        mGetcode.start();
        verify_encode = msg;
        ToastUtils.showToast(getString(R.string.send_msg_already));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mGetcode.setText(millisUntilFinished/1000 + "秒");
    }

    @Override
    public void onFinish() {
        mGetcode.reset();
    }


}
