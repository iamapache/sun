package com.madaex.exchange.ui.login.activity;

import android.content.Context;

import com.alibaba.verificationsdk.ui.VerifyActivity;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 项目：  madaexchange
 * 类名：  SafeActivity.java
 * 时间：  2018/11/23 17:48
 * 描述：  ${TODO}
 */
public class SafeActivity extends VerifyActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        MultiLanguageUtil.attachBaseContext(newBase);
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
}
