package com.madaex.exchange.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.dhh.rxlife2.RxLife;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.BaseApplication;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.NoScrollViewPager;
import com.madaex.exchange.ui.adapter.TabLayoutFragmentAdapter;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.fragment.Deal2Fragment;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.fragment.FinanceFragment;
import com.madaex.exchange.ui.market.bean.FramnetBean;
import com.madaex.exchange.ui.market.fragment.MarketFragment;
import com.madaex.exchange.ui.mine.fragment.HomeFragment;
import com.madaex.exchange.ui.mine.fragment.MineFragment;
import com.madaex.exchange.websocket.RxWebSocket;
import com.madaex.exchange.websocket.WebSocketInfo;
import com.madaex.exchange.websocket.WebSocketSubscriber;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import okhttp3.WebSocket;

/**
 * 项目：  frame
 * 类名：  MainActivity.java
 * 时间：  2018/6/28 15:14
 * 描述：  ${TODO}
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private TabLayoutFragmentAdapter mAdapter;
    private int[] mTabImgs = new int[]{R.mipmap.icon_market_unselect, R.mipmap.icon_buy_unselect, R.mipmap.icon_sell_unselect, R.mipmap.icon_finance_unselect, R.mipmap.icon_mine_unselect};
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTabList = new ArrayList<>();
    private String one_xnb = "";
    private String two_xnb = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 1.使用getSupportFragmentManager().getFragments()获取到当前Activity中添加的Fragment集合
         * 2.遍历Fragment集合，手动调用在当前Activity中的Fragment中的onActivityResult()方法。
         */
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    Deal2Fragment deal2Fragment;

    @Override
    protected void initView() {
        int crruent = 0;
        if (getIntent().hasExtra("show")) {
            crruent = getIntent().getIntExtra("show", 0);
        }
        initTabList();
        mAdapter = new TabLayoutFragmentAdapter(getSupportFragmentManager(), mTabList, mContext, mFragments, mTabImgs);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(crruent);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(mAdapter.getTabView(i, crruent));
        }
        mTabLayout.addOnTabSelectedListener(this);
        initFragmentList();
    }

    public void initFragmentList() {
        mFragments.clear();
//        mFragments.add(MarketFragment.newInstance(getString(R.string.item_market)));
        mFragments.add(HomeFragment.newInstance(getString(R.string.item_market)));
        mFragments.add(MarketFragment.newInstance(getString(R.string.item_market)));
        deal2Fragment = Deal2Fragment.newInstance(0);
        mFragments.add(deal2Fragment);

        mFragments.add(FinanceFragment.newInstance(getString(R.string.item_finance)));
        mFragments.add(MineFragment.newInstance(getString(R.string.item_mine)));
    }

    private void initTabList() {
        mTabList.clear();
        mTabList.add(getString(R.string.item_home));
        mTabList.add(getString(R.string.item_market));
        mTabList.add(getString(R.string.item_onetran));

        mTabList.add(getString(R.string.item_finance2));
        mTabList.add(getString(R.string.item_mine));
        RxWebSocket.get(Constant.Websocket)
                .compose(RxLife.with(this).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onMessage(String text) {
                        Log.d("socket", "" + text);
                    }

                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        Log.d("MainActivity", "onOpen1:");
                        mWebSocket = webSocket;

                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连");
                    }
                });
        sendSocket();
    }

    @Override
    protected void initDatas() {
        one_xnb = SPUtils.getString(Constants.ONE_XNB, "BAT");
        two_xnb = SPUtils.getString(Constants.TWO_XNB, "ETH");

    }


    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtils.showToast(getString(R.string.exit_tips));
                return true;
            } else {
                BaseApplication.getInstance().AppExit(mContext);// 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabSelectedState(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabUnSelectedState(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        setTabSelectedState(tab);
    }

    private void setTabSelectedState(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        TextView tabText = (TextView) customView.findViewById(R.id.tv_tab_text);
        ImageView tabIcon = (ImageView) customView.findViewById(R.id.iv_tab_icon);
        tabText.setTextColor(ContextCompat.getColor(mContext, R.color.title_color));
        String s = tabText.getText().toString();
        if (getString(R.string.item_home).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_sell_select);
        } else if (getString(R.string.item_market).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_market_select);
        } else if (getString(R.string.item_onetran).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_buy_select);
        } else if (getString(R.string.item_finance2).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_finance_select);
        } else if (getString(R.string.item_mine).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_mine_select);
        }

    }

    private void setTabUnSelectedState(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        TextView tabText = (TextView) customView.findViewById(R.id.tv_tab_text);
        ImageView tabIcon = (ImageView) customView.findViewById(R.id.iv_tab_icon);
        tabText.setTextColor(ContextCompat.getColor(mContext, R.color.common_ggray));
        String s = tabText.getText().toString();
        if (getString(R.string.item_home).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_sell_unselect);
        } else if (getString(R.string.item_market).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_market_unselect);
        } else if (getString(R.string.item_onetran).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_buy_unselect);
        } else if (getString(R.string.item_finance2).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_finance_unselect);
        } else if (getString(R.string.item_mine).equals(s)) {
            tabIcon.setImageResource(R.mipmap.icon_mine_unselect);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.DEAL) {
            String coin = event.getMsg();
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];

        } else if (event != null && event.getCode() == Constants.fragmnet) {

            mViewPager.setCurrentItem(2);
            FramnetBean framnetBean = (FramnetBean) event.getData();
            SPUtils.putString(Constants.ONE_XNB, framnetBean.getOne_xnb());
            SPUtils.putString(Constants.TWO_XNB, framnetBean.getTwo_xnb());
            SPUtils.putString("market_type", framnetBean.getMarket_type() + "");
            deal2Fragment.setFragment(framnetBean);
        }
    }

    private void sendSocket() {

//        SocketBean socketBean = new SocketBean();
//        socketBean.setEvent("addChannel");
//        socketBean.setMarket_type("0");
//        socketBean.setChannel(one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depthxx");
//
//        SocketBean socketBean2 = new SocketBean();
//        socketBean2.setEvent("addChannel");
//        socketBean2.setMarket_type("0");
//        socketBean2.setChannel(one_xnb.toLowerCase() + two_xnb.toLowerCase()+ "_ticker");
//        Log.d("<==>", new Gson().toJson(socketBean));


    }

    private WebSocket mWebSocket;

    @Override
    public void onDestroy() {
        super.onDestroy();
        AllenVersionChecker.getInstance().cancelAllMission(this);
    }
}