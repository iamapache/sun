package com.madaex.exchange.ui.finance.c2c.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.adorkable.iosdialog.BottomSheetDialog;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.buy.presenter.CoinPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.c2c.fragment.MyEntrustFragment;
import com.madaex.exchange.ui.finance.c2c.fragment.PlatformEntrustFragment;
import com.madaex.exchange.ui.market.bean.Home;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  C2CListActivity.java
 * 时间：  2019/5/9 14:16
 * 描述：  ${TODO}
 */
public class C2CListActivity extends BaseNetActivity<CoinPresenter> implements CoinContract.View {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();

    private String one_xnb = "";
    private String two_xnb = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_c2clist;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        one_xnb = SPUtils.getString(Constants.C2CBB, "GRC");
        mToolbarTitleTv.setText(getString(R.string.coin)+one_xnb );
        PlatformEntrustFragment fragment3 = PlatformEntrustFragment.newInstance(ConstantUrl.ENTRUSTCURRENT);
        MyEntrustFragment fragment4 = MyEntrustFragment.newInstance(ConstantUrl.ENTRUSTHISTORY);
        mViewList.add(fragment3);
        mViewList.add(fragment4);
        mTitleList.add(getString(R.string.Platform));
        mTitleList.add(getString(R.string.Mycommission));
        TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getSupportFragmentManager(), mViewList
                , mTitleList);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.buy) {
            mPager.setCurrentItem(1);
        }else if (event != null && event.getCode() == Constants.sell) {
            mPager.setCurrentItem(1);
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
                                event.setCode(Constants.C2C);
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
                                event.setCode(Constants.C2C);
                                EventBus.getDefault().post(event);
                            }
                        })
                .show();

    }


    @Override
    protected void initDatas() {
    }

    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void sendMsgSuccess(CoinList msg) {

    }

    @Override
    public void sendDealSuccess(String msg) {

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {

    }

    @Override
    public void sendMsgSuccess(DealInfo msg) {

    }
}
