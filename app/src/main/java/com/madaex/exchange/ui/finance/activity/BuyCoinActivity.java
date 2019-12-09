package com.madaex.exchange.ui.finance.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.ClipboardUtil;
import com.madaex.exchange.common.util.QrcodeUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.bean.QrCode;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  exchange
 * 类名：  BuyCoinActivity.java
 * 时间：  2018/8/22 18:09
 * 描述：  ${TODO}
 */

public class BuyCoinActivity extends BaseNetActivity<AssetPresenter> implements AssetContract.View {
    @BindView(R.id.img_code)
    ImageView mImgCode;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.plain)
    TextView mPlain;
    @BindView(R.id.tv_copycode)
    TextView mTvCopycode;
    private String walletaddress = "";

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buycoin;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }


    @Override
    @SuppressLint("StringFormatInvalid")
    protected void initDatas() {
        String str = getIntent().getStringExtra("xnb");
        String xnb_name = getIntent().getStringExtra("xnb_name");
        walletaddress = getIntent().getStringExtra("address");
        mTitleView.setText(getString(R.string.shou) + xnb_name);

        QrCode qrCode = new QrCode();
        qrCode.setAddress(walletaddress);
        qrCode.setXnb_name(xnb_name);
        mImgCode.setImageBitmap(QrcodeUtil.createImage(mContext, walletaddress, 230));
        mTvAddress.setText(walletaddress);
        String sAgeFormat1 = getResources().getString(R.string.mtjieshao);
        String sFinal1 = String.format(sAgeFormat1, xnb_name, xnb_name, xnb_name);
        mPlain.setText(sFinal1);

        String sAgeFormat2 = getResources().getString(R.string.copymyaddress);
        String sFinal2 = String.format(sAgeFormat2, xnb_name);
        mTvCopycode.setText(sFinal2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_code, R.id.tv_copycode, R.id.ll_code, R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_code:
                ClipboardUtil.setText(BuyCoinActivity.this, walletaddress);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
            case R.id.tv_copycode:
                ClipboardUtil.setText(BuyCoinActivity.this, walletaddress);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
            case R.id.ll_code:
                ClipboardUtil.setText(BuyCoinActivity.this, walletaddress);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(Asset commonBean) {

    }
}
