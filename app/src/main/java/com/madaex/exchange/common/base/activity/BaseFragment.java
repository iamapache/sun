package com.madaex.exchange.common.base.activity;


import android.content.Intent;

import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;

/**
 * 项目：  frame
 * 类名：  BaseFragment.java
 * 时间：  2018/6/28 14:27
 * 描述：  ${TODO}
 */
public abstract class BaseFragment extends RxFragment {
    @Override
    protected void initErrorView() {

    }
    @Override
    public void onError(String message) {

    }

    @Override
    public void stateError(int resid, String errormsg) {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onUnLogin() {
        SPUtils.putString(Constants.TOKEN, "");
        SPUtils.putString(Constants.USER_ID, "");
        startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public void onFreeze() {
        ToastUtils.showToast("账户冻结");
    }
}
