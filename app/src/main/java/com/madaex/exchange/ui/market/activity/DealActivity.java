package com.madaex.exchange.ui.market.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhh.rxlife2.RxLife;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.adapter.TitleStatePagerAdapter;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.bean.Position;
import com.madaex.exchange.ui.market.contract.DealInfoContract;
import com.madaex.exchange.ui.market.fragment.CoinDetailFragment;
import com.madaex.exchange.ui.market.fragment.CoinInfoFragment;
import com.madaex.exchange.ui.market.fragment.DealFragment;
import com.madaex.exchange.ui.market.fragment.HistoryDetailFragment;
import com.madaex.exchange.ui.market.fragment.KLineFragment;
import com.madaex.exchange.ui.market.fragment.TransFragment;
import com.madaex.exchange.ui.market.manager.CustomLrcPagerAdapter;
import com.madaex.exchange.ui.market.presenter.DealInfoPresenter;
import com.madaex.exchange.view.WrapContentHeightViewPager;
import com.madaex.exchange.websocket.RxWebSocket;
import com.madaex.exchange.websocket.WebSocketInfo;
import com.madaex.exchange.websocket.WebSocketSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import okhttp3.WebSocket;

/**
 * 项目：  madaexchange
 * 类名：  DealActivity.java
 * 时间：  2018/8/31 15:33
 * 描述：  ${TODO}
 */

public class DealActivity extends BaseNetActivity<DealInfoPresenter> implements DealInfoContract.View, CoinInfoFragment.FragmentInteraction {
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    @BindView(R.id.tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tab_layout2)
    TabLayout mTabLayout2;
    @BindView(R.id.pager2)
    WrapContentHeightViewPager mPager2;

    @BindView(R.id.tv_buy)
    TextView mTvBuy;
    @BindView(R.id.tv_seller)
    TextView mTvSeller;
    @BindView(R.id.toolbar_right_btn)
    ImageView mToolbarRightBtn;

    @BindView(R.id.currentype)
    TextView mCurrentype;
    @BindView(R.id.exchangeType)
    TextView mExchangeType;
    @BindView(R.id.sellRmb)
    TextView mSellRmb;
    @BindView(R.id.rate)
    TextView mRate;
    @BindView(R.id.hight)
    TextView mHight;
    @BindView(R.id.volume)
    TextView mVolume;
    @BindView(R.id.low)
    TextView mLow;
    CustomLrcPagerAdapter adapter;
    @BindView(R.id.bili)
    TextView bili;
    private String one_xnb = "BAT";
    private String two_xnb = "ETH";
    private String market_type="0";
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();

    private List<String> mTitleList2 = new ArrayList<>();
    private List<Fragment> mViewList2 = new ArrayList<>();
    private int position = 5;
    int item = 0;
    int parentposition = 0;
    int childposition = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_deal;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        one_xnb = getIntent().getStringExtra("one_xnb");
        two_xnb = getIntent().getStringExtra("two_xnb");
        market_type =getIntent().getStringExtra("market_type");
        parentposition = getIntent().getIntExtra(Constants.INFO,0);
        childposition = getIntent().getIntExtra(Constants.INFO_CHILD,0);
        mToolbarTitleTv.setText(one_xnb + "/" + two_xnb);
        mTvBuy.setText(getString(R.string.item_buy) + " " + one_xnb);
        mTvSeller.setText(getString(R.string.item_seller) + " " + one_xnb);
        mTitleList.add(getString(R.string.onef));
        mTitleList.add(getString(R.string.ONEfiveff));
        mTitleList.add(getString(R.string.onefivef));
        mTitleList.add(getString(R.string.threef));
        mTitleList.add(getString(R.string.onehours));
        mTitleList.add(getString(R.string.fourhours));
        mTitleList.add(getString(R.string.onedays));
        mTitleList.add(getString(R.string.oneweeks));


