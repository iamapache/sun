package com.madaex.exchange.common.base.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.BaseApplication;
import com.madaex.exchange.common.di.component.ActivityComponent;
import com.madaex.exchange.common.di.component.DaggerActivityComponent;
import com.madaex.exchange.common.di.module.ActivityModule;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  frame
 * 类名：  SimpleActivity.java
 * 时间：  2018/6/28 11:08
 * 描述：  ${TODO}
 */
public abstract class SimpleActivity extends AppCompatActivity {
    private boolean isConfigChange = false;
    public Context mContext;
    private Unbinder mUnbinder;
    protected ActivityComponent mActivityComponent;
    SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        isConfigChange = false;
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initWindow();
        setContentView(getLayoutId());
        mContext = this;
        mUnbinder = ButterKnife.bind(this);
        initActivityComponent();
        initInjector();
        attachView();
        initErrorView();
        initView();
        initDatas();
    }


    public void startActivityAfterLogin(Intent intent) {
        //未登录（这里用自己的登录逻辑去判断是否未登录）
        if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            ComponentName componentName = new ComponentName(mContext, LoginActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            super.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((BaseApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(newBase));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String str) {
        switch (str) {
            case "EVENT_REFRESH_LANGUAGE":
//                changeAppLanguage();
                recreate();//刷新界面
                break;
        }
    }


    public void changeAppLanguage() {
        String sta = SPUtils.getString("language");
        if(sta != null && !"".equals(sta)){
            // 本地语言设置
            Locale myLocale = new Locale(sta);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
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
    /**
     * 子类实现
     * 获取布局文件
     */
    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void attachView();

    protected abstract void initView();

    protected abstract void initDatas();

    protected abstract void initErrorView();

    /**
     * 重写onConfigurationChanged规避横竖屏切换时候重新进入onCreate生命周期
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        BaseApplication.getInstance().finishActivity(this);
        EventBus.getDefault().unregister(this);
    }


    /**
     * 是否设置沉浸式
     *
     * @return
     */
    protected boolean isSetStatusBar() {
        return true;
    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isSetStatusBar()) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus(true);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorTheme);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void setStatusBarTintRes(int color) {
        if (tintManager != null) {
            tintManager.setStatusBarTintResource(color);
        }
    }


}
