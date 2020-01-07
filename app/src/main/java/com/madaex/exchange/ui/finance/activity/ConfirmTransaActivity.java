package com.madaex.exchange.ui.finance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.TimerText;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.contract.ConfirmTransaContract;
import com.madaex.exchange.ui.finance.presenter.ConfirmTransaPresenter;
import com.madaex.exchange.ui.mine.activity.TransactionPasswordActivity;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  ConfirmTransaActivity.java
 * 时间：  2018/8/23 20:00
 * 描述：  ${TODO}
 */

public class ConfirmTransaActivity extends BaseNetActivity<ConfirmTransaPresenter> implements ConfirmTransaContract.View, TimerText.OnTimerListener {
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.billtype)
    TextView mBilltype;

    @BindView(R.id.tv_code)
    EditText mTvCode;
    @BindView(R.id.tv_fee)
    TextView mTvFee;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.getcode)
    TimerText mGetcode;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.gohome)
    Button mGohome;
    @BindView(R.id.golist)
    Button mGolist;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.statusimg)
    ImageView mStatusimg;
    @BindView(R.id.statusmsg)
    TextView mStatusmsg;
    private String verify_encode = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirmtransa;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        String xnb_name = getIntent().getStringExtra("xnb_name");
        mTitleView.setText(getString(R.string.fu) + xnb_name);
        mTvAmount.setText(getIntent().getStringExtra("number"));
        mBilltype.setText(xnb_name);
        mTvFee.setText(getIntent().getStringExtra("fee"));
        mTvAddress.setText(getIntent().getStringExtra("address"));
        mGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetcode.setOnTimerListener(ConfirmTransaActivity.this);
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.VERIFY_SEND_CODE);

                params.put("type", "3");
                if (SPUtils.getBoolean(Constants.ISMOBILE, true)) {
                    params.put("code_type", "mobile");
                    params.put("mobile", SPUtils.getString(Constants.MOBILE, ""));
                } else {
                    params.put("code_type", "email");
                    params.put("email", SPUtils.getString(Constants.MOBILE, ""));
                }

                mPresenter.getMsgCode(DataUtil.sign(params));
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.gohome, R.id.golist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.gohome:
                Intent intent = new Intent(ConfirmTransaActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.golist:
                Intent intent2 = getIntent();
                intent2.setClass(mContext, TransaListActivity.class);
                intent2.putExtra(ConstantUrl.TYPE, ConstantUrl.TRANS_TYPE_SELLER);
                startActivity(intent2);
                break;
        }
    }

    private void validate() {

        if (TextUtils.isEmpty(mTvCode.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_msg_code));
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_CONFIRM_CASH);
        params.put("verify_code", mTvCode.getText().toString().trim());
        params.put("verify_encode", verify_encode);
        params.put("xnb", getIntent().getStringExtra("xnb"));
        params.put("paypassword", getIntent().getStringExtra("pass"));
        params.put("wallet_type", getIntent().getStringExtra("wallet_type"));
        params.put("addr", getIntent().getStringExtra("address"));
        params.put("num", getIntent().getStringExtra("number"));
//        params.put("fee", getIntent().getStringExtra("fee"));
        params.put("is_platform", getIntent().getBooleanExtra("isCheck", true) == false ? 0 + "" : 1 + "");
        mPresenter.getData(DataUtil.sign(params));
        mSubmit.setClickable(false);
        mSubmit.setBackgroundResource(R.drawable.common_button_shape);
    }

    @Override
    public void requestSuccess(String msg) {
//        ToastUtils.showToast(msg);
        mSubmit.setVisibility(View.GONE);
        mLlStatus.setVisibility(View.VISIBLE);
        mSubmit.setClickable(true);
        mSubmit.setBackgroundResource(R.drawable.common_button_shape);
    }

    @Override
    public void requestError(String msg) {
        mLlContent.setVisibility(View.GONE);
        mLlStatus.setVisibility(View.VISIBLE);
        mStatusimg.setBackgroundResource(R.mipmap.pay_error);
        mStatusmsg.setText(R.string.coinfail);
        mGohome.setVisibility(View.GONE);
        mGolist.setVisibility(View.GONE);
        if (msg.equals("-1")) {
            ToastUtils.showToast(getString(R.string.Completepassword));
            startActivity(new Intent(mContext, TransactionPasswordActivity.class));
        } else {
            ToastUtils.showToast(msg);
        }
        mSubmit.setClickable(true);
        mSubmit.setBackgroundResource(R.drawable.common_button_shape);
//        mLlPwd.setVisibility(View.GONE);
//        mLlCode.setVisibility(View.GONE);
//        mSubmit.setVisibility(View.GONE);
//        mLlMsg.setVisibility(View.VISIBLE);
//        mLlButton.setVisibility(View.VISIBLE);
//        mImgMsg.setImageResource(R.mipmap.pay_error);
//        mTvMsg.setText("付币失败");
    }

    @Override
    public void requestMsgError(String msg) {
        ToastUtils.showToast(msg);
        mSubmit.setClickable(true);
        mSubmit.setBackgroundResource(R.drawable.common_button_shape);
    }

    @Override
    public void sendMsgSuccess(String msg) {
        mGetcode.reset();
        mGetcode.start();
        verify_encode = msg;
        ToastUtils.showToast(getString(R.string.send_msg_already));
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mGetcode.setText(millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        mGetcode.reset();
    }

}
