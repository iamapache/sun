package com.madaex.exchange.ui.buy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.AppUtils;
import com.madaex.exchange.common.util.ArithUtil;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.ui.buy.adapter.BuyAdapter;
import com.madaex.exchange.ui.buy.adapter.DepthDataBean;
import com.madaex.exchange.ui.buy.adapter.DepthMapView;
import com.madaex.exchange.ui.buy.adapter.SellerAdapter;
import com.madaex.exchange.ui.buy.bean.DealBean2;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.bean.SocketBean;
import com.madaex.exchange.ui.buy.contract.CoinLister;
import com.madaex.exchange.ui.buy.contract.DealContract;
import com.madaex.exchange.ui.buy.presenter.DealPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.login.activity.LoginActivity;
import com.madaex.exchange.ui.market.bean.FramnetBean;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;
import com.madaex.exchange.view.PayPassDialog;
import com.madaex.exchange.view.PayPassView;
import com.orhanobut.logger.Logger;
import com.zhangke.websocket.SimpleListener;
import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.response.ErrorResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.WebSocket;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class Buy2Fragment extends BaseNetLazyFragment<DealPresenter> implements DealContract.View, CoinLister, Deal2Fragment.DEtailLister {
    @BindView(R.id.tv_deal)
    TextView mTvDeal;
    Unbinder unbinder;
    @BindView(R.id.buyrecyclerview)
    NoConflictRecyclerView mBuyrecyclerview;
    @BindView(R.id.sellerrecyclerview)
    NoConflictRecyclerView mSellerrecyclerview;
    @BindView(R.id.last)
    TextView mLast;
    @BindView(R.id.coinname)
    TextView mCoinname;
    @BindView(R.id.change)
    ImageView mChange;
    @BindView(R.id.infomation)
    TextView mInfomation;
    @BindView(R.id.dv_depth)
    DepthMapView depthView;
    @BindView(R.id.bili)
    TextView mBili;

    @BindView(R.id.tabs_rg)
    RadioGroup mTabRadioGroup;

    @BindView(R.id.baibili)
    TextView baibili;
    @BindView(R.id.price)
    EditText mPrice;
    @BindView(R.id.number)
    EditText mNumber;
    @BindView(R.id.cny)
    TextView mCny;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.guzhi)
    TextView mGuzhi;
    @BindView(R.id.keyong)
    TextView mKeyong;
    @BindView(R.id.name2)
    TextView mName2;
    @BindView(R.id.today_tab)
    RadioButton mTodayTab;
    @BindView(R.id.record_tab)
    RadioButton mRecordTab;
    @BindView(R.id.contact_tab)
    RadioButton mContactTab;
    @BindView(R.id.settings_tab)
    RadioButton mSettingsTab;
    @BindView(R.id.deletetwo)
    TextView mDeletetwo;
    @BindView(R.id.addtwo)
    TextView mAddtwo;
    @BindView(R.id.deleteone)
    TextView mDeleteone;
    @BindView(R.id.addone)
    TextView mAddone;
    private String type;
    private String one_xnb = "";
    private String two_xnb = "";
    private CustomPopWindow mCustomPopWindow;
    @BindView(R.id.ll_gears)
    LinearLayout mLlGears;
    @BindView(R.id.ll_depth)
    LinearLayout mLlDepth;
    @BindView(R.id.tv_gears)
    TextView mTvGears;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    BuyAdapter mBuyAdapter;

    SellerAdapter mSellerAdapter;
    String str = "";
    Home baseBean = new Home();
    private boolean refresh = false;

    public static Buy2Fragment newInstance(String string, String one_xnb, String two_xnb) {
        Buy2Fragment fragment = null;
        if (fragment == null) {
            fragment = new Buy2Fragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        bundle.putString(Constants.ONE_XNB, one_xnb);
        bundle.putString(Constants.TWO_XNB, two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SocketListener socketListener = new SimpleListener() {
        @Override
        public void onConnected() {
            Log.v("==", "onConnected");
        }

        @Override
        public void onConnectFailed(Throwable e) {
            if (e != null) {
                Log.v("==", "onConnectFailed:" + e.toString());
            } else {
                Log.v("==", "onConnectFailed:null");
            }
        }

        @Override
        public void onDisconnect() {
            Log.v("==", "onDisconnect");
//            sendSocket();
        }

        @Override
        public void onSendDataError(ErrorResponse errorResponse) {
            errorResponse.release();
        }

        @Override
        public <T> void onMessage(String message, T data) {
            Log.v("==", "onDisconnect" + message);
            if (TextUtils.isEmpty(message) || message.equals("hello")) {
//                sendSocket();
                return;
            }
            DealBean2 mDesignates = JSON.parseObject(message, DealBean2.class);
            Message messages = Message.obtain();
            messages.obj = mDesignates;
            messages.what = 1;
            handler.sendMessage(messages);//将message对象发送出去
        }

        @Override
        public <T> void onMessage(ByteBuffer bytes, T data) {
            Log.v("==", "onMessage(ByteBuffer, T):" + bytes);
        }
    };

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;

        initview();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBuyrecyclerview.setLayoutManager(linearLayoutManager);

        mBuyAdapter = new BuyAdapter(mContext);
        mBuyrecyclerview.setAdapter(mBuyAdapter);
        mBuyrecyclerview.setHasFixedSize(true);
        mBuyAdapter.setItemClickListener(new BuyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<BigDecimal> list) {
                if (market_type.equals("0")) {
                    mPrice.setText(list.get(0).toPlainString());
                } else if (EmptyUtils.isNotEmpty(mPrice.getText().toString())){
                    if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                        if (buy_price_lock == 1) {

                        } else {
                            mPrice.setText(list.get(0).toPlainString());
                        }
                    } else {
                        if (sell_price_lock == 1) {
                        } else {
                            mPrice.setText(list.get(0).toPlainString());
                        }
                    }

                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mSellerrecyclerview.setLayoutManager(linearLayoutManager1);
        mSellerAdapter = new SellerAdapter(mContext);
        mSellerrecyclerview.setAdapter(mSellerAdapter);
        mSellerrecyclerview.setHasFixedSize(true);
        mSellerAdapter.setItemClickListener(new SellerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<BigDecimal> list) {
                if (market_type.equals("0")) {
                    mPrice.setText(list.get(0).toPlainString());
                } else if (EmptyUtils.isNotEmpty(mPrice.getText().toString())) {
                    if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                        if (buy_price_lock == 1) {

                        } else {
                            mPrice.setText(list.get(0).toPlainString());
                        }
                    } else {
                        if (sell_price_lock == 1) {
                        } else {
                            mPrice.setText(list.get(0).toPlainString());
                        }
                    }
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });


        mAddone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    if (market_type.equals("0")) {
                        mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(mPrice.getText().toString()) + Double.valueOf(rise_once))) + "");
                    } else if (EmptyUtils.isNotEmpty(mPrice.getText().toString())){
                        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                            if (buy_price_lock == 1) {
                                mPrice.setEnabled(false);
                            } else {
                                mPrice.setEnabled(true);
                                mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(mPrice.getText().toString()) + Double.valueOf(rise_once))) + "");

                            }
                        } else {
                            if (sell_price_lock == 1) {
                                mPrice.setEnabled(false);
                            } else {
                                mPrice.setEnabled(true);
                                mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(mPrice.getText().toString()) + Double.valueOf(rise_once))) + "");
                            }
                        }

                    }

                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });

        mDeleteone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    if (market_type.equals("0")) {
                        mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(mPrice.getText().toString()) - Double.valueOf(rise_once))) + "");
                    } else if (EmptyUtils.isNotEmpty(mPrice.getText().toString())){
                        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                            if (buy_price_lock == 1) {
                                mPrice.setEnabled(false);
                            } else {
                                mPrice.setEnabled(true);
                                mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(mPrice.getText().toString()) - Double.valueOf(rise_once))) + "");

                            }
                        } else {
                            if (sell_price_lock == 1) {
                                mPrice.setEnabled(false);
                            } else {
                                mPrice.setEnabled(true);
                                mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(mPrice.getText().toString()) - Double.valueOf(rise_once))) + "");
                            }
                        }
                    }
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });

        mAddtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    if (EmptyUtils.isNotEmpty(mNumber.getText().toString()) && strResult(mNumber.getText().toString())) {
                        mNumber.setText((Double.valueOf(mNumber.getText().toString()) + 1) + "");
                    } else if (EmptyUtils.isNotEmpty(mNumber.getText().toString())) {
                        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                            if (buy_num_lock == 1) {
                            } else {
                                mNumber.setText((Double.valueOf(mNumber.getText().toString()) + 1) + "");
                            }
                        } else {
                            if (sell_num_lock == 1) {
                            } else {
                                mNumber.setText((Double.valueOf(mNumber.getText().toString()) + 1) + "");
                            }
                        }

                    }

                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });


        mDeletetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    if (EmptyUtils.isNotEmpty(mNumber.getText().toString()) && strResult(mNumber.getText().toString())) {
                        mNumber.setText((Double.valueOf(mNumber.getText().toString()) - 1) + "");
                    }
                    if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                        if (buy_num_lock == 1) {
                        } else {
                            mNumber.setText((Double.valueOf(mNumber.getText().toString()) - 1) + "");
                        }
                    } else {
                        if (sell_num_lock == 1) {
                        } else {
                            mNumber.setText((Double.valueOf(mNumber.getText().toString()) - 1) + "");
                        }
                    }
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            }
        });

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                cacelSocket2();
                linedetail();
                if(mRefreshLister!=null){
                    mRefreshLister.Refresh("");
                }
            }
        });
    }
    RefreshLister mRefreshLister;
    public interface RefreshLister {
        void Refresh(String string);
    }

    public RefreshLister getRefreshLister() {
        return mRefreshLister;
    }

    public void setRefreshLister(RefreshLister refreshLister) {
        mRefreshLister = refreshLister;
    }

    public void refresh() {
        onCreate(null);
        mSwiperefreshlayout.setRefreshing(false);
    }

    private List<DepthDataBean> getBuyDepthList2(List<List<Float>> lists) {
        Log.v("depthList", lists.size() + "");
        List<DepthDataBean> depthList = new ArrayList<>();
        try {
            if (lists.size() >= 50) {
                List<List<Float>> listList = lists.subList(0, 50);

                double account = 0;
                for (int i = 0; i < listList.size(); i++) {
                    account = account + listList.get(i).get(0);
                    depthList.add(new DepthDataBean(listList.get(i).get(0),
                            listList.get(i).get(1)));
                }
            } else {
                for (int i = 0; i < lists.size(); i++) {
                    depthList.add(new DepthDataBean(lists.get(i).get(0),
                            lists.get(i).get(1)));
                }
            }

            return depthList;
        } catch (Exception e) {
            return depthList;
        }
    }

    public int bs(double a, double b) {
        try {
            if (b == 0) {
                return 0;
            } else {
                return (int) ((new BigDecimal((float) a / b).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private List<DepthDataBean> getBuyDepthList3(List<List<Float>> buy, List<List<Float>> sell) {
        List<DepthDataBean> depthList = new ArrayList<>();
        try {

            if (buy.size() >= 50 && sell.size() >= 50) {
                List<List<Float>> listList = buy.subList(0, 49);
                List<List<Float>> listList2 = sell.subList(0, 49);
                double account = 0;
                double account2 = 0;
                if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                    for (int i = 0; i < listList.size(); i++) {
                        account = account + listList.get(i).get(0);
                        account2 = account2 + listList.get(i).get(0);

                    }
                    for (int j = 0; j < listList2.size(); j++) {
                        account = account + listList2.get(j).get(0);
                    }
                    mBili.setText(bs(account2, account) + "%");
                } else {
                    for (int i = 0; i < listList.size(); i++) {
                        account = account + listList.get(i).get(0);
                    }
                    for (int j = 0; j < listList2.size(); j++) {
                        account = account + listList2.get(j).get(0);
                        account2 = account2 + listList2.get(j).get(0);
                    }
                    mBili.setText(bs(account2, account) + "%");
                }

            } else {
                double account = 0;
                double account2 = 0;
                if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                    for (int i = 0; i < buy.size(); i++) {
                        account = account + buy.get(i).get(0);
                        account2 = account2 + buy.get(i).get(0);

                    }
                    for (int j = 0; j < sell.size(); j++) {
                        account = account + sell.get(j).get(0);
                    }
                    mBili.setText(bs(account2, account) + "%");
                } else {
                    for (int i = 0; i < buy.size(); i++) {
                        account = account + buy.get(i).get(0);
                    }
                    for (int j = 0; j < sell.size(); j++) {
                        account = account + sell.get(j).get(0);
                        account2 = account2 + sell.get(j).get(0);
                    }
                    mBili.setText(bs(account2, account) + "%");
                }
            }
            return depthList;
        } catch (Exception e) {
            return depthList;
        }
    }

    private List<DepthDataBean> getSellDepthList2(List<List<Float>> lists) {
        List<DepthDataBean> depthList = new ArrayList<>();
        try {
            if (lists.size() >= 50) {
                List<List<Float>> listList = lists.subList(0, 50);
                Log.v("depthList", listList.size() + "");
                for (int i = 0; i < listList.size(); i++) {
                    depthList.add(new DepthDataBean(listList.get(i).get(0),
                            listList.get(i).get(1)));
                }
            } else {
                for (int i = 0; i < lists.size(); i++) {
                    depthList.add(new DepthDataBean(lists.get(i).get(0),
                            lists.get(i).get(1)));
                }
            }

            return depthList;
        } catch (Exception e) {
            return depthList;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WebSocketHandler.getDefault().removeListener(socketListener);
    }

    private WebSocket mWebSocket;
    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 3;
    private String channel = "";
    private String channel2;


    private void sendSocket() {

        market_type = SPUtils.getString("market_type", "0");
        SocketBean socketBean = new SocketBean();
        socketBean.setEvent("addChannel");
        socketBean.setMarket_type(market_type);
        socketBean.setChannel(one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth");

        SocketBean socketBean2 = new SocketBean();
        socketBean2.setEvent("addChannel");
        socketBean2.setMarket_type(market_type);
        socketBean2.setChannel(one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_ticker");
        Log.d("<==>", new Gson().toJson(socketBean));
//        mServerConnection.sendMessage(new Gson().toJson(socketBean));
        channel = one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth";
        channel2 = one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_ticker";
        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
            WebSocketHandler.getDefault().send(new Gson().toJson(socketBean));
        }
//        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));
//        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean2));

    }

    private void linedetail() {
        TreeMap params2 = new TreeMap<>();
        params2.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL);
        params2.put("market", one_xnb + "_" + two_xnb);
        params2.put("market_type", market_type);
        mPresenter.getJavaLineDetail(DataUtil.sign(params2));

//        Observable<String> user = new GitHubServiceManager()
//                .getTestResult2(DataUtil.sign(params2));
//        //缓存配置
//        CacheProviders.getUserCache()
//                .getTestResult2(user, new DynamicKey(33), new EvictDynamicKey(false))//用户名作为动态key生成不同文件存储数据，默认不清除缓存数据
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String data) {
//                        Gson gson = new Gson();
//                        LineDetail commonBean = gson.fromJson(data, LineDetail.class);
//                        srtData(commonBean.getData());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    void srtData(Home baseBean) {
        if (EmptyUtils.isNotEmpty(baseBean) && EmptyUtils.isNotEmpty(baseBean.getCurrentPrice())) {
            this.baseBean = baseBean;
            mCoinname.setText(baseBean.getRiseRate());
            baibili.setText("￥" + baseBean.getSellRmb());
            mGuzhi.setText(baseBean.getSellRmb().toString());
            mPrice.setText(baseBean.getCurrentPrice());
            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                mLast.setText((Double.valueOf(baseBean.getCurrentPrice().toString() + Double.valueOf(rise_once))) + "");
            } else {
                mLast.setText((Double.valueOf(baseBean.getCurrentPrice().toString() + Double.valueOf(rise_once))) + "");
            }
            if (baseBean.getRiseRate().contains("-")) {
                mLast.setTextColor(mContext.getResources().getColor(R.color.common_green));
                mCoinname.setTextColor(mContext.getResources().getColor(R.color.common_green));
            } else {
                mLast.setTextColor(mContext.getResources().getColor(R.color.common_red));
                mCoinname.setTextColor(mContext.getResources().getColor(R.color.common_red));
            }
//            getMsgInfo();
        }
    }


    private void initview() {
        market_type = SPUtils.getString("market_type", "0");

        mName.setText(two_xnb);

        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
            mName2.setText(two_xnb);
            mTvDeal.setBackgroundResource(R.drawable.common_bg_green_corners);
            mTvDeal.setText(getString(R.string.item_buy) + " " + one_xnb);
            mTvDeal.setTextColor(getResources().getColor(R.color.white));
            if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                mTvDeal.setText(R.string.loginregister);
            }
//            mTvOne.setText(getString(R.string.Available) + " " + two_xnb);
//            mTvTwo.setText(getString(R.string.kebuy) + " " + one_xnb);
//            mTvThree.setText(getString(R.string.Available) + " " + one_xnb);
//            mTvFour.setText(getString(R.string.Frozen) + " " + two_xnb);
        } else {
            mName2.setText(one_xnb);

            mTvDeal.setBackgroundResource(R.drawable.common_bg_red_corners);
            mTvDeal.setText(getString(R.string.item_seller) + " " + one_xnb);
            mTvDeal.setTextColor(getResources().getColor(R.color.white));
            if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                mTvDeal.setText(R.string.loginregister);
            }
//            mTvOne.setText(getString(R.string.Available) + " " + one_xnb);
//
//            mTvTwo.setText(getString(R.string.exchange) + " " +two_xnb  + "");
//            mTvThree.setText(getString(R.string.Available) + " " + two_xnb);
//            mTvFour.setText(getString(R.string.Frozen) + " " + one_xnb);
        }


    }

    private void getMsgInfo() {
        Logger.i("<==>:market_typemarket_typemarket_typemarket_type" + market_type);
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            if (market_type.equals("0")) {
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.TRADE_TRADE);
                params.put("one_xnb", one_xnb);
                params.put("two_xnb", two_xnb);
                mPresenter.getMsgInfo(DataUtil.sign(params));
            } else {
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.Contract_contractAssets);
                params.put("one_xnb", one_xnb);
                params.put("two_xnb", two_xnb);
                mPresenter.getMsgInfo(DataUtil.sign(params));
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy2;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {

        mPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    mCny.setText("￥0");
                    return;
                }
                str = editable.toString();
                if (!TextUtils.isEmpty(mNumber.getText().toString().trim()) && !TextUtils.isEmpty(editable.toString())) {
                    if (!mPrice.getText().toString().trim().equals("0.") && !editable.toString().equals("0.")) {
                        double number = Double.valueOf(mNumber.getText().toString().trim());
                        double price = Double.valueOf(editable.toString());
                        double key = Double.valueOf(mKeyong.getText().toString().trim());
                        mCny.setText(new DecimalFormat("0.0000").format(ArithUtil.round(ArithUtil.mul(number, price), 4) ));
                        mNumber.setText(new DecimalFormat("0.0000").format(ArithUtil.round(ArithUtil.div(key, price), 4)));
//                        mOrderBuyFee.setText((ArithUtil.round(ArithUtil.mul(number, price) * 0.003, 2) + ""));
                    }
                }

