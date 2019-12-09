package com.madaex.exchange.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.languagelib.LanguageType;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;
import com.madaex.exchange.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  LanguageActivity.java
 * 时间：  2018/9/19 10:47
 * 描述：  ${TODO}
 */

public class LanguageActivity extends BaseActivity {

    @BindView(R.id.iv_followsystem)
    ImageView mIvFollowsystem;
    @BindView(R.id.iv_simplified_chinese)
    ImageView mIvSimplifiedChinese;
    private int selectedLanguage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        selectedLanguage = MultiLanguageUtil.getInstance().getLanguageType();
        if (selectedLanguage == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            setTraditionalVisible();
        } else if (selectedLanguage == LanguageType.LANGUAGE_EN) {
            setEnglishVisible();
        }
    }

    private void setEnglishVisible() {
        mIvFollowsystem.setVisibility(View.GONE);
        mIvSimplifiedChinese.setVisibility(View.VISIBLE);
    }

    private void setTraditionalVisible() {
        mIvFollowsystem.setVisibility(View.VISIBLE);
        mIvSimplifiedChinese.setVisibility(View.GONE);
    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_tv_ll,R.id.rl_followsytem, R.id.rl_simplified_chinese})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_tv_ll:
                MultiLanguageUtil.getInstance().updateLanguage(selectedLanguage);
                Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.rl_followsytem:
                selectedLanguage = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                setTraditionalVisible();
                break;
            case R.id.rl_simplified_chinese:
                selectedLanguage = LanguageType.LANGUAGE_EN;
                setEnglishVisible();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

}
