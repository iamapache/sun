package com.madaex.exchange.ui.buy.fragment;

import android.os.Bundle;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetFragment;
import com.madaex.exchange.ui.constant.Constants;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class SellerFragment extends BaseNetFragment{

    public static SellerFragment newInstance(String string) {
        SellerFragment fragment = null;
        if (fragment == null) {
            fragment = new SellerFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_seller;
    }

    @Override
    public void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
