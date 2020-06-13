package com.madaex.exchange.ui.finance.contracts.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.AppUtils;
import com.madaex.exchange.common.util.DataUtil;
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
 * 类名：  ShifangSellerActivity.java
 * 时间：  2020/6/4 10:14
 * 描述：
 */
public class ShifangSellerActivity extends BaseNetActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.bill_dealtype)
    TextView mBillDealtype;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.submit)
    Button mSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shifangseller;
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
        String mavail = getIntent().getStringExtra("mavail");
        mNumber.setText(mavail);
    }


    @OnClick({R.id.toolbar_left_btn_ll,R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.submit:
                if (AppUtils.isFastClick2()) {
                    if (Double.valueOf(mNumber.getText().toString().trim()) == 0) {
                        ToastUtils.showToast(R.string.quantitynotsufficient);
                    } else {
                        TreeMap params = new TreeMap<>();
                        params.put("act", ConstantUrl.TradeRelease_release);
                        params.put("id", mParcelableExtra.getWallet_id() + "");
                        params.put("num", mNumber.getText().toString().trim() + "");
                        mPresenter.shifangseller(DataUtil.sign(params));
                    }
                }else {
                    ToastUtils.showToast(getString(R.string.Cannotclick));
                }

                break;
        }
    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void requestSuccess(ContractAsset commonBean) {

    }

    @Override
    public void requestSuccess(WalletInfo commonBean) {

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
    public void requestErrorcontract(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
