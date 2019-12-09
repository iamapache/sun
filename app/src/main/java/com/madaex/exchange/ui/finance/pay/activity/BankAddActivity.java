package com.madaex.exchange.ui.finance.pay.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.bank.activity.SelectBankActivity;
import com.madaex.exchange.ui.finance.bank.contract.SelectBank;
import com.madaex.exchange.ui.finance.pay.bean.Payway;
import com.madaex.exchange.ui.finance.pay.contract.WayContract;
import com.madaex.exchange.ui.finance.pay.presenter.WayPresenter;
import com.wc.widget.dialog.IosDialog;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  BankAddActivity.java
 * 时间：  2019/5/13 18:55
 * 描述：  ${TODO}
 */
public class BankAddActivity extends BaseNetActivity<WayPresenter> implements WayContract.View {
    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.nick)
    EditText mNick;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bankadd;
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
        if (getIntent().hasExtra("bean")) {
            Payway.DataBean dataBean = getIntent().getParcelableExtra("bean");
            mAddr.setText(dataBean.getBank());
            mName.setText(dataBean.getName());
            mPassword.setText(dataBean.getBank_branch());
        }
    }

    private int CODE_REQUEST = 0x002;

    @OnClick({R.id.submit, R.id.toolbar_left_btn_ll, R.id.addr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.addr:
                Intent intent;
                intent = new Intent(BankAddActivity.this, SelectBankActivity.class);
                startActivityForResult(intent, CODE_REQUEST);
                break;
        }
    }

    private void validate() {
        if (TextUtils.isEmpty(mAddr.getText().toString())) {
            ToastUtils.showToast(R.string.Selectopeningbank);
            return;
        }
        if (TextUtils.isEmpty(mNick.getText().toString())) {
            ToastUtils.showToast(R.string.branchinformation);
            return;
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            ToastUtils.showToast(R.string.accountname);
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(R.string.bankid);
            return;
        }
        if (TextUtils.isEmpty(mCode.getText().toString())) {
            ToastUtils.showToast(R.string.aiganbankid);
            return;
        }

        Dialog dialog = new IosDialog.Builder(this).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                .setMessage(getString(R.string.submit)).setMessageSize(14)
                .setNegativeButtonColor(Color.GRAY)
                .setNegativeButtonSize(18)
                .setNegativeButton(getString(R.string.cancel), new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonColor(ContextCompat.getColor(mContext, R.color.common_bule))
                .setPositiveButtonSize(18)
                .setPositiveButton(getString(R.string.submit), new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        if (getIntent().hasExtra("bean")) {
                            Payway.DataBean dataBean = getIntent().getParcelableExtra("bean");
                            TreeMap params = new TreeMap<Object, Object>();
                            params.put("act", ConstantUrl.USER_PAYMENTSAVE);
                            params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
                            params.put("id", dataBean.getId());
                            params.put("account", mPassword.getText().toString().trim());
                            params.put("type", 3 + "");
                            params.put("bank", mAddr.getText().toString().trim());
                            params.put("bank_branch", mNick.getText().toString().trim());
                            params.put("name", mName.getText().toString());
                            mPresenter.edit(DataUtil.sign(params));
                        } else {
                            TreeMap params = new TreeMap<Object, Object>();
                            params.put("act", ConstantUrl.USER_PAYMENTADD);
                            params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
                            params.put("account", mPassword.getText().toString().trim());
                            params.put("type", 3 + "");
                            params.put("bank", mAddr.getText().toString().trim());
                            params.put("bank_branch", mNick.getText().toString().trim());
                            params.put("name", mName.getText().toString());
                            mPresenter.edit(DataUtil.sign(params));
                        }
                    }
                }).build();
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            SelectBank.DataBean resultBean = data.getParcelableExtra("data");
            mAddr.setText(resultBean.getBank_name());
        }

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String commonBean) {
        ToastUtils.showToast(commonBean);
        finish();
    }

    @Override
    public void requestSuccess(Payway commonBean) {

    }

    @Override
    public void requestPayWaySuccess(String commonBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
