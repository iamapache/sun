package com.madaex.exchange.common.base.activity.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  frame
 * 类名：  SimpleFragment.java
 * 时间：  2018/6/28 12:25
 * 描述：  ${TODO}
 */
public abstract class SimpleDialogFragment extends DialogFragment {
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
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }




    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }
}
