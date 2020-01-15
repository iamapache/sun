package com.madaex.exchange.ui.finance.contracts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;
import com.madaex.exchange.ui.finance.contracts.contract.ContractContract;
import com.madaex.exchange.ui.finance.contracts.presenter.ContractPresenter;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  HuaActivity.java
 * 时间：  2020/1/9 22:22
 * 描述：
 */
public class HuaActivity extends BaseNetActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.Coin)
    TextView mCoin;
    @BindView(R.id.Contract)
    TextView mContract;
    @BindView(R.id.accounts)
    EditText mAccounts;
    @BindView(R.id.usablebalance)
    TextView mUsablebalance;
    @BindView(R.id.all)
    TextView mAll;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    private String type ="coin_to_contract";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hua;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    private ContractAsset.DataBean.XnbListBean mParcelableExtra;

    @Override
    protected void initDatas() {
        mParcelableExtra = getIntent().getParcelableExtra("bean");
        mTitleView.setText(mParcelableExtra.getXnb_name()+getString(R.string.Transferh));
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.Trade_transfer_wallet_info);
        params.put("wallet_id", mParcelableExtra.getWallet_id() + "");
        mPresenter.wallet_info(DataUtil.sign(params));
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_btn_ll:
                Intent intent = getIntent();
                intent.setClass(mContext, HuaRecordActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean flag = true;
    @OnClick({R.id.imageView, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                if(flag){
                    flag=false;
                    type ="contract_to_coin";
                    mCoin.setText(getString(R.string.Contracthy));
                    mContract.setText(getString(R.string.Coin));
                    mUsablebalance.setText(commonBean.getData().getGen_wallet_id()+"");
                }else {
                    flag=true;
                    type ="coin_to_contract";
                    mCoin.setText(getString(R.string.Coin));
                    mContract.setText(getString(R.string.Contracthy));
                    mUsablebalance.setText(commonBean.getData().getCon_assets()+"");
                }
                break;
            case R.id.submit:
                validate();
                break;
        }
    }
    private void validate() {
        if (TextUtils.isEmpty(mAccounts.getText().toString())) {
            ToastUtils.showToast(getString(R.string.pTransferaccounts));
            return;
        }
        if(EmptyUtils.isNotEmpty(commonBean)){
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.Trade_contract_transfer);
            params.put("gen_wallet_id", commonBean.getData().getGen_wallet_id()+ "");
            params.put("con_wallet_id", commonBean.getData().getCon_wallet_id()+ "");
            params.put("type", type+ "");
            params.put("num", mAccounts.getText().toString()+ "");
            mPresenter.hua(DataUtil.sign(params));
        }

    }
    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String commonBean) {
        ToastUtils.showToast(commonBean);
    }

    @Override
    public void requestSuccess(ContractAsset commonBean) {

    }
    WalletInfo commonBean;
    @Override
    public void requestSuccess(WalletInfo commonBean) {
        this.commonBean = commonBean;
        mUsablebalance.setText(commonBean.getData().getCon_assets());
        mAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAccounts.setText(commonBean.getData().getCon_assets());
            }
        });
    }

    @Override
    public void requestSuccess(USDTinfo commonBean) {

    }

    @Override
    public void requestSuccess(AlscInfo commonBean) {

    }

    @Override
    public void requestSuccess(Bills commonBean) {

    }

    @Override
    public void requestErrorcontract(String s) {
        ToastUtils.showToast(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
