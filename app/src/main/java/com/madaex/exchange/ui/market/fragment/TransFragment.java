package com.madaex.exchange.ui.market.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.ui.buy.bean.DealBean;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.bean.SocketBean;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.adapter.TransBuyAdapter;
import com.madaex.exchange.ui.market.adapter.TransSellerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.WebSocket;

/**
 * 项目：  madaexchange
 * 类名：  CoinDetailFragment.java
 * 时间：  2018/9/14 17:11
 * 描述：  ${TODO}
 */

public class TransFragment extends BaseNetLazyFragment {
    @BindView(R.id.buyrecyclerview)
    RecyclerView mBuyrecyclerview;
    @BindView(R.id.sellerrecyclerview)
    RecyclerView mSellerrecyclerview;
    Unbinder unbinder;

    TransBuyAdapter mBuyAdapter;

    TransSellerAdapter mSellerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trans;
    }

    public static TransFragment newInstance(String one_xnb, String two_xnb) {
        TransFragment fragment = null;
        if (fragment == null) {
            fragment = new TransFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("one_xnb", one_xnb);
        bundle.putString("two_xnb", two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
    }
    private WebSocket mWebSocket;
    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBuyrecyclerview.setLayoutManager(linearLayoutManager);
        mBuyAdapter = new TransBuyAdapter(mContext);
        mBuyrecyclerview.setAdapter(mBuyAdapter);
        mBuyrecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mSellerrecyclerview.setLayoutManager(linearLayoutManager1);
        mSellerAdapter = new TransSellerAdapter(mContext);
        mSellerrecyclerview.setAdapter(mSellerAdapter);
        mSellerrecyclerview.setHasFixedSize(true);
        RxWebSocket.get(Constant.Websocket)
                .subscribeOn(Schedulers.io())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        Log.d("HistoryDetailFragment", "onOpen1:");
                        mWebSocket = webSocket;

                    }
                    @Override
                    protected void onMessage(String message) {
                        if (TextUtils.isEmpty(message)) {
                            return;
                        }

                        if (currentBackPressedTime < BACK_PRESSED_INTERVAL) {
                            currentBackPressedTime++;
                        } else {
                            currentBackPressedTime = 0;
                            DealBean mDesignates = JSON.parseObject(message, DealBean.class);
                            if (mBuyAdapter != null && mSellerAdapter != null) {
                                if (mDesignates != null && channel.equals(mDesignates.getChannel())) {
                                    if (mDesignates.getAsks().size() >= 20) {
                                        mBuyAdapter.setNewData(mDesignates.getAsks().subList(0, 20));
                                    }
                                    if (mDesignates.getBids().size() >= 20) {
                                        mSellerAdapter.setNewData(mDesignates.getBids().subList(0, 20));
                                    }

                                }
                            }
                        }
                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连");
                    }
                });
    }


    @Override
    protected void initDatas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        String one_xnb = getArguments().getString("one_xnb");
        String two_xnb = getArguments().getString("two_xnb");
        getData(one_xnb, two_xnb);
    }
    private String channel = "";

    private void getData(String one_xnb, String two_xnb) {
        SocketBean socketBean = new SocketBean();
        socketBean.setEvent("addChannel");
        socketBean.setChannel(one_xnb.toLowerCase() + "qc" + "_depth");
        Log.d("<==>", new Gson().toJson(socketBean));
        channel = one_xnb.toLowerCase() + "qc" + "_depth";
        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.MARK) {
            if (isVisible == false && isPrepared == false) {
                String coin = event.getMsg();
                String one_xnb = coin.split("/")[0];
                String two_xnb = coin.split("/")[1];
                getData(one_xnb, two_xnb);
            }

        }else if (event != null && event.getCode() == Constants.change) {
                String coin = event.getMsg();
                String one_xnb = coin.split("/")[0];
                String two_xnb = coin.split("/")[1];
                getData(one_xnb, two_xnb);
        }
    }

    private String one_xnb = "";
    private String two_xnb = "";
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 14;

}
