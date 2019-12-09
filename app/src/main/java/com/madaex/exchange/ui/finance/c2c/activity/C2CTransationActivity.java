package com.madaex.exchange.ui.finance.c2c.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.adorkable.iosdialog.BottomSheetDialog;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.adapter.TitlePagerAdapter;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.c2c.fragment.TransationBuyFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.TransationSellerFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  C2CTransationActivity.java
 * 时间：  2018/8/30 10:11
 * 描述：  ${TODO}
 */

public class C2CTransationActivity extends BaseActivity {
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();
    private int position = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2ctransation;
    }

    @Override
    protected void initInjector() {

    }

    private int current = 0;

    @Override
    protected void initView() {
        one_xnb = SPUtils.getString(Constants.C2CBB, "GRC");
        mToolbarTitleTv.setText(getString(R.string.item_buy) + one_xnb);
        TransationBuyFragment fragment1 = TransationBuyFragment.newInstance(one_xnb);
        TransationSellerFragment fragment2 = TransationSellerFragment.newInstance(one_xnb);

        if(getIntent().hasExtra("type")){
            if (getIntent().getStringExtra("type").equals("2")) {
                current = 0;
                mToolbarTitleTv.setText(getString(R.string.item_buy) + one_xnb);
                mViewList.add(fragment1);
                mTitleList.add(getString(R.string.item_buy));
            } else if (getIntent().getStringExtra("type").equals("1")) {
                current = 1;
                mToolbarTitleTv.setText(getString(R.string.item_seller) + one_xnb);
                mViewList.add(fragment2);
                mTitleList.add(getString(R.string.item_seller));
            }
        }else {
            mViewList.add(fragment1);
            mTitleList.add(getString(R.string.item_buy));
            mViewList.add(fragment2);
            mTitleList.add(getString(R.string.item_seller));
        }

        TitlePagerAdapter adapter = new TitlePagerAdapter(getSupportFragmentManager(), mViewList
                , mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(current);
        mPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if (position == 0) {
                    mToolbarTitleTv.setText(getString(R.string.item_buy) + one_xnb);
                } else if (position == 1) {
                    mToolbarTitleTv.setText(getString(R.string.item_seller) + one_xnb);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private String one_xnb = "";
    private String two_xnb = "";

    @Override
    protected void initDatas() {


    }


    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll, R.id.toolbar_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_btn_ll:
                break;
            case R.id.toolbar_title_tv:
//                showBottomSheetDialog();
                break;
        }
    }
    public void showBottomSheetDialog() {
        new BottomSheetDialog(mContext)
                .init()
                .setTitle(getString(R.string.selectcoin))
                .setCancelable(true)    //设置手机返回按钮是否有效
                .setCanceledOnTouchOutside(true)  //设置 点击空白处是否取消 Dialog 显示
                //如果条目样式一样，可以直接设置默认样式
                .addSheetItem("GRC",
                        //可以对一个条目单独设置字体样式
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mToolbarTitleTv.setText(getString(R.string.coin)+"GRC" );
                                Event event = new Event();
                                event.setMsg("GRC");
                                event.setCode(Constants.C2CTrans);
                                EventBus.getDefault().post(event);
                            }
                        })
                .addSheetItem("SNRC",
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mToolbarTitleTv.setText(getString(R.string.coin)+"SNRC" );
                                Event event = new Event();
                                event.setMsg("SNRC");
                                event.setCode(Constants.C2CTrans);
                                EventBus.getDefault().post(event);
                            }
                        })
                .show();

    }

}
