package com.madaex.exchange.ui.mine.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.TimerText;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  TransactionPasswordActivity.java
 * 时间：  2018/8/24 14:23
 * 描述：  ${TODO}
 */

public class TransactionPasswordActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View, TimerText.OnTimerListener {
    @BindView(R.id.et_newpassword)
    EditText mEtNewpassword;
    @BindView(R.id.et_replacepassword)
    EditText mEtReplacepassword;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.getcode)
    TimerText mGetcode;

    private String verify_encode="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transactionpassword;
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
        mGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetcode.setOnTimerListener(TransactionPasswordActivity.this);
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
        if (TextUtils.isEmpty(mEtNewpassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrytranspwd));
            return;
        }
        if (TextUtils.isEmpty(mEtReplacepassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrytranspwd));
            return;
        }
        if (!mEtNewpassword.getText().toString().equals(mEtReplacepassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.pwdnosume));
            return;
        }
        if (TextUtils.isEmpty(mCode.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_msg_code));
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_SETPASSWORD);
        params.put("verify_code", mCode.getText().toString().trim());
        params.put("verify_encode", verify_encode);
        params.put("type", "trade");
        params.put("password", mEtReplacepassword.getText().toString().trim());
        mPresenter.getData(DataUtil.sign2(params));
    }


    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        finish();
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {

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
        mGetcode.setText(millisUntilFinished/1000 + getString(R.string.second));
    }

    @Override
    public void onFinish() {
        mGetcode.reset();
    }
}
