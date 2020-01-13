package com.madaex.exchange.ui.buy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.ArithUtil;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.ui.buy.adapter.BuyAdapter;
import com.madaex.exchange.ui.buy.adapter.Depth;
import com.madaex.exchange.ui.buy.adapter.DepthView;
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
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;
import com.madaex.exchange.view.EditInputFilter;
import com.madaex.exchange.view.PayPassDialog;
import com.madaex.exchange.view.PayPassView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.WebSocket;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class BuyFragment extends BaseNetLazyFragment<DealPresenter> implements DealContract.View, CoinLister {
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.et_account)
    TextView mEtAccount;
    @BindView(R.id.tv_deal)
    TextView mTvDeal;
    Unbinder unbinder;
    //    @BindView(R.id.one_xnb)
//    TextView mOneXnb;
//    @BindView(R.id.one_xnbd)
//    TextView mOneXnbd;
//    @BindView(R.id.two_xnb)
//    TextView mTwoXnb;
//    @BindView(R.id.two_xnbd)
//    TextView mTwoXnbd;
//    @BindView(R.id.tv_one)
//    TextView mTvOne;
//    @BindView(R.id.tv_two)
//    TextView mTvTwo;
//    @BindView(R.id.tv_three)
//    TextView mTvThree;
//    @BindView(R.id.tv_four)
//    TextView mTvFour;
    @BindView(R.id.typeone)
    TextView mTypeone;
    @BindView(R.id.typetwo)
    TextView mTypetwo;
    @BindView(R.id.typethree)
    TextView mTypethree;
    @BindView(R.id.cny)
    TextView mCny;
    @BindView(R.id.buyrecyclerview)
    RecyclerView mBuyrecyclerview;
    @BindView(R.id.sellerrecyclerview)
    RecyclerView mSellerrecyclerview;
    @BindView(R.id.last)
    TextView mLast;
    @BindView(R.id.coinname)
    TextView mCoinname;
    @BindView(R.id.change)
    ImageView mChange;
    @BindView(R.id.order_buy_fee)
    TextView mOrderBuyFee;
    @BindView(R.id.ll_fee)
    LinearLayout mLlFee;
    @BindView(R.id.infomation)
    TextView mInfomation;
    @BindView(R.id.dv_depth)
    DepthView depthView;
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

    BuyAdapter mBuyAdapter;

    SellerAdapter mSellerAdapter;
    String str = "";
    Home baseBean = new Home();

    public static BuyFragment newInstance(String string, String one_xnb, String two_xnb) {
        BuyFragment fragment = null;
        if (fragment == null) {
            fragment = new BuyFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        bundle.putString(Constants.ONE_XNB, one_xnb);
        bundle.putString(Constants.TWO_XNB, two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

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
            public void onItemClick(List<Double> list) {
                mEtPrice.setText(list.get(0).toString());
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
            public void onItemClick(List<Double> list) {
                mEtPrice.setText(list.get(0).toString());
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
//        mSellerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                List<String> list = (List<String>) adapter.getItem(position);
//                mEtPrice.setText(list.get(0).toString());
//            }
//        });
        RxWebSocket.get(Constant.Websocket)
                .subscribeOn(Schedulers.io())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        Log.d("MainActivity", "onOpen1:");
                        mWebSocket = webSocket;
                    }

                    @Override
                    protected void onMessage(String message) {

                        if (TextUtils.isEmpty(message) || message.equals("hello")) {
                            return;
                        }
                        Log.i("MainActivity", message);
                        if (currentBackPressedTime < BACK_PRESSED_INTERVAL) {
                            currentBackPressedTime++;
                        } else {
                            currentBackPressedTime = 0;
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    DealBean2 mDesignates = JSON.parseObject(message, DealBean2.class);
                                    Message message = Message.obtain();
                                    message.obj = mDesignates;
                                    message.what = 1;
                                    handler.sendMessage(message);//将message对象发送出去
                                }
                            }).start();

                        }
                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连");
                    }
                });

    }

    private void initData(double centerPrice) {
//        //添加购买数据
//        depthView.setBuyDataList(getBuyDepthList());
//
//        //添加出售数据
//        depthView.setSellDataList(getSellDepthList());

        //设置横坐标中间值
        depthView.setAbscissaCenterPrice(centerPrice);

        //设置数据详情的价钱说明
        depthView.setDetailPriceTitle(getString(R.string.price));

        //设置数据详情的数量说明
        depthView.setDetailVolumeTitle(getString(R.string.allnum));

        //设置横坐标价钱小数位精度
        depthView.setPricePrecision(4);

        //是否显示竖线
        depthView.setShowDetailLine(true);

        //手指单击松开后，数据是否继续显示
        depthView.setShowDetailSingleClick(true);

        //手指长按松开后，数据是否继续显示
        depthView.setShowDetailLongPress(true);

    }

    //模拟深度数据
    private List<Depth> getBuyDepthList() {
        List<Depth> depthList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            depthList.add(new Depth(100 - random.nextDouble() * 10,
                    random.nextInt(10) * random.nextInt(10) * random.nextInt(10) + random.nextDouble(), 0));
        }
        return depthList;
    }

    private List<Depth> getBuyDepthList2(List<List<Double>> lists) {
        List<Depth> depthList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            depthList.add(new Depth(lists.get(i).get(0),
                    lists.get(i).get(1), 0));
        }
        return depthList;
    }

    //模拟深度数据
    private List<Depth> getSellDepthList() {
        List<Depth> depthList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            depthList.add(new Depth(100 + random.nextDouble() * 10,
                    random.nextInt(10) * random.nextInt(10) * random.nextInt(10) + random.nextDouble(), 1));
        }
        return depthList;
    }

    private List<Depth> getSellDepthList2(List<List<Double>> lists) {
        List<Depth> depthList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            depthList.add(new Depth(lists.get(i).get(0),
                    lists.get(i).get(1), 1));
        }
        return depthList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        depthView.cancelCallback();
    }

    private WebSocket mWebSocket;
    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 6;
    private String channel;
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

        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));
        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean2));
    }

    private void linedetail() {
        TreeMap params2 = new TreeMap<>();
        params2.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL);
        params2.put("market", one_xnb + "_" + two_xnb);
        mPresenter.getJavaLineDetail(DataUtil.sign(params2));
    }


    private void initview() {
        market_type = SPUtils.getString("market_type", "0");
        getMsgInfo();
        if (market_type.equals("0")) {
            mEtPrice.setEnabled(true);

        } else {
            mEtPrice.setEnabled(false);
        }
        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
            mLlFee.setVisibility(View.VISIBLE);
            mTvDeal.setBackgroundResource(R.drawable.common_bg_red_corners);
            mTvDeal.setText(getString(R.string.item_buy) + " " + one_xnb);
            mTvDeal.setTextColor(getResources().getColor(R.color.white));
//            mTvOne.setText(getString(R.string.Available) + " " + two_xnb);
//            mTvTwo.setText(getString(R.string.kebuy) + " " + one_xnb);
//            mTvThree.setText(getString(R.string.Available) + " " + one_xnb);
//            mTvFour.setText(getString(R.string.Frozen) + " " + two_xnb);
        } else {
            mLlFee.setVisibility(View.VISIBLE);
            mTvDeal.setBackgroundResource(R.drawable.common_bg_green_corners);
            mTvDeal.setText(getString(R.string.item_seller) + " " + one_xnb);
            mTvDeal.setTextColor(getResources().getColor(R.color.white));
//            mTvOne.setText(getString(R.string.Available) + " " + one_xnb);
//
//            mTvTwo.setText(getString(R.string.exchange) + " " +two_xnb  + "");
//            mTvThree.setText(getString(R.string.Available) + " " + two_xnb);
//            mTvFour.setText(getString(R.string.Frozen) + " " + one_xnb);
        }


        mTypeone.setText(two_xnb);
        mTypetwo.setText(one_xnb);
        mTypethree.setText(two_xnb);
    }

    private void getMsgInfo() {
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


    @Override
    public void onResume() {
        super.onResume();
        sendSocket();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        mEtPrice.addTextChangedListener(new TextWatcher() {
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
                if (!TextUtils.isEmpty(mEtNumber.getText().toString().trim()) && !TextUtils.isEmpty(editable.toString())) {
                    if (!mEtPrice.getText().toString().trim().equals("0.") && !editable.toString().equals("0.")) {
                        double number = Double.valueOf(mEtNumber.getText().toString().trim());
                        double price = Double.valueOf(editable.toString());
                        mEtAccount.setText(ArithUtil.round(ArithUtil.mul(number, price), 4) + "");
//                        mOrderBuyFee.setText((ArithUtil.round(ArithUtil.mul(number, price) * 0.003, 2) + ""));
                    }
                }

                if (!TextUtils.isEmpty(editable.toString())) {
                    if (!editable.toString().equals("0.") && Double.valueOf(editable.toString()) != 0) {
//                        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
//                            mOneXnbd.setText(new BigDecimal(ArithUtil.div(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(editable.toString()), 2) + "").stripTrailingZeros().toPlainString());
//                        } else {
//                            mOneXnbd.setText(new BigDecimal(ArithUtil.round(ArithUtil.mul(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(editable.toString())), 2) + "").stripTrailingZeros().toPlainString());
//                        }
                        if (baseBean != null && baseBean.getSellRmb() != null && baseBean.getCurrentPrice() != null && Double.valueOf(baseBean.getCurrentPrice()) != 0) {
                            mCny.setText("￥" + new BigDecimal(ArithUtil.round(ArithUtil.mul(ArithUtil.div(Double.valueOf(baseBean.getSellRmb()),
                                    Double.valueOf(baseBean.getCurrentPrice()), 2), Double.valueOf(editable.toString())), 2) + "").stripTrailingZeros().toPlainString());
                        }
                    }
                }
            }
        });
        mEtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(mEtPrice.getText().toString().trim()) && !TextUtils.isEmpty(editable.toString())) {
                    if (!mEtPrice.getText().toString().trim().equals("0.") && !editable.toString().equals("0.")) {
                        double number = Double.valueOf(mEtPrice.getText().toString().trim());
                        double price = Double.valueOf(editable.toString());
                        mEtAccount.setText(ArithUtil.round(ArithUtil.mul(number, price), 4) + "");
//                        mOrderBuyFee.setText((ArithUtil.round(ArithUtil.mul(number, price) * 0.003, 2) + ""));
                    }
                }
            }
        });
    }

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
        mEtPrice.setFilters(new InputFilter[]{new EditInputFilter(500000)});
        mEtNumber.setFilters(new InputFilter[]{new EditInputFilter(100000)});
        Observable.interval(0, 15, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        linedetail();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    private void submit() {
        if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
            if (TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
                ToastUtils.showToast(getString(R.string.entryvoteprice));
                return;
            }
            if (TextUtils.isEmpty(mEtNumber.getText().toString().trim())) {
                ToastUtils.showToast(getString(R.string.entryvotenumber));
                return;
            }
            payDialog();

        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }

    }


    PayPassDialog dialog;

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
                            params.put("price", mEtPrice.getText().toString().trim());
                            params.put("num", mEtNumber.getText().toString().trim());
                            params.put("type", type);
                            params.put("paypassword", passContent);
                            params.put("source", "android");
                            mPresenter.getData(DataUtil.sign(params));
                        } else {
                            TreeMap params = new TreeMap<>();
                            params.put("act", ConstantUrl.TRADE_UPTRADE);
                            params.put("market", one_xnb + "_" + two_xnb);
                            params.put("price", mEtPrice.getText().toString().trim());
                            params.put("num", mEtNumber.getText().toString().trim());
                            params.put("type", type);
                            params.put("paypassword", passContent);
                            params.put("source", "android");
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
        mEtPrice.getText().clear();
        mEtNumber.getText().clear();
        mEtAccount.setText("");
        mOrderBuyFee.setText("");
        getMsgInfo();
        Event event = new Event();
        event.setCode(Constants.ENTRUST);
        EventBus.getDefault().post(event);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void sendMsgSuccess(DealInfo data) {
        if (type.equals(ConstantUrl.TRANS_TYPE_BUY)) {

//            mOneXnb.setText(data.getData().getTwo_xnb());
            mOrderBuyFee.setText(data.getData().getTrade_buy_fee());
//            mOneXnbd.setText(data.getData().getOne_xnbd());
//            mTwoXnb.setText(data.getData().getOne_xnb());
//            mTwoXnbd.setText(data.getData().getTwo_xnbd());
        } else {
//            mOneXnb.setText(data.getData().getOne_xnb());
//            mOneXnbd.setText(data.getData().getTwo_xnbd());
//            mTwoXnb.setText(data.getData().getTwo_xnb());
//            mTwoXnbd.setText(data.getData().getOne_xnbd());
            mOrderBuyFee.setText(data.getData().getTrade_sell_fee());
        }
    }

    @Override
    public void requestDetailSuccess(Home baseBean) {
        this.baseBean = baseBean;
        mLast.setText(baseBean.getCurrentPrice().toString());
        mCoinname.setText(baseBean.getExchangeType().toUpperCase());
        if (baseBean.getRiseRate().contains("-")) {
            mLast.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mCoinname.setTextColor(mContext.getResources().getColor(R.color.common_green));
        } else {
            mLast.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mCoinname.setTextColor(mContext.getResources().getColor(R.color.common_red));
        }
        initData(Double.valueOf(baseBean.getCurrentPrice()));
//        if (type.equals(ConstantUrl.TRANS_TYPE_BUY) && EmptyUtils.isNotEmpty(mOneXnb)) {
//            mOneXnbd.setText(new BigDecimal(ArithUtil.div(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(baseBean.getCurrentPrice()), 2) + "").stripTrailingZeros().toPlainString());
//        } else {
//            mOneXnbd.setText(new BigDecimal(ArithUtil.round((ArithUtil.mul(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(baseBean.getCurrentPrice()))), 2) + "").stripTrailingZeros().toPlainString());
//        }
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

    @OnClick({R.id.tv_deal, R.id.ll_gears, R.id.ll_depth, R.id.change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_deal:
                submit();
                break;
            case R.id.ll_gears:
                showGearsPopMenu();
                break;
            case R.id.ll_depth:
                showDepthPopMenu();
                break;
            case R.id.change:
                if (baseBean != null) {
                    if (change) {
                        change = false;
                        mLast.setText(baseBean.getSellRmb().toString());
                        mCoinname.setText("￥");
                    } else {
                        change = true;
                        mLast.setText(baseBean.getCurrentPrice().toString());
                        mCoinname.setText(baseBean.getExchangeType().toUpperCase());
                    }

                }

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
            DealBean2 mDesignates = (DealBean2) msg.obj;
            int i = msg.what;
            if (i == 1) {
                if (mBuyAdapter != null && mSellerAdapter != null) {
                    if (mDesignates != null && EmptyUtils.isNotEmpty(mDesignates.getAsks()) && EmptyUtils.isNotEmpty(mDesignates.getBids())) {
                        Collections.reverse(mDesignates.getAsks());
                        if (stutas == 50) {
                            mSellerAdapter.setNewData(mDesignates.getAsks());
                            mBuyAdapter.setNewData(mDesignates.getBids());
                            depthView.resetAllData(getBuyDepthList2(mDesignates.getBids()), getSellDepthList2(mDesignates.getAsks()));
                        }
                        if (stutas == 20) {
                            if (mDesignates.getAsks().size() >= 20) {
                                mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 20));

                            }
                            if (mDesignates.getBids().size() >= 20) {
                                mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 20));
                            }
                        }
                        if (stutas == 10) {
                            if (mDesignates.getAsks().size() >= 10) {
                                mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 10));

                            }
                            if (mDesignates.getBids().size() >= 10) {
                                mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 10));
                            }
                        }
                        if (stutas == 5) {
                            if (mDesignates.getAsks().size() >= 5) {
                                mSellerAdapter.setNewData(mDesignates.getAsks().subList(0, 5));

                            }
                            if (mDesignates.getBids().size() >= 5) {
                                mBuyAdapter.setNewData(mDesignates.getBids().subList(0, 5));
                            }
                        }
                    }
                }

                if (mDesignates != null) {
                    if (mDesignates != null) {
                        if (mEtPrice != null) {
//                            if (TextUtils.isEmpty(str)) {
//                                if (type.equals(ConstantUrl.TRANS_TYPE_BUY) && EmptyUtils.isNotEmpty(mOneXnb)) {
//                                    mOneXnbd.setText(new BigDecimal(ArithUtil.div(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(mDesignates.getTicker().getLast()), 2) + "").stripTrailingZeros().toPlainString());
//                                } else {
//                                    mOneXnbd.setText(new BigDecimal(ArithUtil.round((ArithUtil.mul(Double.valueOf(mOneXnb.getText().toString()), Double.valueOf(mDesignates.getTicker().getLast()))), 2) + "").stripTrailingZeros().toPlainString());
//                                }
//                            }
                        }
                    }
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.DEAL) {
            String coin = event.getMsg();
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];

            mEtPrice.setText("");
            mEtNumber.setText("");
//            mOneXnbd.setText("0");
            mEtAccount.setText("");
            if (one_xnb.equals("SNRC")) {
                mInfomation.setVisibility(View.VISIBLE);
            } else {
                mInfomation.setVisibility(View.GONE);
            }
            sendSocket();
            initview();
        } else if (event != null && event.getCode() == Constants.LOGINSUCCESS) {
            String coin = event.getMsg();
            getMsgInfo();
        }
    }

    private String market_type = "0";
    private int stutas = 50;

}
