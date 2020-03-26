package com.madaex.exchange.ui.finance.contracts.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.languagelib.LanguageType;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;
import com.madaex.exchange.ui.finance.contracts.contract.ContractContract;
import com.madaex.exchange.ui.finance.contracts.presenter.ContractPresenter;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  OpenContractActivity.java
 * 时间：  2020/1/9 18:21
 * 描述：
 */
public class OpenContractActivity extends BaseNetActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.phone)
    EditText mPhone;
    @BindView(R.id.agree_register)
    CheckBox mAgreeRegister;
    @BindView(R.id.ll_agree)
    TextView mLlAgree;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_opencontract;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    mImgClear.setVisibility(View.INVISIBLE);
                } else {
                    mImgClear.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initDatas() {
        String mSearchKey = getString(R.string.register_agreement);
        String name = mLlAgree.getText().toString().trim();
        int index = name.indexOf(mSearchKey);
        int len = mSearchKey.length();
        int languageType = SPUtils.getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
        if(languageType==2){
            String temp = name.substring(0, index)
                    + name.substring(index, index + len)
                    + "<font color=\"#FBB03B\">"
                    + name.substring(index + len, name.length()) + "</font>";
            mLlAgree.setText(Html.fromHtml(temp));
        }else if(languageType==1){
            mLlAgree.setText(mSearchKey);
        }

    }
    @OnClick({R.id.img_close, R.id.btn_login, R.id.img_clear, R.id.ll_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.btn_login:
                validate();
                break;
            case R.id.img_clear:
                mPhone.setText("");
                break;
            case R.id.ll_agree:
                Intent intent = new Intent(mContext, LinkWebViewActivity.class);
                intent.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.serviceagreement));
                intent.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.greement);
                startActivity(intent);
                break;
        }
    }
    private void validate() {
        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            ToastUtils.showToast(getString(R.string.invitationcode));
            return;
        }
        if (!mAgreeRegister.isChecked()) {
            ToastUtils.showToast(getString(R.string.agreetmt));
            return;
        }
//        ContractAsset.DataBean.XnbListBean  mParcelableExtra = getIntent().getParcelableExtra("bean");
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.Userlevel_add);
        params.put("market",getIntent().getSerializableExtra("market_name")+ "");
        params.put("invit", mPhone.getText().toString()+ "");
        mPresenter.hua(DataUtil.sign(params));
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
}
