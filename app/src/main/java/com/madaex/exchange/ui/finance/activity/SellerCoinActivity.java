package com.madaex.exchange.ui.finance.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madaex.dialog.ActionSheetDialog;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.ClipboardUtil;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.address.activity.AddressListActivity;
import com.madaex.exchange.ui.finance.address.activity.ScanActivity;
import com.madaex.exchange.ui.finance.bean.SellerCoin;
import com.madaex.exchange.ui.finance.bean.TransaList;
import com.madaex.exchange.ui.finance.contract.SellerCoinContract;
import com.madaex.exchange.ui.finance.presenter.SellerCoinPresenter;
import com.madaex.exchange.view.EditInputFilter;
import com.wc.widget.dialog.IosDialog;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 项目：  exchange
 * 类名：  SellerCoinActivity.java
 * 时间：  2018/8/22 18:32
 * 描述：  ${TODO}
 */

public class SellerCoinActivity extends BaseNetActivity<SellerCoinPresenter> implements SellerCoinContract.View {
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.tv_address)
    EditText mTvAddress;
    @BindView(R.id.tv_kgf)
    TextView mTvKgf;
    @BindView(R.id.tv_number)
    EditText mTvNumber;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    SellerCoin mSellerCoin;
    @BindView(R.id.togglebutton)
    ToggleButton mTogglebutton;
    @BindView(R.id.cointype)
    TextView mCointype;
    @BindView(R.id.ll_neibu)
    LinearLayout mLlNeibu;
    @BindView(R.id.submit)
    Button mSubmit;
    private int CODE_REQUEST = 0x002;
    private boolean isCheck = true;
    private double zc_min = 0;
    private double zc_max = 10000;
    @BindView(R.id.tv_transpassword)
    EditText mTvTranspassword;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sellercoin;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mTvAddress.setCursorVisible(false);
        mTvAddress.setFocusable(false);
        mTvAddress.setFocusableInTouchMode(false);
        mTvNumber.setFilters(new InputFilter[]{new EditInputFilter(100000)});
        mTogglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCheck = true;
                } else {
                    isCheck = false;
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        String str = getIntent().getStringExtra("xnb");
        String xnb_name = getIntent().getStringExtra("xnb_name");
        String wallet_type = getIntent().getStringExtra("wallet_type");
        mTitleView.setText(getString(R.string.fu) + xnb_name);
        mCointype.setText(xnb_name);
        if (!xnb_name.equals("GRC")) {
            mLlNeibu.setVisibility(View.GONE);
            mTogglebutton.setChecked(false);
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_CASH_COIN);
        params.put("xnb", str);
        params.put("wallet_type", wallet_type);
        params.put("coin_id", getIntent().getIntExtra("coin_id",0));
        mPresenter.getData(DataUtil.sign(params));
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_address, R.id.tv_kgf, R.id.toolbar_left_btn_ll, R.id.toolbar_right_tv_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                showCustomDialog();
                break;
            case R.id.tv_kgf:
                if (EmptyUtils.isNotEmpty(mSellerCoin)) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, FeeActivity.class);
                    intent.putStringArrayListExtra("bean", (ArrayList<String>) mSellerCoin.getData().getXnb_fee());
                    startActivityForResult(intent, CODE_REQUEST);
                }

                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_tv_ll:
                Intent intent = getIntent();
                intent.setClass(mContext, TransaListActivity.class);
                intent.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_SELLER);
                startActivity(intent);
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(mTvAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.selectadd));
            return;
        }
        if (TextUtils.isEmpty(mTvNumber.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.entrureturnnumber));
            return;
        }
