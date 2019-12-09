package com.madaex.exchange.common.base.activity;

import android.view.View;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.common.view.CustomDialog;

/**
 * 项目：  frame
 * 类名：  PresenterActivity.java
 * 时间：  2018/6/28 12:09
 * 描述：  ${TODO}
 */
public abstract class PresenterActivity extends
        SimpleActivity implements BaseContract.BaseView{
    private CustomDialog dialog;
    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    /**
     * 上次点击时间
     */
    private long lastClick = 0;


    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    public void onClick(final View view) {
        if (!isFastClick()) {
        }
    }

    public CustomDialog getProgressDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
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
    public void onDestroy() {
        if(dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }
}