        for (int j = 0; j < mTitleList.size(); j++) {
            if (j == 0) {
                KLineFragment fragment1 = KLineFragment.newInstance("1min", true);
                mViewList.add(fragment1);
            } else if (j == 1) {
                KLineFragment fragment1 = KLineFragment.newInstance("5min", true);
                mViewList.add(fragment1);
            }else if (j == 2) {
                KLineFragment fragment1 = KLineFragment.newInstance("15min", true);
                mViewList.add(fragment1);
            }else if (j == 3) {
                KLineFragment fragment1 = KLineFragment.newInstance("30min", true);
                mViewList.add(fragment1);
            } else if (j == 4) {
                KLineFragment fragment1 = KLineFragment.newInstance("1hour", true);
                mViewList.add(fragment1);
            } else if (j == 5) {
                KLineFragment fragment1 = KLineFragment.newInstance("4hour", true);
                mViewList.add(fragment1);
            } else if (j == 6) {
                KLineFragment fragment1 = KLineFragment.newInstance("1day", true);
                mViewList.add(fragment1);
            }else if (j == 7) {
                KLineFragment fragment1 = KLineFragment.newInstance("1week", true);
                mViewList.add(fragment1);
            }
        }
        TitleStatePagerAdapter adapter = new TitleStatePagerAdapter(getSupportFragmentManager(), mViewList
                , mTitleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(5);
        mViewPager.setOffscreenPageLimit(0);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        CoinDetailFragment fragment1 = CoinDetailFragment.newInstance(one_xnb);
        TransFragment fragment2 = TransFragment.newInstance(one_xnb, two_xnb);
        HistoryDetailFragment fragment3 = HistoryDetailFragment.newInstance(one_xnb, two_xnb);

        mViewList2.clear();
        mTitleList2.clear();
        mViewList2.add(fragment1);
        mViewList2.add(fragment2);
        mViewList2.add(fragment3);

        mTitleList2.add(getString(R.string.introduce));
        mTitleList2.add(getString(R.string.GOrders));
        mTitleList2.add(getString(R.string.make));
        TitleStatePagerAdapter adapter2 = new TitleStatePagerAdapter(getSupportFragmentManager(), mViewList2
                , mTitleList2);
        mPager2.setAdapter(adapter2);
        mPager2.setCurrentItem(0);
        mPager2.setOffscreenPageLimit(2);
        mTabLayout2.setupWithViewPager(mPager2);
        mTabLayout2.setTabsFromPagerAdapter(adapter2);
        mToolbarRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclik();
            }
        });



        mPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((NestedScrollView) findViewById(R.id.nestedScrollView)).scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void onclik() {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            HashMap params = new HashMap<>();
            params.put("act", ConstantUrl.TRADE_IS_COLLECTION);
            params.put("one_xnb", one_xnb);
            params.put("two_xnb", two_xnb);
            params.put("status", item == 0 ? 1 + "" : 0 + "");
            mPresenter.collection(DataUtil.sign(params));
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.MARK) {
            String coin = event.getMsg();
            Position position =  (Position) event.getData();
            parentposition=position.getParent();
            childposition=position.getChild();
//            mToolbarTitleTv.setText(coin);
//            one_xnb = coin.split("/")[0];
//            two_xnb = coin.split("/")[1];
//            mTvBuy.setText(getString(R.string.item_buy) + " " + one_xnb);
//            mTvSeller.setText(getString(R.string.item_seller) + " " + one_xnb);
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_COIN_LIST);
            mPresenter.getData(DataUtil.sign(params));

        }else if (event != null && event.getCode() == Constants.change) {
            String coin = event.getMsg();
        }
    }

    private WebSocket mWebSocket;

    @Override
    protected void initDatas() {


//        TreeMap params = new TreeMap<>();
//        params.put("act", ConstantUrl.TRADE_COIN_LIST);
//        mPresenter.getData(DataUtil.sign(params));
        TreeMap params2 = new TreeMap<>();
        params2.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL);
        params2.put("market", one_xnb + "_" + two_xnb);
        params2.put("market_type", getIntent().getStringExtra("market_type"));
        mPresenter.getJavaLineDetail(DataUtil.sign(params2));

        RxWebSocket.get(Constant.Websocket)
                .compose(RxLife.with(this).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onMessage(String text) {
                    }

                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        Log.d("MainActivity", "onOpen1:");
                        mWebSocket = webSocket;
                    }

                    @Override
                    protected void onReconnect() {

                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.toolbar_left_btn, R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll, R.id.tv_buy, R.id.tv_seller, R.id.toolbar_title_tv, R.id.fullscreen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn:
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_btn_ll:
                break;
            case R.id.toolbar_title_tv:
//                FragmentManager fm0 = getSupportFragmentManager();
//                CoinListFrament editNameDialog = CoinListFrament.newInstance(Constants.MARK, one_xnb, two_xnb);
//                editNameDialog.show(fm0, "fragment_bottom_dialog");
                break;
            case R.id.tv_buy:
                showBuyDialogfragment(ConstantUrl.TRANS_TYPE_BUY);
                break;
            case R.id.tv_seller:
                showBuyDialogfragment(ConstantUrl.TRANS_TYPE_SELLER);
                break;
            case R.id.fullscreen:
                Intent intent = getIntent();
                intent.setClass(mContext, KLineActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
        }
    }

    private void showBuyDialogfragment(String str) {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            FragmentManager fm = getSupportFragmentManager();
            DealFragment dealFragmentbuy = DealFragment.newInstance(str, one_xnb, two_xnb);
            dealFragmentbuy.show(fm, "dealFragmentbuy");
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }


    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        if (item == 0) {
            item = 1;
            mToolbarRightBtn.setBackgroundResource(R.mipmap.optional_select);
        } else if (item == 1) {
            item = 0;
            mToolbarRightBtn.setBackgroundResource(R.mipmap.img_collect);
        }
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebSocket != null) {
            mWebSocket.cancel();
        }
    }

    private List<String> mTitleInfoList = new ArrayList<>();
    private List<Fragment> mViewInfoList = new ArrayList<>();

    @Override
    public void sendMsgSuccess(final CoinList data) {
//        for (CoinList.DataBean dataBean : data.getData()) {
//
//        }
//        mTitleInfoList.clear();
//        mViewInfoList.clear();
//        for (int i = 0; i < data.getData().size(); i++) {
//            if(parentposition==i){
//               CoinList.DataBean  dataBean = data.getData().get(i);
//                for (int k = 0; k < dataBean.getList().size(); k++) {
//                    CoinList.DataBean.ListBean bean =dataBean.getList().get(k);
//                    mTitleInfoList.add(bean.getName());
//                    CoinInfoFragment fragment3 = CoinInfoFragment.newInstance(bean.getName());
//                    mViewInfoList.add(fragment3);
//
//                }
//            }
//        }
//        adapter = new CustomLrcPagerAdapter(getSupportFragmentManager()
//                , mTitleInfoList);
//        mVpDetail.setAdapter(adapter);
//        mVpDetail.setCurrentItem(childposition);
//        adapter.updateData(mTitleInfoList);
//        mVpDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                String coin = mTitleInfoList.get(position);
//                mToolbarTitleTv.setText(coin);
//                one_xnb = coin.split("/")[0];
//                two_xnb = coin.split("/")[1];
//                mTvBuy.setText(getString(R.string.item_buy) + " " + one_xnb);
//                mTvSeller.setText(getString(R.string.item_seller) + " " + one_xnb);
//                Event event = new Event();
//                event.setCode(Constants.change);
//                event.setMsg(coin);
//                EventBus.getDefault().post(event);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    public void requestDetailSuccess(Home baseBean) {
        mCurrentype.setText(baseBean.getCurrentype().toUpperCase() + "  " + getString(R.string.currentprice));
        mExchangeType.setText(  baseBean.getCurrentPrice());
        mSellRmb.setText("≈¥" + baseBean.getSellRmb());
        mRate.setText(baseBean.getRiseRate());
        mHight.setText(baseBean.getHigh() + "");
        mVolume.setText(DataUtil.thousand(baseBean.getVolumn(), mContext)+"");
        mLow.setText(baseBean.getLow() + "");
        bili.setText(baseBean.getRiseRate());
        if (baseBean.getRiseRate().contains("-")) {
            mExchangeType.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mSellRmb.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mRate.setTextColor(mContext.getResources().getColor(R.color.common_green));
            bili.setBackgroundResource( R.drawable.common_button_buleshape);

        } else {
            bili.setText("+" + baseBean.getRiseRate());
            bili.setBackgroundResource( R.drawable.common_button_redshape);
            mExchangeType.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mSellRmb.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mRate.setTextColor(mContext.getResources().getColor(R.color.common_red));
        }
    }
    @Override
    public void process(int collection) {
        item =collection;
        if (collection == 0) {
            mToolbarRightBtn.setBackgroundResource(R.mipmap.img_collect);
        } else if (collection == 1) {
            mToolbarRightBtn.setBackgroundResource(R.mipmap.optional_select);
        }
    }
}
