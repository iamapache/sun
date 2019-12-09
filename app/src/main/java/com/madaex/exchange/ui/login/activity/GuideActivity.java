package com.madaex.exchange.ui.login.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.constant.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目：  madaexchange
 * 类名：  GuideActivity.java
 * 时间：  2018/8/28 17:56
 * 描述：  ${TODO}
 */

public class GuideActivity extends BaseActivity {
    private ViewPager mIn_vp;
    private List<View> mViewList;


    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        mViewList = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater().from(GuideActivity.this);
        View view1 = lf.inflate(R.layout.guide_indicator1, null);
        View view2 = lf.inflate(R.layout.guide_indicator2, null);
        View view3 = lf.inflate(R.layout.guide_indicator3, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.putBoolean(Constants.guide, false);

//                if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
//                    Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                }
            }
        });
        mIn_vp = (ViewPager) findViewById(R.id.in_viewpager);
        mIn_vp.setAdapter(new ViewPagerAdatper(mViewList));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initInjector() {

    }


    public class ViewPagerAdatper extends PagerAdapter {
        private List<View> mViewList;

        public ViewPagerAdatper(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

}
