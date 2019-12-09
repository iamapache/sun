package com.madaex.exchange.ui.finance.transfer.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.transfer.contract.CoinList;
import com.madaex.exchange.ui.finance.transfer.contract.NcContract;
import com.madaex.exchange.ui.finance.transfer.contract.Ncrecord;
import com.madaex.exchange.ui.finance.transfer.presenter.NcPresenter;
import com.madaex.exchange.view.GlideImgManager;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sunn
 * 类名：  NcActivity.java
 * 时间：  2019/5/23 9:50
 * 描述：  ${TODO}
 */
public class NcActivity extends BaseNetActivity<NcPresenter> implements NcContract.View{
    @BindView(R.id.billaddr)
    TextView mBilladdr;
    @BindView(R.id.copy)
    TextView mCopy;
    @BindView(R.id.ll_addr)
    LinearLayout mLlAddr;
    @BindView(R.id.number)
    EditText mNumber;
    @BindView(R.id.getnumber)
    TextView mGetnumber;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.submit)
    Button mSubmit;
    private List<String> mDatalist = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nc;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mGetnumber.setText(editable.toString());
            }
        });
    }

    @Override
    protected void initDatas() {

    }


    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String commonBean) {
        ToastUtils.showToast(commonBean);
        finish();
    }

    @Override
    public void requestSuccess(PlatRecord commonBean) {

    }

    @Override
    public void requestSuccess(Ncrecord commonBean) {

    }

    @Override
    public void requestSuccess(CoinList commonBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img, R.id.submit, R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll, R.id.copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img:
                Intent intent = new Intent(NcActivity.this, PhotoSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("limit", 1);//number是选择图片的数量
                startActivityForResult(intent, 20);
                break;
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_btn_ll:
                startActivity(new Intent(mContext, NcListActivity.class));
                break;
            case R.id.copy:
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText(null, mBilladdr.getText().toString());
                clipboard.setPrimaryClip(clipData);
                ToastUtils.showToast(getString(R.string.copyaddress));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 20:
                if (data != null) {
                    List<String> photos = (List<String>) data.getExtras().getSerializable("photos");
                    //path是选择拍照或者图片的地址数组
                    //处理代码
                    mDatalist.clear();
                    mDatalist.addAll(photos);
                    GlideImgManager.loadImage(mContext, new File(mDatalist.get(0)), mImg);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void validate() {
        if (TextUtils.isEmpty(mNumber.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entrureturnnumber));
            return;
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(R.string.entrytranspw);
            return;
        }
        if (TextUtils.isEmpty(mCode.getText().toString())) {
            ToastUtils.showToast(R.string.NCwalletaddress);
            return;
        }
        if (EmptyUtils.isEmpty(mDatalist)) {
            ToastUtils.showToast(R.string.selectpicture);
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.COIN_COIN_CZ_ADD);
        params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
        params.put("coinname", "SNRC");
        params.put("address", mBilladdr.getText().toString().trim());
        params.put("num", mNumber.getText().toString().trim());
        params.put("pwd", mPassword.getText().toString().trim());
        params.put("mark", mCode.getText().toString().trim());
        mPresenter.saveUserHeadImage(DataUtil.sign(params), (ArrayList<String>) mDatalist);
    }
}
