package com.madaex.exchange.ui.finance.transfer.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.transfer.contract.CoinList;
import com.madaex.exchange.ui.finance.transfer.contract.NcContract;
import com.madaex.exchange.ui.finance.transfer.contract.Ncrecord;
import com.madaex.exchange.ui.finance.transfer.contract.SingleOptionsPicker;
import com.madaex.exchange.ui.finance.transfer.presenter.NcPresenter;
import com.wc.widget.dialog.IosDialog;

import java.util.ArrayList;
import java.util.List;
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

public class NCOutFragment extends BaseNetLazyFragment<NcPresenter> implements NcContract.View {


    @BindView(R.id.coinname)
    TextView mCoinname;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.number)
    EditText mNumber;
    @BindView(R.id.submit)
    Button mSubmit;
    Unbinder unbinder;
    @BindView(R.id.password)
    EditText mPassword;
    public static NCOutFragment newInstance(String string) {
        NCOutFragment fragment = null;
        if (fragment == null) {
            fragment = new NCOutFragment();
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ncout;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
//        startActivity(new Intent(mContext, C2CTransationListActivity.class));
    }

    @Override
    public void requestSuccess(PlatRecord commonBean) {

    }

    @Override
    public void requestSuccess(Ncrecord commonBean) {

    }

    @Override
    public void requestSuccess(CoinList commonBean) {
        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.addAll(commonBean.getData());
        SingleOptionsPicker.openOptionsPicker(getActivity(), mOptionsItems, 1, mCoinname);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }


    private String one_xnb = "";

    @OnClick(R.id.submit)
    public void onViewClicked() {
        validate();
    }

    private void validate() {

        if (TextUtils.isEmpty(mCoinname.getText().toString())) {
            ToastUtils.showToast(R.string.selectcurrency);
            return;
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            ToastUtils.showToast(R.string.userID);
            return;
        }

        if (TextUtils.isEmpty(mNumber.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrureturnnumber));
            return;
        } if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(R.string.entrytranspwd);
            return;
        }
        Dialog dialog = new IosDialog.Builder(mContext).setTitle(getString(R.string.Transfer)).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                .setMessage(getString(R.string.moneys) + "GRC" + mNumber.getText().toString().trim()).setMessageColor(ContextCompat.getColor(mContext, R.color.common_red)).setMessageSize(14)
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
                .setPositiveButton(getString(R.string.submit), new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        TreeMap params = new TreeMap<>();
                        params.put("act", ConstantUrl.TRADE_INTERNAL_TRANSFER);
                        params.put("to_id", mName.getText().toString());
                        params.put("coin", mCoinname.getText().toString().trim());
                        params.put("num", mNumber.getText().toString().trim());
                        params.put("paypassword", mPassword.getText().toString().trim());
                        mPresenter.submit(DataUtil.sign(params));
                        dialog.dismiss();
                    }
                }).build();
        dialog.show();
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

    @OnClick(R.id.coinname)
    public void onViewClicked2() {

        showBottomSheetDialog();
    }

    public void showBottomSheetDialog() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_COIN_ENAME_LIST);
        mPresenter.getList(DataUtil.sign(params));

    }
}
