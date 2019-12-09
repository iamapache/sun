package com.madaex.exchange.ui.market.fragment;

import android.os.Bundle;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.ui.constant.Constants;

/**
 * 项目：  madaexchange
 * 类名：  EntrustHistoryFragment.java
 * 时间：  2018/9/4 16:14
 * 描述：  ${TODO}
 */

public class EntrustHistoryFragment extends BaseNetLazyFragment {
    public static EntrustHistoryFragment newInstance(String string) {
        EntrustHistoryFragment fragment = null;
        if (fragment == null) {
            fragment = new EntrustHistoryFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entrustcurrent;
    }

    @Override
    public void initInjector() {

    }
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        initDatas();
    }
    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
