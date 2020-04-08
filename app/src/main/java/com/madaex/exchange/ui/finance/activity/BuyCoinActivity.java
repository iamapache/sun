package com.madaex.exchange.ui.finance.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.ClipboardUtil;
import com.madaex.exchange.common.util.QrcodeUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.bean.QrCode;
import com.madaex.exchange.ui.finance.bean.Recharge;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  exchange
 * 类名：  BuyCoinActivity.java
 * 时间：  2018/8/22 18:09
 * 描述：  ${TODO}
 */

public class BuyCoinActivity extends BaseNetLazyFragment<AssetPresenter> implements AssetContract.View {
    @BindView(R.id.img_code)
    ImageView mImgCode;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.plain)
    TextView mPlain;
    @BindView(R.id.tv_copycode)
    TextView mTvCopycode;
    private String walletaddress = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_buycoin;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {

    }
    public static BuyCoinActivity newInstance(boolean flag,String xnb_name, String address) {
        BuyCoinActivity fragment = null;
        if (fragment == null) {
            fragment = new BuyCoinActivity();
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", flag);
        bundle.putString("xnb_name", xnb_name);
        bundle.putString("address", address);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    @SuppressLint("StringFormatInvalid")
    protected void initDatas() {
        String xnb_name = getArguments().getString("xnb_name");
        walletaddress = getArguments().getString("address");
        boolean flag = getArguments().getBoolean("flag");
        if(flag){
            mPlain.setVisibility(View.VISIBLE);
        }else {
            mPlain.setVisibility(View.GONE);

        }
//        mTitleView.setText(getString(R.string.shou) + xnb_name);

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


    @OnClick({R.id.img_code, R.id.tv_copycode, R.id.ll_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_code:
                ClipboardUtil.setText(mContext, walletaddress);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
            case R.id.tv_copycode:
                ClipboardUtil.setText(mContext, walletaddress);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
            case R.id.ll_code:
                ClipboardUtil.setText(mContext, walletaddress);
                ToastUtils.showToast(getString(R.string.copyaddress));
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

    @Override
    public void requestRecharge(Recharge commonBean) {

    }

    @Override
    protected void lazyLoad() {

    }
}