//                if (!TextUtils.isEmpty(editable.toString())) {
//                    if (!editable.toString().equals("0.") && Double.valueOf(editable.toString()) != 0) {
////                        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
////                            mOneXnbd.setText(new BigDecimal(ArithUtil.div(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(editable.toString()), 2) + "").stripTrailingZeros().toPlainString());
////                        } else {
////                            mOneXnbd.setText(new BigDecimal(ArithUtil.round(ArithUtil.mul(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(editable.toString())), 2) + "").stripTrailingZeros().toPlainString());
////                        }
//                        if (baseBean != null && baseBean.getSellRmb() != null && baseBean.getCurrentPrice() != null && Double.valueOf(baseBean.getCurrentPrice()) != 0) {
//                            mCny.setText("￥" + new BigDecimal(ArithUtil.round(ArithUtil.mul(ArithUtil.div(Double.valueOf(baseBean.getSellRmb()),
//                                    Double.valueOf(baseBean.getCurrentPrice()), 2), Double.valueOf(editable.toString())), 2) + "").stripTrailingZeros().toPlainString());
//                        }
//                    }
//                }
            }
        });


        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(mPrice.getText().toString().trim()) && !TextUtils.isEmpty(editable.toString())) {
                    if (!mPrice.getText().toString().trim().equals("0.") && !editable.toString().equals("0.") && !editable.toString().equals(".")) {
                        double number = Double.valueOf(mPrice.getText().toString().trim());
                        double price = Double.valueOf(editable.toString());
                        double key = Double.valueOf(mKeyong.getText().toString().trim());
                        mCny.setText(new DecimalFormat("0.0000").format(ArithUtil.round(ArithUtil.mul(number, price), 4)));
//                        mNumber.setText(ArithUtil.round(ArithUtil.div(key, number), 4) + "");
//                        mOrderBuyFee.setText((ArithUtil.round(ArithUtil.mul(number, price) * 0.003, 2) + ""));
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBuyrecyclerview.setLayoutManager(linearLayoutManager);

//        mBuyAdapter = new BuyAdapter(mContext);
//        mBuyrecyclerview.setAdapter(mBuyAdapter);
//        mBuyrecyclerview.setHasFixedSize(true);
//        mBuyAdapter.setItemClickListener(new BuyAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(List<BigDecimal> list) {
//                    mPrice.setText(list.get(0).toString());
//            }
//
//            @Override
//            public void onItemLongClick(View view) {
//
//            }
//        });
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
//        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
//        mSellerrecyclerview.setLayoutManager(linearLayoutManager1);
//        mSellerAdapter = new SellerAdapter(mContext);
//        mSellerrecyclerview.setAdapter(mSellerAdapter);
//        mSellerrecyclerview.setHasFixedSize(true);
//        mSellerAdapter.setItemClickListener(new SellerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(List<Float> list) {
//                if (market_type.equals("0")) {
//                    mPrice.setText(list.get(0).toString());
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onItemLongClick(View view) {
//
//            }
//        });
////        mSellerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                List<String> list = (List<String>) adapter.getItem(position);
////                mEtPrice.setText(list.get(0).toString());
////            }
////        });
//        RxWebSocket.get(Constant.Websocket)
//                .compose(RxLife.with(this).<WebSocketInfo>bindToLifecycle())
//                .subscribe(new WebSocketSubscriber() {
//                    @Override
//                    public void onOpen(@NonNull WebSocket webSocket) {
//                        Log.d("MainActivity", "onOpen1:");
//                        mWebSocket = webSocket;
//                    }
//
//                    @Override
//                    protected void onMessage(String message) {
//
//                        if (TextUtils.isEmpty(message) || message.equals("hello")) {
//                            return;
//                        }
//                        Log.i("BuyFragment", message.substring(message.length() - 100));
//                        if (currentBackPressedTime < BACK_PRESSED_INTERVAL) {
//                            currentBackPressedTime++;
//                        } else {
//                            currentBackPressedTime = 0;
//                            new Thread(new Runnable() {
//
//                                @Override
//                                public void run() {
//                                    DealBean2 mDesignates = JSON.parseObject(message, DealBean2.class);
//                                    Message messages = Message.obtain();
//                                    messages.obj = mDesignates;
//                                    messages.what = 1;
//                                    handler.sendMessage(messages);//将message对象发送出去
//                                }
//                            }).start();
//
//                        }
//                    }
//
//                    @Override
//                    protected void onReconnect() {
//                        Log.d("MainActivity", "重连");
//                    }
//                });
//
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                for (int i = 0; i < group.getChildCount(); i++) {
//                    if (group.getChildAt(i).getId() == checkedId) {
//                        RadioButton radioButton = (RadioButton) group.getChildAt(i);
//                        ToastUtils.showToast(radioButton.getText().toString());
//                        return;
//                    }
//                }
                switch (checkedId) {
                    case R.id.today_tab:
                        if (EmptyUtils.isNotEmpty(mPrice.getText().toString())) {
                            double number = Double.valueOf(total);
                            double price = Double.valueOf(mPrice.getText().toString().trim());
                            String resa = "0";
                            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(totalnum, 0.25)) + "";
                                mNumber.setText(resa);
                            } else {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(number, 0.25)) + "";
                                mNumber.setText(resa);
                            }
                            Logger.i("<==>:CCC" + ArithUtil.div(ArithUtil.div(number, price), 0.25, 3) + "");
                            Logger.i("<==>:CCC" + ArithUtil.div(number, 0.25, 3) + "");
                        }

                        break;
                    case R.id.record_tab:
                        if (EmptyUtils.isNotEmpty(mPrice.getText().toString())) {
                            double number = Double.valueOf(total);
                            double price = Double.valueOf(mPrice.getText().toString().trim());
                            String resa = "0";
                            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(totalnum, 0.5)) + "";
                                mNumber.setText(resa);
                            } else {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(number, 0.5)) + "";
                                mNumber.setText(resa);
                            }
                            Logger.i("<==>:" + ArithUtil.div(ArithUtil.div(number, price), 0.5, 3) + "");
                            Logger.i("<==>:" + ArithUtil.div(number, 0.5, 3) + "");
                        }
                        break;
                    case R.id.contact_tab:
                        if (EmptyUtils.isNotEmpty(mPrice.getText().toString())) {
                            double number = Double.valueOf(total);
                            double price = Double.valueOf(mPrice.getText().toString().trim());
                            String resa = "0";
                            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(totalnum, 0.75)) + "";
                                mNumber.setText(resa);
                            } else {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(number, 0.75)) + "";
                                mNumber.setText(resa);
                            }
                        }
                        break;
                    case R.id.settings_tab:
                        if (EmptyUtils.isNotEmpty(mPrice.getText().toString())) {
                            double number = Double.valueOf(total);
                            double price = Double.valueOf(mPrice.getText().toString().trim());
                            String resa = "0";
                            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(totalnum, 1)) + "";
                                mNumber.setText(resa);
                            } else {
                                resa = new DecimalFormat("0.0000").format(ArithUtil.mul(number, 1)) + "";
                                mNumber.setText(resa);
                            }
                            Logger.i("<==>:CCC" + totalnum);
                            Logger.i("<==>:CCC" + resa);
                        }
                        break;
                }
            }
        });
    }


    private String rise_once = "0";
    private String trade_sell_fee = "0";
    private String trade_buy_fee = "0";
    private double totalnum = 0;

    @Override
    protected void initDatas() {

        type = getArguments().getString(Constants.ARGS);
        one_xnb = getArguments().getString(Constants.ONE_XNB);
        two_xnb = getArguments().getString(Constants.TWO_XNB);

        if (one_xnb.equals("SNRC")) {
            mInfomation.setVisibility(View.VISIBLE);
        } else {
            mInfomation.setVisibility(View.GONE);
        }
//        mPrice.setFilters(new InputFilter[]{new EditInputFilter(500000)});
//        mNumber.setFilters(new InputFilter[]{new EditInputFilter(1000000)});
//        Observable.interval(0, 60, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.bindToLifecycle())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        initview();
//        linedetail();
        WebSocketHandler.getDefault().addListener(socketListener);

        sendSocket();


//        getMsgInfo();
    }

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 3000L;

    private void submit() {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            if (TextUtils.isEmpty(mPrice.getText().toString().trim())) {
                ToastUtils.showToast(getString(R.string.entryvoteprice));
                return;
            }
            if (TextUtils.isEmpty(mNumber.getText().toString().trim())) {
                ToastUtils.showToast(getString(R.string.entryvotenumber));
                return;
            }
            long nowTime = System.currentTimeMillis();
            if (nowTime - mLastClickTime > TIME_INTERVAL) {
                // do something
                if (market_type.equals("0")) {
                    payDialog();

                } else {
//                    TreeMap params = new TreeMap<>();
//                    params.put("act", ConstantUrl.User_form_token);
//                    mPresenter.getToken(DataUtil.sign(params));
                    if (market_type.equals("0")) {
                        TreeMap params = new TreeMap<>();
                        params.put("act", ConstantUrl.TRADE_UPTRADE);
                        params.put("market", one_xnb + "_" + two_xnb);
                        params.put("price", mPrice.getText().toString().trim());
                        params.put("num", mNumber.getText().toString().trim());
                        params.put("type", type);
                        params.put("paypassword", passContents);
                        params.put("source", "android");
//                        params.put("__token__", baseBean);
                        mPresenter.getData(DataUtil.sign(params));
                    } else {
                        TreeMap params = new TreeMap<>();
                        params.put("act", ConstantUrl.Contract_upContract);
                        params.put("market", one_xnb + "_" + two_xnb);
                        params.put("price", mPrice.getText().toString().trim());
                        params.put("num", mNumber.getText().toString().trim());
                        params.put("type", type);
//            params.put("paypassword", passContents);
                        params.put("source", "android");
//                        params.put("__token__", baseBean);
                        mPresenter.getData(DataUtil.sign(params));
                    }
                }
                mLastClickTime = nowTime;
            } else {
//                Toast.makeText(mContext, "不要重复点击", Toast.LENGTH_SHORT).show();
            }


        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }

    }


    PayPassDialog dialog;
    String passContents = "";

    private void payDialog() {
        dialog = new PayPassDialog(mContext);
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        if (market_type.equals("0")) {
                            TreeMap params = new TreeMap<>();
                            params.put("act", ConstantUrl.TRADE_UPTRADE);
                            params.put("market", one_xnb + "_" + two_xnb);
                            params.put("price", mPrice.getText().toString().trim());
                            params.put("num", mNumber.getText().toString().trim());
                            params.put("type", type);
                            params.put("paypassword", passContent);
                            params.put("source", "android");
//                            params.put("__token__", baseBean);
                            mPresenter.getData(DataUtil.sign(params));
                        } else {
                            TreeMap params = new TreeMap<>();
                            params.put("act", ConstantUrl.Contract_upContract);
                            params.put("market", one_xnb + "_" + two_xnb);
                            params.put("price", mPrice.getText().toString().trim());
                            params.put("num", mNumber.getText().toString().trim());
                            params.put("type", type);
//            params.put("paypassword", passContents);
                            params.put("source", "android");
//                            params.put("__token__", baseBean);
                            mPresenter.getData(DataUtil.sign(params));
                        }


                    }

                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭回调
                    }

                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调
                        startActivity(new Intent(mContext, TransactionPasswordActivity.class));
                    }
                });
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        mNumber.setText("");
        mPrice.setText("");
        handler.sendEmptyMessageDelayed(0, 2000);


        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void requestError(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
        ToastUtils.showToast(msg);
//        if (refresh) {
//            refresh = false;
//            sendSocket();
//        }
    }

    private String total = "0";
    private int buy_price_lock = 0;
    private int sell_price_lock = 0;
    private int buy_num_lock = 0;
    private int sell_num_lock = 0;

    @Override
    public void sendMsgSuccess(DealInfo data) {
//        if (refresh) {
//            refresh = false;
//            sendSocket();
//        }
        mSwiperefreshlayout.setRefreshing(false);
        mCny.setText("0");
        if (EmptyUtils.isNotEmpty(data.getData()) && EmptyUtils.isNotEmpty(data.getData().getRise_once()) && EmptyUtils.isNotEmpty(data.getData())) {
            trade_sell_fee = data.getData().getTrade_sell_fee();
            trade_buy_fee = data.getData().getTrade_buy_fee();
        }
        mTodayTab.setChecked(false);
        mRecordTab.setChecked(false);
        mContactTab.setChecked(false);
        mSettingsTab.setChecked(false);
        if (EmptyUtils.isNotEmpty(data.getData())) {
            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {

                mKeyong.setText(data.getData().getTwo_xnb());
                rise_once = data.getData().getRise_once() + "";
                total = data.getData().getTwo_xnb();
                if (EmptyUtils.isNotEmpty(baseBean) && EmptyUtils.isNotEmpty(baseBean.getCurrentPrice())) {
                    if (market_type.equals("0")) {
                        double vou = Double.valueOf(data.getData().getTwo_xnb()) / Double.valueOf(baseBean.getCurrentPrice());
                        totalnum = vou;
//                        mPrice.setText(baseBean.getCurrentPrice());
//                        mTabRadioGroup.check(mContactTab.getId());
                        getPrice();
                    } else {
                        if (data.getData().getBuy_price_lock() == 1) {
                            buy_price_lock = data.getData().getBuy_price_lock();
//                            mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(baseBean.getCurrentPrice()) + data.getData().getBuy_price_change())) + "");
                            mPrice.setEnabled(false);
                            mAddone.setEnabled(false);
                            mDeleteone.setEnabled(false);
//                            mTabRadioGroup.check(mContactTab.getId());
                            getPrice();
                        } else {
                            buy_price_lock = data.getData().getBuy_price_lock();
//                            mPrice.setText(baseBean.getCurrentPrice());
                            mPrice.setEnabled(true);
                            mAddone.setEnabled(true);
                            mDeleteone.setEnabled(true);
//                            mTabRadioGroup.check(mContactTab.getId());
                            getPrice();
                        }
                        if (data.getData().getBuy_num_lock() == 1) {
                            buy_num_lock = data.getData().getBuy_num_lock();
                            mNumber.setEnabled(false);
                            double vou = Double.valueOf(data.getData().getTwo_xnb()) / Double.valueOf(Double.valueOf(baseBean.getCurrentPrice()) + data.getData().getBuy_price_change());
                            totalnum = vou;
//                            mTabRadioGroup.check(mSettingsTab.getId());

                            mAddtwo.setEnabled(false);
                            mDeletetwo.setEnabled(false);
                        } else {
                            buy_num_lock = data.getData().getBuy_num_lock();
                            mNumber.setEnabled(true);
                            mAddtwo.setEnabled(true);
                            mDeletetwo.setEnabled(true);
                            double vou = Double.valueOf(data.getData().getTwo_xnb()) / Double.valueOf(baseBean.getCurrentPrice());
                            totalnum = vou;
//                            enableRadioGroup(mTabRadioGroup);
                            getPrice();
                        }
                    }
                }

//            mOneXnb.setText(data.getData().getTwo_xnb());
//            if (baseBean != null && baseBean.getSellRmb() != null && EmptyUtils.isNotEmpty(data.getData().getRise_once()) && EmptyUtils.isNotEmpty(baseBean.getCurrentPrice()) && Double.valueOf(baseBean.getCurrentPrice()) != 0) {
//                mPrice.setText((Double.valueOf(data.getData().getRise_once()) + Double.valueOf(baseBean.getCurrentPrice())) + "");
//            }

//            mOneXnbd.setText(data.getData().getOne_xnbd());
//            mTwoXnb.setText(data.getData().getOne_xnb());
//            mTwoXnbd.setText(data.getData().getTwo_xnbd());
            } else {
                mKeyong.setText(data.getData().getOne_xnb());
                total = data.getData().getOne_xnb();
                rise_once = data.getData().getRise_once() + "";
                if (EmptyUtils.isNotEmpty(baseBean) && EmptyUtils.isNotEmpty(baseBean.getCurrentPrice())) {
                    if (market_type.equals("0")) {
                        double vou = Double.valueOf(data.getData().getTwo_xnb()) / Double.valueOf(baseBean.getCurrentPrice());
                        totalnum = vou;
//                        mPrice.setText(baseBean.getCurrentPrice());
                        getPrice();
//                        mTabRadioGroup.check(mContactTab.getId());
                    } else {
                        if (data.getData().getSell_price_lock() == 1) {
                            sell_price_lock = data.getData().getSell_price_lock();
                            mPrice.setEnabled(false);
                            mAddone.setEnabled(false);
                            mDeleteone.setEnabled(false);
                            getPrice();
//                            mTabRadioGroup.check(mContactTab.getId());
//                            mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(baseBean.getCurrentPrice()) + data.getData().getSell_price_change())) + "");
                        } else {
                            sell_price_lock = data.getData().getSell_price_lock();
                            mPrice.setEnabled(true);
                            mAddone.setEnabled(true);
                            mDeleteone.setEnabled(true);
                            mTabRadioGroup.check(mContactTab.getId());
//                            mPrice.setText(baseBean.getCurrentPrice());
                            getPrice();
                        }
                        if (data.getData().getSell_num_lock() == 1) {
                            sell_num_lock = data.getData().getSell_num_lock();
                            mNumber.setEnabled(false);
                            mAddtwo.setEnabled(false);
                            mDeletetwo.setEnabled(false);
                            double vou = Double.valueOf(data.getData().getTwo_xnb()) / Double.valueOf(Double.valueOf(baseBean.getCurrentPrice()) + data.getData().getSell_price_change());
                            totalnum = vou;
//                            mTabRadioGroup.check(mSettingsTab.getId());
//                            disableRadioGroup(mTabRadioGroup);
                        } else {
                            sell_num_lock = data.getData().getSell_num_lock();
                            mNumber.setEnabled(true);
                            mAddtwo.setEnabled(true);
                            mDeletetwo.setEnabled(true);
                            double vou = Double.valueOf(data.getData().getTwo_xnb()) / Double.valueOf(baseBean.getCurrentPrice());
                            totalnum = vou;

//                            enableRadioGroup(mTabRadioGroup);
                            getPrice();
                        }
//                        mPrice.setText(new java.text.DecimalFormat("0.000000").format((Double.valueOf(baseBean.getCurrentPrice()) + data.getData().getSell_price_change()) )+ "");
                    }
                }
//            mOneXnb.setText(data.getData().getOne_xnb());
//            mOneXnbd.setText(data.getData().getTwo_xnbd());
//            mTwoXnb.setText(data.getData().getTwo_xnb());
//            mTwoXnbd.setText(data.getData().getOne_xnbd());

//            if (baseBean != null && baseBean.getSellRmb() != null && baseBean.getCurrentPrice() != null && Double.valueOf(baseBean.getCurrentPrice()) != 0) {
//                mPrice.setText(baseBean.getCurrentPrice());
//            }
            }

        }
    }

    private void getPrice() {
//        double number = Double.valueOf(total);
//        String resa = "0";
//        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
//            resa = new DecimalFormat("0.0000").format(ArithUtil.mul(totalnum, 0.75)) + "";
//            mNumber.setText(resa);
//        } else {
//            resa = new DecimalFormat("0.0000").format(ArithUtil.mul(number, 0.75)) + "";
//            mNumber.setText(resa);
//        }
    }


    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }

    public void setDetailSuccess(Home baseBean) {
        try {
            this.baseBean = baseBean;
            if (EmptyUtils.isNotEmpty(baseBean) && EmptyUtils.isNotEmpty(baseBean.getCurrentPrice())) {
                mLast.setText(baseBean.getCurrentPrice() + "");
                mCoinname.setText(baseBean.getRiseRate() + "");
                baibili.setText("￥" + baseBean.getSellRmb() + "");
                mGuzhi.setText("￥" + baseBean.getSellRmb().toString());

                mTodayTab.setChecked(false);
                mRecordTab.setChecked(false);
                mContactTab.setChecked(false);
                mSettingsTab.setChecked(false);
                if (market_type.equals("0")) {
                    mPrice.setText(baseBean.getCurrentPrice() + "");
                } else {
                    if (sell_price_lock == 1) {
                        mPrice.setEnabled(false);
                        mPrice.setText(new DecimalFormat("0.000000").format((Double.valueOf(baseBean.getCurrentPrice()) + Double.valueOf(rise_once))) + "");
                    } else {
                        mPrice.setEnabled(true);
                        mPrice.setText(baseBean.getCurrentPrice());
                    }
                }
                if (EmptyUtils.isNotEmpty(mKeyong.getText().toString())) {
                    double vou = 0;
                    if (market_type.equals("0")) {
                        vou = Double.valueOf(mKeyong.getText().toString()) / Double.valueOf(baseBean.getCurrentPrice());
                    } else {
                        vou = Double.valueOf(mKeyong.getText().toString()) / Double.valueOf(new DecimalFormat("0.000000").format((Double.valueOf(baseBean.getCurrentPrice()) + Double.valueOf(rise_once))));
                    }
                    totalnum = vou;
                }

                if (baseBean.getRiseRate().contains("-")) {
                    if ((mContext instanceof Activity)) {
                        mLast.setTextColor(mContext.getResources().getColor(R.color.common_green));
                        mCoinname.setTextColor(mContext.getResources().getColor(R.color.common_green));
                    }
                } else {
                    if ((mContext instanceof Activity)) {
                        mLast.setTextColor(mContext.getResources().getColor(R.color.common_red));
                        mCoinname.setTextColor(mContext.getResources().getColor(R.color.common_red));
                    }
                }
                getMsgInfo();
            }
        } catch (Exception e) {
//            mLast.setText("0");
//            mCoinname.setText("0");
//            baibili.setText("￥" + "0");
//            mGuzhi.setText("￥" + "0");
        }

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {
        if (EmptyUtils.isNotEmpty(baseBean)) {
            setDetailSuccess(baseBean);
        }


//        if (type.equals(ConstantUrl.TRANS_TYPE_BUY) && EmptyUtils.isNotEmpty(mOneXnb)) {
//            mOneXnbd.setText(new BigDecimal(ArithUtil.div(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(baseBean.getCurrentPrice()), 2) + "").stripTrailingZeros().toPlainString());
//        } else {
//            mOneXnbd.setText(new BigDecimal(ArithUtil.round((ArithUtil.mul(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(baseBean.getCurrentPrice()))), 2) + "").stripTrailingZeros().toPlainString());
//        }
    }

    @Override
    public void requestToken(String baseBean) {
        if (market_type.equals("0")) {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_UPTRADE);
            params.put("market", one_xnb + "_" + two_xnb);
            params.put("price", mPrice.getText().toString().trim());
            params.put("num", mNumber.getText().toString().trim());
            params.put("type", type);
            params.put("paypassword", passContents);
            params.put("source", "android");
            params.put("__token__", baseBean);
            mPresenter.getData(DataUtil.sign(params));
        } else {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.Contract_upContract);
            params.put("market", one_xnb + "_" + two_xnb);
            params.put("price", mPrice.getText().toString().trim());
            params.put("num", mNumber.getText().toString().trim());
            params.put("type", type);
//            params.put("paypassword", passContents);
            params.put("source", "android");
            params.put("__token__", baseBean);
            mPresenter.getData(DataUtil.sign(params));
        }
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

    private boolean change = true;

    public static boolean strResult(String args) {
        Boolean strResult = args.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
        return strResult;
    }

    @OnClick({R.id.tv_deal, R.id.ll_gears, R.id.ll_depth, R.id.change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_deal:
                if (AppUtils.isFastClick2()) {
                    submit();
                } else {
                    ToastUtils.showToast(getString(R.string.Cannotclick));
                }

                break;
            case R.id.ll_gears:
                showGearsPopMenu();
                break;
            case R.id.ll_depth:
                showDepthPopMenu();
                break;
            case R.id.change:
//                if (baseBean != null) {
//                    if (change) {
//                        change = false;
//                        mLast.setText(baseBean.getSellRmb().toString());
//                        mCoinname.setText("￥");
//                    } else {
//                        change = true;
//                        mLast.setText(baseBean.getCurrentPrice().toString());
//                        mCoinname.setText(baseBean.getExchangeType().toUpperCase());
//                    }
//
//                }

                break;
        }

    }

    private void showGearsPopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_deal_geals, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create();
        mCustomPopWindow.showAsDropDown(mLlGears, 15, -(mLlGears.getHeight() + mCustomPopWindow.getHeight() + 20));
    }

    private void showDepthPopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_deal_depth, null);
        //处理popWindow 显示内容
        handleDepthLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create();
        mCustomPopWindow.showAsDropDown(mLlDepth, 15, -(mLlDepth.getHeight() + mCustomPopWindow.getHeight() + 20));
    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        stutas = 5;
                        mTvGears.setText(stutas + getString(R.string.gears));
                        break;
                    case R.id.ll_item2:
                        stutas = 10;
                        mTvGears.setText(stutas + getString(R.string.gears));
                        break;
                    case R.id.ll_item3:
                        stutas = 20;
                        mTvGears.setText(stutas + getString(R.string.gears));
                        break;
                    case R.id.ll_item4:
                        stutas = 50;
                        mTvGears.setText(getString(R.string.fivefiles) + getString(R.string.gears));
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item2).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item3).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item4).setOnClickListener(listener);
    }

    private void handleDepthLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
    }

    @Override
    public void inputInforCompleted(String string) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 处理从子线程发送过来的消息

            int i = msg.what;
            if (i == 1) {
                DealBean2 mDesignates = (DealBean2) msg.obj;
                if (mBuyAdapter != null && mSellerAdapter != null) {
                    if (mDesignates != null && channel.equals(mDesignates.getChannel())) {
                        if (mDesignates != null) {

                            if (stutas == 50) {
                                if (mDesignates.getAsks().size() >= 50) {
                                    mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 50));
                                } else {
                                    mSellerAdapter.setNewData(mDesignates.getAsks());
                                }
                                if (mDesignates.getBids().size() >= 50) {
                                    mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 50));
                                } else {
                                    mBuyAdapter.setNewData(mDesignates.getBids());
                                }
                                getBuyDepthList3(mDesignates.getBids(), mDesignates.getAsks());
                                if (EmptyUtils.isNotEmpty(mDesignates.getAsks()) && EmptyUtils.isNotEmpty(mDesignates.getBids())&& EmptyUtils.isNotEmpty(depthView)) {
                                    depthView.setData(getBuyDepthList2(mDesignates.getBids()), getSellDepthList2(mDesignates.getAsks()));
                                }

                            } else if (stutas == 20) {
                                if (mDesignates.getAsks().size() >= 20) {
                                    mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 20));

                                } else {
                                    mSellerAdapter.setNewData(mDesignates.getAsks());
                                }
                                if (mDesignates.getBids().size() >= 20) {
                                    mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 20));
                                } else {
                                    mBuyAdapter.setNewData(mDesignates.getBids());
                                }
                                getBuyDepthList3(mDesignates.getBids(), mDesignates.getAsks());
                                if (EmptyUtils.isNotEmpty(mDesignates.getAsks()) && EmptyUtils.isNotEmpty(mDesignates.getBids())&& EmptyUtils.isNotEmpty(depthView)) {
                                    depthView.setData(getBuyDepthList2(mDesignates.getBids()), getSellDepthList2(mDesignates.getAsks()));
                                }

                            } else if (stutas == 10) {
                                ArrayList arrayList = new ArrayList<>(1);
                                arrayList.add(0f);
                                arrayList.add(0f);

                                ArrayList arrayList2 = new ArrayList<>(1);
                                arrayList2.add(0f);
                                arrayList2.add(0f);

                                if (mDesignates.getBids().size() < 10) {
                                    for (int bb = 0; i < 8; i++) {
                                        mDesignates.getBids().add(arrayList);
                                    }
                                }
                                if (mDesignates.getAsks().size() < 10) {
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
                                    mDesignates.getAsks().add(arrayList2);
//                                    for (int bb = 0; i < 8; i++) {
//                                        mDesignates.getAsks().add(arrayList);
//                                    }
                                }
//                                for (int jj = 0; i < 8; i++) {
//                                    mDesignates.getAsks().add(0,arrayList2);
//                                }
                                if (mDesignates.getAsks().size() >= 10) {
                                    mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 10));

                                } else {
                                    mSellerAdapter.setNewData(mDesignates.getAsks());
                                }
                                if (mDesignates.getBids().size() >= 10) {
                                    mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 10));
                                } else {


                                    mBuyAdapter.setNewData(mDesignates.getBids());
                                }
                                getBuyDepthList3(mDesignates.getBids(), mDesignates.getAsks());
                                if (EmptyUtils.isNotEmpty(mDesignates.getAsks()) && EmptyUtils.isNotEmpty(mDesignates.getBids())&& EmptyUtils.isNotEmpty(depthView)) {
                                    depthView.setData(getBuyDepthList2(mDesignates.getBids()), getSellDepthList2(mDesignates.getAsks()));
                                }

                            } else if (stutas == 5) {
                                if (mDesignates.getAsks().size() >= 5) {
                                    mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 5));

                                } else {
                                    mSellerAdapter.setNewData(mDesignates.getAsks());
                                }
                                if (mDesignates.getBids().size() >= 5) {
                                    mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 5));
                                } else {
                                    mBuyAdapter.setNewData(mDesignates.getBids());
                                }
                                getBuyDepthList3(mDesignates.getBids(), mDesignates.getAsks());
                                if (EmptyUtils.isNotEmpty(mDesignates.getAsks()) && EmptyUtils.isNotEmpty(mDesignates.getBids())&& EmptyUtils.isNotEmpty(depthView)) {
                                    depthView.setData(getBuyDepthList2(mDesignates.getBids()), getSellDepthList2(mDesignates.getAsks()));
                                }

                            }


                        }
                    }
                }
            } else if (i == 0) {
                getMsgInfo();
                Event event = new Event();
                event.setCode(Constants.ENTRUST);
                EventBus.getDefault().post(event);
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.DEAL) {
            cacelSocket();
            String coin = event.getMsg();
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];
//            mOneXnbd.setText("0");
            if (one_xnb.equals("SNRC")) {
                mInfomation.setVisibility(View.VISIBLE);
            } else {
                mInfomation.setVisibility(View.GONE);
            }
            channel = one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth";
            mNumber.setText("");
            mKeyong.setText("");
            mCny.setText("");
            initview();
            sendSocket();
