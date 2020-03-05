package com.madaex.exchange.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  AccountManagerActivity.java
 * 时间：  2018/8/29 14:43
 * 描述：  ${TODO}
 */

public class AccountManagerActivity extends BaseActivity {
    @BindView(R.id.loginaccount)
    TextView mLoginaccount;
    @BindView(R.id.confirm)
    TextView mConfirm;
    @BindView(R.id.realname)
    ImageView mRealname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accountmanager;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        mLoginaccount.setText(SPUtils.getString(Constants.MOBILE));
        if (TextUtils.isEmpty(SPUtils.getString(Constants.USERNAME))) {
            mConfirm.setText(R.string.Notauthentic);
            mRealname.setVisibility(View.GONE);
        } else {
            mConfirm.setText(SPUtils.getString(Constants.USERNAME));
            mRealname.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.submit, R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                SPUtils.putString(Constants.TOKEN, "");
                SPUtils.putString(Constants.USER_ID, "");
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