//        if (Double.valueOf(mTvAmount.getText().toString().trim())<Double.valueOf(mTvNumber.getText().toString().trim())) {
//            ToastUtils.showToast(getString(R.string.Pcorrectamount));
//            return;
//        }
//        if (zc_min > Double.valueOf(mTvNumber.getText().toString().trim())) {
//            ToastUtils.showToast(getString(R.string.Pcorrectamount));
//            return;
//        }
//        if (Double.valueOf(mTvNumber.getText().toString().trim()) > zc_max) {
//            ToastUtils.showToast(getString(R.string.Pcorrectamount));
//            return;
//        }
        if (TextUtils.isEmpty(mTvTranspassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrytranspwd));
            return;
        }
//        if (!SPUtils.getBoolean(Constants.has_paypassword, false)) {
//            ToastUtils.showToast(getString(R.string.passwordfirst));
//            return;
//        }
        String xnb_name = getIntent().getStringExtra("xnb_name");
        Dialog dialog = new IosDialog.Builder(this).setTitle(getString(R.string.oksellerbill) + "?").setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                .setMessage(getString(R.string.money) + xnb_name + mTvNumber.getText().toString().trim()).setMessageColor(ContextCompat.getColor(mContext, R.color.common_red)).setMessageSize(14)
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
                .setPositiveButton(getString(R.string.oksellerbill), new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        Intent intent = getIntent();
                        intent.setClass(mContext, ConfirmTransaActivity.class);
                        intent.putExtra("number", mTvNumber.getText().toString().trim());
                        intent.putExtra("address", mTvAddress.getText().toString().trim());
                        intent.putExtra("pass", mTvTranspassword.getText().toString().trim());
//                        intent.putExtra("fee", mTvKgf.getText().toString().trim());
                        intent.putExtra("isCheck", isCheck);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                }).build();
        dialog.show();

    }

    private void showCustomDialog() {
        new ActionSheetDialog(SellerCoinActivity.this)
                .builder()
                .setTitle(getString(R.string.buycoinadd))
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(getString(R.string.selectaddress), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = getIntent();
                                intent.setClass(mContext, AddressListActivity.class);
                                intent.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_SELLER);
                                startActivityForResult(intent, 100);
                            }
                        })
                .addSheetItem(getString(R.string.scancode), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openScanCode();
                            }
                        })
                .addSheetItem(getString(R.string.clipboard), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                String walletaddress = (String) ClipboardUtil.getText(mContext);
                                mTvAddress.setText(walletaddress);
                            }
                        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            String wallets = data.getStringExtra("fee");
            mTvKgf.setText(wallets + "%");
        } else if (resultCode == RESULT_OK
                && requestCode == 100) {
            String address = data.getStringExtra("address");
            mTvAddress.setText(address);
        } else {
            try {
                if (resultCode == RESULT_OK) {
                    if (requestCode == REQUEST_CODE) {
                        String code = data.getStringExtra("data");
                        try {
                            if (TextUtils.isEmpty(code)) {
                                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                                if (result != null && !TextUtils.isEmpty(result.getContents())) {
                                    code = result.getContents();
                                }
                            }
                            if (!TextUtils.isEmpty(code)) {
                                if (code.contains(":")) {
                                    mTvAddress.setText(code.split(":")[1]);
                                } else {
                                    mTvAddress.setText(code);
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            if (!TextUtils.isEmpty(code)) {
                                if (code.contains(":")) {
                                    mTvAddress.setText(code.split(":")[1]);
                                } else {
                                    mTvAddress.setText(code);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showToast(getString(R.string.qrcode_error));
            }
        }
    }

    private void openScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void requestError(String msg) {

    }


    @Override
    public void requestSuccess(SellerCoin commonBean) {
        mSellerCoin = new SellerCoin();
        mSellerCoin = commonBean;
        mTvAmount.setText(commonBean.getData().getXnb_num());
        mTvKgf.setText(commonBean.getData().getXnb_fee().get(0) + "%");
        zc_min = Double.valueOf(commonBean.getData().getZc_min());
        zc_max = Double.valueOf(commonBean.getData().getZc_max());
    }

    @Override
    public void requestSuccess(TransaList commonBean) {

    }
}
