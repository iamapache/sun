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
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.bean.SocketBean;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.adapter.HistoryBuyAdapter;
import com.madaex.exchange.ui.market.bean.CoinDetail;
import com.madaex.exchange.ui.market.bean.HistoryDetail;
import com.madaex.exchange.ui.market.contract.CoinDetailContract;
import com.madaex.exchange.ui.market.presenter.CoinDetailPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

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

public class HistoryDetailFragment extends BaseNetLazyFragment<CoinDetailPresenter> implements CoinDetailContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    HistoryBuyAdapter mBuyAdapter;
    ArrayList<HistoryDetail.DataBean> testBeans1 = new ArrayList<>();
    private String market_type = "0";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_historydetail;
    }

    public static HistoryDetailFragment newInstance(String one_xnb,String two_xnb) {
        HistoryDetailFragment fragment = null;
        if (fragment == null) {
            fragment = new HistoryDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("one_xnb", one_xnb);
        bundle.putString("two_xnb", two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {getFragmentComponent().inject(this);
    }

    private WebSocket mWebSocket;
    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mBuyAdapter = new HistoryBuyAdapter(mContext);
        mRecyclerview.setAdapter(mBuyAdapter);
        mRecyclerview.setHasFixedSize(true);
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
                        Log.d("MainActivity", message);
                        if (TextUtils.isEmpty(message)||message.equals("hello")) {
                            return;
                        }
                        if (currentBackPressedTime < BACK_PRESSED_INTERVAL) {
                            currentBackPressedTime++;
                        } else {
                            currentBackPressedTime = 0;
                            HistoryDetail mDesignates = JSON.parseObject(message, HistoryDetail.class);
                            if (mDesignates != null && channel.equals(mDesignates.getChannel())) {
                                mBuyAdapter.setNewData(mDesignates.getData());
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
        market_type =getActivity().getIntent().getStringExtra("market_type");
        getData(one_xnb, two_xnb);
    }


    private String channel="";
    private void getData( String one_xnb, String two_xnb) {
        SocketBean socketBean = new SocketBean();
        socketBean.setEvent("addChannel");
        socketBean.setMarket_type(market_type);
        socketBean.setChannel(one_xnb.toLowerCase()+two_xnb.toLowerCase()+"_trades");
        Log.d("<==>",new Gson().toJson(socketBean));
        channel = one_xnb.toLowerCase()+two_xnb.toLowerCase()+"_trades";
        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));

//        TreeMap params = new TreeMap<>();
//        params.put("act", ConstantUrl.TRADE_coin_deal_list);
//        params.put("one_xnb", getArguments().getString("one_xnb"));
//        params.put("two_xnb", getArguments().getString("two_xnb"));
//        params.put("market_type", market_type);
//        mPresenter.HistoryDetail(DataUtil.sign(params));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.MARK) {
            if(isVisible==false&&isPrepared==false){
                String coin = event.getMsg();
                 one_xnb = coin.split("/")[0];
                 two_xnb = coin.split("/")[1];
                market_type= event.getHeyue();
                getData(one_xnb, two_xnb);
            }

        }else if (event != null && event.getCode() == Constants.change) {
                String coin = event.getMsg();
                one_xnb = coin.split("/")[0];
                two_xnb = coin.split("/")[1];
                getData(one_xnb, two_xnb);
        }
    }
    private String one_xnb = "";
    private String two_xnb = "";
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 5;

    @Override
    public void requestSuccess(CoinDetail bean) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(HistoryDetail commonBean) {

    }
}