//            WebSocketSetting setting = WebSocketHandler.getDefault().getSetting();
//            setting.setConnectUrl(Constant.Websocket);
//            WebSocketHandler.getDefault().reconnect(setting);
            getMsgInfo();

            Logger.i("<==>:isVisibleisVisibleisVisibleisVisible" + isVisible);


        } else if (event != null && event.getCode() == Constants.LOGINSUCCESS) {
            String coin = event.getMsg();
            if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                mTvDeal.setBackgroundResource(R.drawable.common_bg_red_corners);
                mTvDeal.setText(getString(R.string.item_buy) + " " + one_xnb);
                mTvDeal.setTextColor(getResources().getColor(R.color.white));
                if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    mTvDeal.setText(R.string.loginregister);
                }
            } else {
                mTvDeal.setBackgroundResource(R.drawable.common_bg_green_corners);
                mTvDeal.setText(getString(R.string.item_seller) + " " + one_xnb);
                mTvDeal.setTextColor(getResources().getColor(R.color.white));
                if (TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    mTvDeal.setText(R.string.loginregister);
                }
            }
            getMsgInfo();
        }
    }

    private String market_type = "0";
    private int stutas = 10;

    @Override
    public void inputInforCompleted(Home string) {

    }


    public void setFragment(FramnetBean framnetBean) {
        cacelSocket();
        one_xnb = framnetBean.getOne_xnb();
        two_xnb = framnetBean.getTwo_xnb();
        market_type = framnetBean.getMarket_type();

//            mOneXnbd.setText("0");
        channel = one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth";

        mNumber.setText("");
        mKeyong.setText("");
        initview();
        sendSocket();
//        ;
//        WebSocketSetting setting = WebSocketHandler.getDefault().getSetting();
//        setting.setConnectUrl(Constant.Websocket);
//        WebSocketHandler.getDefault().reconnect(setting);

        getMsgInfo();

        Logger.i("<==>:isVisibleisVisibleisVisibleisVisible" + isVisible);
    }

    private void cacelSocket() {

        SocketBean socketBean = new SocketBean();
        socketBean.setEvent("cancelChannel");
        socketBean.setMarket_type(market_type);
        socketBean.setChannel(one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth");
        Log.d("<==>", new Gson().toJson(socketBean));
//        mServerConnection.sendMessage(new Gson().toJson(socketBean));
//        channel = one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth";
        WebSocketHandler.getDefault().send(new Gson().toJson(socketBean));
//        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));
//        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean2));
        if (mSwiperefreshlayout != null) {
            mSwiperefreshlayout.setRefreshing(false);
        }
    }

    private void cacelSocket2() {

        SocketBean socketBean = new SocketBean();
        socketBean.setEvent("cancelChannel");
        socketBean.setMarket_type(market_type);
        socketBean.setChannel(one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth");
        Log.d("<==>", new Gson().toJson(socketBean));
//        mServerConnection.sendMessage(new Gson().toJson(socketBean));
//        channel = one_xnb.toLowerCase() + two_xnb.toLowerCase() + "_depth";
        WebSocketHandler.getDefault().send(new Gson().toJson(socketBean));
//        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));
//        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean2));
        sendSocket();
        if (mSwiperefreshlayout != null) {
            mSwiperefreshlayout.setRefreshing(false);
        }
    }
}
