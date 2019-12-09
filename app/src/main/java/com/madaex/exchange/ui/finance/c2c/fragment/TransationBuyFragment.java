package com.madaex.exchange.ui.finance.c2c.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.ArithUtil;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.c2c.bean.OrderFee;
import com.madaex.exchange.ui.finance.c2c.bean.TransationInfo;
import com.madaex.exchange.ui.finance.c2c.contract.TransationContract;
import com.madaex.exchange.ui.finance.c2c.presenter.TransationPresenter;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;
import com.madaex.exchange.view.EditInputFilter;
import com.madaex.exchange.view.PayPassDialog;
import com.madaex.exchange.view.PayPassView;
import com.wc.widget.dialog.IosDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目：  madaexchange
 * 类名：  TransationBuyFragment.java
 * 时间：  2018/8/30 10:19
 * 描述：  ${TODO}
 */

public class TransationBuyFragment extends BaseNetLazyFragment<TransationPresenter> implements TransationContract.View {

    @BindView(R.id.number)
    EditText mNumber;
    Unbinder unbinder;
    @BindView(R.id.tv_buytotal)
    TextView mTvBuytotal;
    @BindView(R.id.price)
    EditText mPrice;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.order_sell_fee)
    TextView mOrderSellFee;
    @BindView(R.id.minunmber)
    EditText mMinunmber;
    @BindView(R.id.ll_min)
    LinearLayout mLlMin;
    private String goods_min = "0";
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.name2)
    TextView mName2;
    private String goods_buy_price_min = "0";
    private String goods_buy_price_max = "0";

    public static TransationBuyFragment newInstance(String string) {
        TransationBuyFragment fragment = null;
        if (fragment == null) {
            fragment = new TransationBuyFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
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
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FIAT_FEE_CONFIG);
        mPresenter.getView(DataUtil.sign(params));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_c2cbuy;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        one_xnb = getArguments().getString(Constants.ARGS, "GRC");
        if (getActivity().getIntent().hasExtra("num")) {
            mNumber.setHint("最小起买量" + getActivity().getIntent().getDoubleExtra("num",0));
        }
        if (getActivity().getIntent().hasExtra("price")) {
            mPrice.setText(getActivity().getIntent().getStringExtra("price"));
            mPrice.setFocusable(false);
            mPrice.setFocusableInTouchMode(false);

        } else {
            mLlMin.setVisibility(View.VISIBLE);
        }
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (EmptyUtils.isNotEmpty(editable.toString()) && DataUtil.isNumber(editable.toString())) {
                    mOrderSellFee.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(mTransationInfo.getData().getOrder_buy_fee()), Double.valueOf(editable.toString())), 2) + "");
                    if (EmptyUtils.isNotEmpty(mTransationInfo) && DataUtil.isNumber(mPrice.getText().toString())) {
                        mTvBuytotal.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(mPrice.getText().toString()), Double.valueOf(editable.toString())), 2) + "");
                    }
                }
            }
        });
        mPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (EmptyUtils.isNotEmpty(editable.toString()) && DataUtil.isNumber(editable.toString()) && DataUtil.isNumber(mNumber.getText().toString())) {
                    mTvBuytotal.setText(ArithUtil.round(ArithUtil.mul(Double.valueOf(mNumber.getText().toString()), Double.valueOf(editable.toString())), 2) + "");
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        mNumber.setFilters(new InputFilter[]{new EditInputFilter(1000000, 2)});

    }

    @Override
    public void requestSuccess(String msg) {
        Event event = new Event();
        event.setCode(Constants.buy);
        event.setMsg("");
        EventBus.getDefault().post(event);
        ToastUtils.showToast(msg);
        getActivity().finish();
//        startActivity(new Intent(mContext, C2CTransationListActivity.class));
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void sendViewSuccess(TransationInfo msg) {

    }

    OrderFee mTransationInfo;

    @Override
    public void sendViewSuccess(OrderFee msg) {
        goods_min = msg.getData().getGoods_min();
        mTransationInfo = msg;
        goods_buy_price_min = msg.getData().getGoods_buy_price_min();
        mPrice.setHint("最小起买金额" +goods_buy_price_min+"-"+goods_buy_price_max);
        goods_buy_price_max = msg.getData().getGoods_buy_price_max();
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

    private String one_xnb = "";

    @OnClick(R.id.submit)
    public void onViewClicked() {
        validate();
    }

    private void validate() {
        if (TextUtils.isEmpty(mNumber.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrureturnnumber));
            return;
        }
        if (TextUtils.isEmpty(mPrice.getText().toString())) {
            ToastUtils.showToast(R.string.Selectprice);
            return;
        }
//        if (Double.valueOf(goods_buy_price_min) > Double.valueOf(mPrice.getText().toString()) || Double.valueOf(mPrice.getText().toString()) > Double.valueOf(goods_buy_price_max)) {
//            ToastUtils.showToast(getString(R.string.Select) + goods_buy_price_min + getString(R.string.to) + goods_buy_price_max + getString(R.string.wayprice));
//            return;
//        }
//        if (Double.valueOf(mNumber.getText().toString()) < Double.valueOf(goods_min)) {
//            ToastUtils.showToast(R.string.Notless);
//            return;
//        }
//        if (getActivity().getIntent().hasExtra("num")) {
//            if (Double.valueOf(mNumber.getText().toString()) > getActivity().getIntent().getDoubleExtra("num", 10000)) {
//                ToastUtils.showToast(R.string.quantitynotsufficient);
//                return;
//            }
//        }else if (TextUtils.isEmpty(mMinunmber.getText().toString())) {
//            ToastUtils.showToast(R.string.Minimumpurchasequantity);
//            return;
//        }

        Dialog dialog = new IosDialog.Builder(mContext).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                .setMessage(R.string.sure).setMessageSize(14)
                .setNegativeButtonColor(Color.GRAY)
                .setNegativeButtonSize(18)
                .setNegativeButton(getString(R.string.cancel), new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonColor(ContextCompat.getColor(mContext, R.color.common_bule))
                .setPositiveButtonSize(18)
                .setPositiveButton(R.string.submit, new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        dialog.dismiss();
                        payDialog();
                    }
                }).build();
        dialog.show();
    }

    PayPassDialog dialog;

    private void payDialog() {
        dialog = new PayPassDialog(mContext);
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        if (getActivity().getIntent().hasExtra("price")) {
                            TreeMap params = new TreeMap<>();
                            params.put("act", ConstantUrl.FIAT_ORDER_PLACE);
                            params.put("num", mNumber.getText().toString().trim());
                            params.put("goods_id", getActivity().getIntent().getStringExtra("goods_id"));
                            params.put("paypassword", passContent);
                            params.put("order_type", "1");
                            mPresenter.getData(DataUtil.sign(params));
                        } else {
                            TreeMap params = new TreeMap<>();
                            params.put("act", ConstantUrl.FIAT_GOODS_ADD);
                            params.put("coin_ename", one_xnb);
                            params.put("price", mPrice.getText().toString().trim());
                            params.put("num", mNumber.getText().toString().trim());
                            params.put("num_min", mMinunmber.getText().toString().trim());
                            params.put("paypassword", passContent);
                            params.put("type", "1");
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



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.C2CTrans) {
            String coin = event.getMsg();
            mName.setText(coin);
            one_xnb = coin;
        }
    }
}
