package com.madaex.exchange.ui.market.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.dialog.BaseNetDialogFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.ArithUtil;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealBean;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.bean.SocketBean;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.buy.fragment.CoinListFrament;
import com.madaex.exchange.ui.buy.presenter.CoinPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.activity.EntrustActivity;
import com.madaex.exchange.ui.market.adapter.BuyDealAdapter;
import com.madaex.exchange.ui.market.adapter.SellerDealAdapter;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.view.KeyboardUtil;
import com.madaex.exchange.ui.market.view.MyKeyBoardView;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;
import com.madaex.exchange.view.EditInputFilter;
import com.madaex.exchange.view.PayPassDialog;
import com.madaex.exchange.view.PayPassView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.WebSocket;

/**
 * 项目：  madaexchange
 * 类名：  DealFragment.java
 * 时间：  2018/9/3 15:44
 * 描述：  ${TODO}
 */

public class DealFragment extends BaseNetDialogFragment<CoinPresenter> implements CoinContract.View {
    @BindView(R.id.keyboard_view)
    MyKeyBoardView keyboard_view;
    KeyboardUtil keyboardUtil;
    Unbinder unbinder;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    @BindView(R.id.deal_type)
    TextView mDealType;
    @BindView(R.id.acount)
    TextView mAcount;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.price_type)
    TextView mPriceType;
    @BindView(R.id.number_type)
    TextView mNumberType;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.ll_gears)
    LinearLayout mLlGears;
    @BindView(R.id.ll_depth)
    LinearLayout mLlDepth;
    @BindView(R.id.currentype)
    TextView mCurrentype;
    @BindView(R.id.sellRmb)
    TextView mSellRmb;
    @BindView(R.id.buyrecyclerview)
    RecyclerView mBuyrecyclerview;
    @BindView(R.id.sellerrecyclerview)
    RecyclerView mSellerrecyclerview;

    BuyDealAdapter mBuyAdapter;

    SellerDealAdapter mSellerAdapter;
    @BindView(R.id.exchangeType)
    TextView mExchangeType;
    @BindView(R.id.tv_gears)
    TextView mTvGears;
    @BindView(R.id.cny)
    TextView mCny;
    @BindView(R.id.total)
    TextView mTotal;
    @BindView(R.id.currentnumber)
    TextView mCurrentnumber;
    @BindView(R.id.tv_four)
    TextView mTvFour;
    @BindView(R.id.tv_two)
    TextView mTvTwo;
    @BindView(R.id.tv_three)
    TextView mTvThree;
    @BindView(R.id.exchangename)
    TextView mExchangename;

    private String trans_type;
    private String one_xnb = "";
    private String two_xnb = "";
    private CustomPopWindow mCustomPopWindow;
    Home baseBean = new Home();
    String mString = "";

    public static DealFragment newInstance(String string, String one_xnb, String two_xnb) {
        DealFragment fragment = null;
        if (fragment == null) {
            fragment = new DealFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        bundle.putString(Constants.ONE_XNB, one_xnb);
        bundle.putString(Constants.TWO_XNB, two_xnb);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mark_deal;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        trans_type = getArguments().getString(Constants.ARGS);
        one_xnb = getArguments().getString(Constants.ONE_XNB);
        two_xnb = getArguments().getString(Constants.TWO_XNB);

        mToolbarTitleTv.setText(one_xnb + "/" + two_xnb);
        mPriceType.setText(two_xnb);
        mNumberType.setText(one_xnb);
        if (trans_type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
            keyboardUtil = new KeyboardUtil(getActivity(), keyboard_view, 1);
            mDealType.setText(getString(R.string.item_buy) + one_xnb);
            mTvTwo.setText(getString(R.string.kebuy) + " " + one_xnb);
            mTvThree.setText(getString(R.string.cash) + " " + two_xnb);
            mDealType.setTextColor(getResources().getColor(R.color.common_red));
        } else {
            keyboardUtil = new KeyboardUtil(getActivity(), keyboard_view, 2);
            mDealType.setText(getString(R.string.item_seller) + one_xnb);
            mTvTwo.setText(getString(R.string.exchange) + " " + two_xnb);
            mTvThree.setText(getString(R.string.cash) + " " + one_xnb);
            mDealType.setTextColor(getResources().getColor(R.color.common_green));
        }
        mAcount.setText(SPUtils.getString(Constants.MOBILE));
        mSeekbar.setClickable(false);
        mSeekbar.setEnabled(false);
        mSeekbar.setSelected(false);
        mSeekbar.setFocusable(false);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mEtNumber.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBuyrecyclerview.setLayoutManager(linearLayoutManager);
        mBuyAdapter = new BuyDealAdapter(mContext);
        mBuyrecyclerview.setHasFixedSize(true);
        mBuyAdapter.setItemClickListener(new BuyDealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<String> list) {
                mEtPrice.setText(list.get(0).toString());
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        mBuyrecyclerview.setAdapter(mBuyAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mSellerrecyclerview.setLayoutManager(linearLayoutManager1);

        mSellerAdapter = new SellerDealAdapter(mContext);
        mSellerrecyclerview.setAdapter(mSellerAdapter);
        mSellerrecyclerview.setHasFixedSize(true);
        mSellerAdapter.setItemClickListener(new SellerDealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<String> list) {
                mEtPrice.setText(list.get(0).toString());
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        RxWebSocket.get(Constant.Websocket)
                .subscribeOn(Schedulers.io())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
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
                            DealBean mDesignates = new Gson().fromJson(message, DealBean.class);
                            if (mBuyAdapter != null && mSellerAdapter != null) {
                                if (mDesignates != null && channel.equals(mDesignates.getChannel())) {
                                    Collections.reverse(mDesignates.getAsks());
                                    if (stutas == 50) {
                                        mSellerAdapter.setNewData(mDesignates.getAsks());
                                        mBuyAdapter.setNewData(mDesignates.getBids());
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

                            if (mDesignates != null && mDesignates.getChannel().equals(channel2)) {
                                if (mDesignates != null) {
//                                    if (TextUtils.isEmpty(mString) && EmptyUtils.isNotEmpty(mCurrentnumber)) {
//                                        if (trans_type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
//                                            mTvFour.setText(ArithUtil.div(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(mDesignates.getTicker().getLast()), 2) + "");
//                                        } else {
//                                            mTvFour.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(mDesignates.getTicker().getLast())), 2) + "");
//                                        }
//                                    }
                                }

                            }
                        }
                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连");
                    }
                });
        sendSocket();
    }

    private WebSocket mWebSocket;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void initDatas() {

        getMsgInfo();

    }

    private void getMsgInfo() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_TRADE);
        params.put("one_xnb", one_xnb);
        params.put("two_xnb", two_xnb);
        mPresenter.getMsgInfo(DataUtil.sign(params));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);


        keyboardUtil.setOnOkClick(new KeyboardUtil.OnOkClick() {
            public void onOkClick() {
                submit();
            }
        });
        keyboardUtil.setOnRetClick(new KeyboardUtil.OnRetClick() {
            @Override
            public void onRetClick() {
                mEtPrice.setText("");
                mEtNumber.setText("");
            }
        });
        keyboardUtil.setOnTWOClick(new KeyboardUtil.onTWOClick() {
            @Override
            public void onOneClick() {
                keyboardUtil.attachTo(mEtPrice);
                mEtPrice.setFocusable(true);
                mEtPrice.setFocusableInTouchMode(true);
                mEtPrice.requestFocus();
            }

            @Override
            public void onTWOClick() {
                keyboardUtil.attachTo(mEtNumber);
                mEtNumber.setFocusable(true);
                mEtNumber.setFocusableInTouchMode(true);
                mEtNumber.requestFocus();
            }
        });
        mEtPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(mEtPrice);
                return false;
            }
        });
        mEtNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardUtil.attachTo(mEtNumber);
                return false;
            }
        });
        keyboardUtil.attachTo(mEtPrice);

        mEtPrice.setFilters(new InputFilter[]{new EditInputFilter(500000)});
        mEtNumber.setFilters(new InputFilter[]{new EditInputFilter(100000)});
        mEtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    mCny.setText("￥0");
                    return;
                }
                mString = editable.toString();

                if(!TextUtils.isEmpty(mCurrentnumber.getText().toString())){
                    mSeekbar.setClickable(true);
                    mSeekbar.setEnabled(true);
                    mSeekbar.setSelected(true);
                    mSeekbar.setFocusable(true);
                    int sb = (int) ArithUtil.round(ArithUtil.div(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(editable.toString())), 1);
                    mSeekbar.setMax(sb);
                }


                if (!TextUtils.isEmpty(mEtNumber.getText().toString().trim()) && !TextUtils.isEmpty(editable.toString())) {
                    if (!mEtNumber.getText().toString().trim().equals("0.") && !editable.toString().equals("0.")) {
                        double number = Double.valueOf(mEtNumber.getText().toString().trim());
                        double price = Double.valueOf(editable.toString());
                        mTotal.setText(ArithUtil.round(ArithUtil.mul(number, price), 2) + "");
                    }
                }

                if (!TextUtils.isEmpty(editable.toString())) {
                    if (!editable.toString().equals("0.") && Double.valueOf(editable.toString()) != 0 && Double.valueOf(baseBean.getCurrentPrice()) != 0) {
                        if (trans_type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                            mTvFour.setText(ArithUtil.div(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(editable.toString()), 2) + "");
                        } else {
                            mTvFour.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(editable.toString())), 2) + "");
                        }
                        mCny.setText("￥" + ArithUtil.round(ArithUtil.mul(ArithUtil.div(Double.valueOf(baseBean.getSellRmb()), Double.valueOf(baseBean.getCurrentPrice()), 2), Double.valueOf(editable.toString())), 4) + "");

                    }
                }

            }
        });
        mEtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(mEtPrice.getText().toString().trim()) && !TextUtils.isEmpty(editable.toString())) {
                    if (!mEtPrice.getText().toString().trim().equals("0.") && !editable.toString().equals("0.")) {
                        double number = Double.valueOf(mEtPrice.getText().toString().trim());
                        double price = Double.valueOf(editable.toString());
                        mTotal.setText(ArithUtil.round(ArithUtil.mul(number, price), 2) + "");
                    }
                }
                if (!TextUtils.isEmpty(editable.toString())) {
                    if (!editable.toString().equals("0.") && !editable.toString().equals("0")) {
                        if (trans_type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
                            mTvFour.setText(ArithUtil.div(Double.valueOf(editable.toString()), Double.valueOf(mExchangename.getText().toString()), 2) + "");
                        } else {
                            mTvFour.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(editable.toString()), Double.valueOf(mExchangename.getText().toString())), 2) + "");
                        }
                    }
                }
            }
        });
    }

    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 6;

    private InputFilter lengthFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                if (dotValue.length() == DECIMAL_DIGITS) {
                    return "";
                }
            }
            return null;
        }

    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.MARK) {
            String coin = event.getMsg();
            mToolbarTitleTv.setText(coin);
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];
            sendSocket();
        }
    }

    private String channel;
    private String channel2;

    private void sendSocket() {

        SocketBean socketBean = new SocketBean();
        socketBean.setEvent("addChannel");
        socketBean.setChannel(one_xnb.toLowerCase() + "qc" + "_depth");
        Log.d("<==>", new Gson().toJson(socketBean));
        channel = one_xnb.toLowerCase() + "qc" + "_depth";
        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean));
        TreeMap params2 = new TreeMap<>();
        params2.put("act", ConstantUrl.TRADE_HOME_INDEX_DETAIL);
        params2.put("market", one_xnb + "_" + two_xnb);
        mPresenter.getJavaLineDetail(params2);
        SocketBean socketBean2 = new SocketBean();
        socketBean2.setEvent("addChannel");
        socketBean2.setChannel(one_xnb.toLowerCase() + "qc" + "_ticker");
        channel2 = one_xnb.toLowerCase() + "qc" + "_ticker";
        RxWebSocket.asyncSend(Constant.Websocket, new Gson().toJson(socketBean2));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDatePickerDialog);
    }


    @OnClick({R.id.close_input, R.id.look_entrust, R.id.rl, R.id.toolbar_title_tv, R.id.ll_gears, R.id.ll_depth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_input:
                dismiss();
                break;
            case R.id.look_entrust:
                Intent intent = new Intent();
                intent.setClass(mContext, EntrustActivity.class);
                intent.putExtra(Constants.ONE_XNB, one_xnb);
                intent.putExtra(Constants.TWO_XNB, two_xnb);
                startActivity(intent);
                break;
            case R.id.rl:
                dismiss();
                break;
            case R.id.toolbar_title_tv:
                FragmentManager fm = getChildFragmentManager();
                CoinListFrament editNameDialog = CoinListFrament.newInstance(Constants.MARK, one_xnb, two_xnb);
                editNameDialog.show(fm, "fragment_bottom_dialog");
                break;
            case R.id.ll_gears:
                showGearsPopMenu();
                break;
            case R.id.ll_depth:
                showDepthPopMenu();
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
        mCustomPopWindow.showAsDropDown(mLlGears, 0, -(mLlGears.getHeight() + mCustomPopWindow.getHeight()));
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
        mCustomPopWindow.showAsDropDown(mLlDepth, 0, -(mLlDepth.getHeight() + mCustomPopWindow.getHeight()));
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
                        mTvGears.setText(stutas + "");
                        break;
                    case R.id.ll_item2:
                        stutas = 10;
                        mTvGears.setText(stutas + "");
                        break;
                    case R.id.ll_item3:
                        stutas = 20;
                        mTvGears.setText(stutas + "");
                        break;
                    case R.id.ll_item4:
                        stutas = 50;
                        mTvGears.setText(getString(R.string.fivefiles) + "");
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

    private void submit() {
        if (TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.entryvoteprice));
            return;
        }
        if (TextUtils.isEmpty(mEtNumber.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.entryvotenumber));
            return;
        }

        payDialog();

    }

    PayPassDialog dialog;

    private void payDialog() {
        dialog = new PayPassDialog(mContext);
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        TreeMap params = new TreeMap<>();
                        params.put("act", ConstantUrl.TRADE_UPTRADE);
                        params.put("market", one_xnb + "_" + two_xnb);
                        params.put("price", mEtPrice.getText().toString().trim());
                        params.put("num", mEtNumber.getText().toString().trim());
                        params.put("paypassword", passContent);
                        params.put("type", trans_type);
                        params.put("source", "android");
                        mPresenter.deal(DataUtil.sign(params));
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
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void sendMsgSuccess(final CoinList msg) {
    }

    @Override
    public void sendDealSuccess(String msg) {
        ToastUtils.showToast(msg);
        mEtPrice.setText("");
        mEtNumber.setText("");
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    @Override
    public void requestDetailSuccess(Home baseBean) {
        mCurrentype.setText(baseBean.getCurrentype().toUpperCase() + "  " + getString(R.string.currentprice));
        mExchangeType.setText(baseBean.getExchangeType().toUpperCase() + "  ");
        mExchangename.setText(baseBean.getCurrentPrice());
        mSellRmb.setText("￥" + baseBean.getSellRmb());
        this.baseBean = baseBean;
        if (baseBean.getRiseRate().contains("-")) {
            mExchangeType.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mExchangename.setTextColor(mContext.getResources().getColor(R.color.common_green));
            mSellRmb.setTextColor(mContext.getResources().getColor(R.color.common_green));
        } else {
            mExchangeType.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mExchangename.setTextColor(mContext.getResources().getColor(R.color.common_red));
            mSellRmb.setTextColor(mContext.getResources().getColor(R.color.common_red));
        }

//        if (TextUtils.isEmpty(mString) && EmptyUtils.isNotEmpty(mCurrentnumber)) {
//            if (trans_type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
//                mTvFour.setText(ArithUtil.div(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(baseBean.getCurrentPrice()), 2) + "");
//            } else {
//                mTvFour.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(mCurrentnumber.getText().toString()), Double.valueOf(baseBean.getCurrentPrice())), 2) + "");
//            }
//        }
    }

    @Override
    public void sendMsgSuccess(DealInfo data) {

        if (trans_type.equals(ConstantUrl.TRANS_TYPE_BUY)) {
            mCurrentnumber.setText(data.getData().getTwo_xnb());
            mTvFour.setText(data.getData().getOne_xnbd());
        } else {
            mCurrentnumber.setText(data.getData().getOne_xnb());
            mTvFour.setText(data.getData().getTwo_xnbd());
        }
    }

    private int stutas = 50;


    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 8;

}
