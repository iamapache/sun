package com.madaex.exchange.common.base.activity;


import com.madaex.exchange.common.util.ToastUtils;

/**
 * 项目：  frame
 * 类名：  BaseActivity.java
 * 时间：  2018/6/28 14:14
 * 描述：  ${TODO}
 */
public abstract class BaseActivity extends BasePermissionActivity {
    @Override
    protected void attachView() {

    }

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
        showProgressDialog();
    }

    @Override
    public void dismissDialog() {
        dismissProgressDialog();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onUnLogin() {
        ToastUtils.showToast("账号未登录");
    }

    @Override
    public void onFreeze() {
        ToastUtils.showToast("账户冻结");
    }
}
