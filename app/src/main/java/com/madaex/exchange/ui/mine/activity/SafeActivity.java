package com.madaex.exchange.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  SafeActivity.java
 * 时间：  2018/8/24 14:11
 * 描述：  ${TODO}
 */

public class SafeActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_safe;
    }

    @Override
    protected void initInjector() {

    }


    @Override
    protected void initView() {

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

    @OnClick({R.id.tv_login, R.id.tv_transaction,R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(mContext, LoginPasswordActivity.class));
                break;
            case R.id.tv_transaction:
                startActivity(new Intent(mContext, TransactionPasswordActivity.class));
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }
}
