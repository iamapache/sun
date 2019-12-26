package com.madaex.exchange.ui.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.constant.Constants;


/**
 * 项  目 :  loan
 * 包  名 :  com.app.loan.ui.activity
 * 类  名 :  WelcomeActivity
 * 时  间 :  2017/5/4 11:19
 * 描  述 :  ${TODO}
 */
public class WelcomeActivity extends BaseActivity {


    @Override
    public void initDatas() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initInjector() {

    }

    @Override
    protected void initView() {

    }


    @Override
    protected boolean isSetStatusBar() {
        return false;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome() {

        if (SPUtils.getBoolean(Constants.guide, true)) {
            Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
