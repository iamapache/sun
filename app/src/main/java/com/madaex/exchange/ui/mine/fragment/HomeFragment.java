package com.madaex.exchange.ui.mine.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.ui.mine.bean.update;
import com.madaex.exchange.ui.mine.contract.PageHomeContract;
import com.madaex.exchange.ui.mine.presenter.PageHomePresenter;

import java.util.TreeMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class HomeFragment extends BaseNetLazyFragment<PageHomePresenter> implements PageHomeContract.View {

    Unbinder unbinder;

    public static HomeFragment newInstance(String string) {
        HomeFragment fragment = null;
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_USERINFO_STATUS);
        mPresenter.getData(DataUtil.sign(params));
        if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
    }






    @Override
    public void requestSuccess(User user) {
    }

    @Override
    public void nodata(String msg) {

        SPUtils.putString(Constants.TOKEN, "");
    }



    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestupdate(update bean) {
    }


}
