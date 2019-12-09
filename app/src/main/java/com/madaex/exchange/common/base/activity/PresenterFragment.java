package com.madaex.exchange.common.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.common.view.CustomDialog;

/**
 * 项目：  frame
 * 类名：  PresenterFragment.java
 * 时间：  2018/6/28 12:26
 * 描述：  ${TODO}
 */
public abstract class PresenterFragment extends
        SimpleFragment implements BaseContract.BaseView {
    private CustomDialog dialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initErrorView();
        attachView();
    }

    public CustomDialog getProgressDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(getActivity());
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void showProgressDialog() {
        getProgressDialog().show();
    }

    public void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null && !getActivity().isFinishing()) {
            dialog.dismiss();
        }
    }
}
