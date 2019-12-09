package com.madaex.exchange.ui.login.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.login.fragment.ForgetPasswordEmailFragment;
import com.madaex.exchange.ui.login.fragment.ForgetPasswordPhoneFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  ForgetPasswordActivity.java
 * 时间：  2018/8/29 11:43
 * 描述：  ${TODO}
 */

public class ForgetPasswordActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private TextView pager1;
    private TextView pager2;
    private View underLine1;
    private View underLine2;
    private NoScrollViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        pager1 = (TextView) findViewById(R.id.id_page1);
        pager2 = (TextView) findViewById(R.id.id_page2);
        underLine1 = findViewById(R.id.under_line1);
        underLine2 = findViewById(R.id.under_line2);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        ForgetPasswordPhoneFragment phoneFragment = new ForgetPasswordPhoneFragment();
        ForgetPasswordEmailFragment emailFragment = new ForgetPasswordEmailFragment();
        mFragmentList.add(phoneFragment);
        mFragmentList.add(emailFragment);
        pager1.setOnClickListener(this);
        pager2.setOnClickListener(this);
        // 给ViewPager填充数据
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), mFragmentList));
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        underLine1.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.id_page1:
                viewPager.setCurrentItem(0);
                underLine1.setVisibility(View.VISIBLE);
                underLine2.setVisibility(View.INVISIBLE);
                break;
            case R.id.id_page2:
                viewPager.setCurrentItem(1);
                underLine2.setVisibility(View.VISIBLE);
                underLine1.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                underLine1.setVisibility(View.VISIBLE);
                underLine2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                underLine2.setVisibility(View.VISIBLE);
                underLine1.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }

    /**
     * viewpager适配器
     */
    public class MyAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
