package com.madaex.exchange.ui.buy.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.dhh.rxlife2.RxLife;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketInfo;
import com.dhh.websocket.WebSocketSubscriber;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.dialog.BaseNetDialogFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.bean.SocketBean;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.buy.contract.CoinLister;
import com.madaex.exchange.ui.buy.presenter.CoinPresenter;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.adapter.HistoryBuyAdapter;
import com.madaex.exchange.ui.market.bean.HistoryDetail;
import com.madaex.exchange.ui.market.bean.Home;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import okhttp3.WebSocket;

/**
 * 项目：  madaexchange
 * 类名：  CoinFrament.java
 * 时间：  2018/9/5 12:17
 * 描述：  ${TODO}
 */

public class HistoryRecordFrament extends BaseNetDialogFragment<CoinPresenter> implements CoinContract.View, CoinLister {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    HistoryBuyAdapter mBuyAdapter;
    ArrayList<HistoryDetail.DataBean> testBeans1 = new ArrayList<>();
    private String market_type = "0";

    public static HistoryRecordFrament newInstance(int type, String one_xnb, String two_xnb) {
        HistoryRecordFrament fragment = null;
        if (fragment == null) {
            fragment = new HistoryRecordFrament();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
//        bundle.putParcelable(Constants.ARGS, str);
        bundle.putString(Constants.ONE_XNB, one_xnb);
        bundle.putString(Constants.TWO_XNB, two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        String one_xnb = getArguments().getString(Constants.ONE_XNB);
        String two_xnb = getArguments().getString(Constants.TWO_XNB);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mBuyAdapter = new HistoryBuyAdapter(mContext);
        mRecyclerview.setAdapter(mBuyAdapter);
        mRecyclerview.setHasFixedSize(true);
        RxWebSocket.get(Constant.Websocket)
                .compose(RxLife.with(this).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        Log.d("HistoryDetailFragment", "onOpen1:");

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
        if (event != null && event.getCode() == Constants.DEAL) {
                String coin = event.getMsg();
                one_xnb = coin.split("/")[0];
                two_xnb = coin.split("/")[1];
                market_type= event.getHeyue();
                getData(one_xnb, two_xnb);

        }
    }
    @Override
    protected void initDatas() {
        String one_xnb = getArguments().getString("one_xnb");
        String two_xnb = getArguments().getString("two_xnb");
        market_type =getActivity().getIntent().getStringExtra("market_type");
        getData(one_xnb, two_xnb);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDatePickerDialog);
    }
    private String one_xnb = "";
    private String two_xnb = "";
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 5;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window dialogWindow = getDialog().getWindow();

//        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);

    }

    @Override
    public void requestSuccess(String msg) {

    }


    @Override
    public void requestError(String msg) {

    }

    @Override
    public void sendMsgSuccess(CoinList data) {
    }

    @Override
    public void sendDealSuccess(String msg) {

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {

    }

    @Override
    public void requestToken(String baseBean) {

    }


    @Override
    public void sendMsgSuccess(DealInfo msg) {

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

    @OnClick(R.id.shouqi)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void inputInforCompleted(String string) {
        dismiss();
    }

}
