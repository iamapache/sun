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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.madaex.dialog.ActionSheetDialog;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.ClipboardUtil;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.address.activity.AddressListActivity;
import com.madaex.exchange.ui.finance.address.activity.ScanActivity;
import com.madaex.exchange.view.EditInputFilter;
import com.wc.widget.dialog.IosDialog;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 项目：  madaexchange
 * 类名：  BuyBillActivity.java
 * 时间：  2018/8/31 17:29
 * 描述：  ${TODO}
 */

public class BuyBillActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View {
    @BindView(R.id.billaddr)
    TextView mBilladdr;
    @BindView(R.id.tv_address)
    EditText mTvAddress;
    @BindView(R.id.tv_number)
    EditText mTvNumber;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.gohome)
    Button mGohome;
    @BindView(R.id.golist)
    Button mGolist;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.statusimg)
    ImageView mStatusimg;
    @BindView(R.id.statusmsg)
    TextView mStatusmsg;
    @BindView(R.id.submit)
    Button mSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buybill;
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
    }

    @Override
    protected void initDatas() {
        String str = getIntent().getStringExtra("xnb");
        String xnb_name = getIntent().getStringExtra("xnb_name");
        mBilladdr.setText(getIntent().getStringExtra("address"));
        mTitleView.setText(getString(R.string.chong) + xnb_name + getString(R.string.apply));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_address, R.id.submit, R.id.toolbar_right_tv_ll, R.id.toolbar_left_btn_ll, R.id.gohome, R.id.golist, R.id.ll_addr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                showCustomDialog();
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_tv_ll:
                Intent intent = getIntent();
                intent.setClass(mContext, TransaListActivity.class);
                intent.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_BUY);
                startActivity(intent);
                break;
            case R.id.gohome:
                Intent intent3 = new Intent(BuyBillActivity.this, MainActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent3);
                break;
            case R.id.golist:
                Intent intent2 = getIntent();
                intent2.setClass(mContext, TransaListActivity.class);
                intent2.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_SELLER);
                startActivity(intent2);
                break;
            case R.id.ll_addr:
                Intent intent4 = getIntent();
                intent4.setClass(mContext, BuyCoinActivity.class);
                startActivity(intent4);
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(mTvAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.selectadd));
            return;
        }
        if (TextUtils.isEmpty(mTvNumber.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.entrymoney));
            return;
        }
        if (Integer.valueOf(mTvNumber.getText().toString().trim()) < 10) {
            ToastUtils.showToast(getString(R.string.priceno10));
            return;
        }

        Dialog dialog = new IosDialog.Builder(this).setTitle(getString(R.string.confirmsubmit)).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                .setMessage(getString(R.string.money) + mTvNumber.getText().toString().trim()).setMessageColor(ContextCompat.getColor(mContext, R.color.common_red)).setMessageSize(14)
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
                        params.put("act", ConstantUrl.TRADE_CONFIRM_RECHARGE);
                        params.put("xnb", getIntent().getStringExtra("xnb"));
                        params.put("recharge_addr", getIntent().getStringExtra("address"));
                        params.put("num", mTvNumber.getText().toString().trim());
                        params.put("cash_addr", mTvAddress.getText().toString().trim());
                        mPresenter.getData(DataUtil.sign2(params));
                        dialog.dismiss();
                    }
                }).build();
        dialog.show();
    }

    private void showCustomDialog() {
        new ActionSheetDialog(BuyBillActivity.this)
                .builder()
                .setTitle(getString(R.string.Turnsaddress))
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(getString(R.string.selectadd), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = getIntent();
                                intent.setClass(mContext, AddressListActivity.class);
                                intent.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_BUY);
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

    private void openScanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(ScanActivity.class);
        intentIntegrator.initiateScan();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
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
                            mTvAddress.setText(code);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (!TextUtils.isEmpty(code)) {
                                mTvAddress.setText(code);
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

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        mSubmit.setVisibility(View.GONE);
        mLlStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
        mSubmit.setVisibility(View.GONE);
        mLlStatus.setVisibility(View.VISIBLE);
        mStatusimg.setBackgroundResource(R.mipmap.pay_error);
        mStatusmsg.setText(R.string.coinfail);
        mGohome.setVisibility(View.GONE);
        mGolist.setVisibility(View.GONE);
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void sendMsgSuccess(String msg) {

    }
}
