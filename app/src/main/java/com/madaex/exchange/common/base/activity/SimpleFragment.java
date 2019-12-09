package com.madaex.exchange.common.base.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.BaseApplication;
import com.madaex.exchange.common.di.component.DaggerFragmentComponent;
import com.madaex.exchange.common.di.component.FragmentComponent;
import com.madaex.exchange.common.di.module.FragmentModule;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  frame
 * 类名：  SimpleFragment.java
 * 时间：  2018/6/28 12:25
 * 描述：  ${TODO}
 */
public abstract class SimpleFragment extends Fragment {
    protected View rootView;
    private Unbinder unbinder;
    private Activity mActivity;
    public Context mContext;
    protected FragmentComponent mFragmentComponent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        mContext = getActivity();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str) {
        switch (str) {
            case "EVENT_REFRESH_LANGUAGE":
                break;
        }
    }
    public void startActivityAfterLogin(Intent intent) {
        //未登录（这里用自己的登录逻辑去判断是否未登录）
        if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            ComponentName componentName = new ComponentName(getContext(), LoginActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            super.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragmentComponent();
        initInjector();
        initView();
        initDatas();
    }

    /**
     * 切换密码显示和隐藏
     *
     * @param view
     */
    final protected void switchPassword(EditText view, ImageView mSwitchImg) {
        if (mSwitchImg.isSelected()){
            // 输入为密码且可见
            view.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        }else {
            // 设置文本类密码（默认不可见），这两个属性必须同时设置
            view.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if (mSwitchImg.isSelected()) {
            mSwitchImg.setImageResource(R.mipmap.denglu_zy);
        } else {
            mSwitchImg.setImageResource(R.mipmap.denglu_by);
        }
    }
    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((BaseApplication) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }
    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    protected abstract int getLayoutId();

    public abstract void initInjector();

    public abstract void attachView();

    protected abstract void initErrorView();
    protected abstract void initView();
    protected abstract void initDatas();
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof Activity) {
//            mActivity = (Activity) context;
//        }
//    }
//
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mActivity = null;
//    }
}
