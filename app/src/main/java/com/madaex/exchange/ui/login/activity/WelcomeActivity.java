package com.madaex.exchange.ui.login.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.PermissionUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 项  目 :  loan
 * 包  名 :  com.app.loan.ui.activity
 * 类  名 :  WelcomeActivity
 * 时  间 :  2017/5/4 11:19
 * 描  述 :  ${TODO}
 */
public class WelcomeActivity extends BaseActivity {


    @BindView(R.id.img)
    ImageView mImageView;

    @Override
    public void initDatas() {
        handler.sendEmptyMessageDelayed(0, 3600);
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
        Glide.with(this).load(R.mipmap.welcome).into(new GlideDrawableImageViewTarget(mImageView,1)); //加载一次

        if (PermissionUtils.hasAlwaysDeniedPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            PermissionUtils.requestPermissions(this, 0x11, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                }

                @Override
                public void onPermissionDenied(String[] deniedPermissions) {
                }
            });
        }
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
