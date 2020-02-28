package com.madaex.exchange.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.verificationsdk.ui.IActivityCallback;
import com.alibaba.verificationsdk.ui.VerifyActivity;
import com.alibaba.verificationsdk.ui.VerifyType;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.util.ViewUtil;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.mine.activity.RegionActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  RegisterActivity.java
 * 时间：  2018/8/23 13:39
 * 描述：  ${TODO}
 */

public class LoginActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View {

    @BindView(R.id.phone)
    EditText mPhone;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.img_switch_pwd)
    ImageView mImgSwitchPwd;
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.view2)
    View mView2;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.tv_addresscode)
    TextView mTvAddresscode;
    @BindView(R.id.ll_code)
    LinearLayout mLlCode;
    private int CODE_REQUEST = 0x002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        ViewUtil.setView(mPhone, mView1);
        ViewUtil.setView(mPassword, mView2);
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
//                    mLlCode.setVisibility(View.GONE);
                } else {
                    mImgClear.setVisibility(View.VISIBLE);
                    if (editable.toString().contains("@")) {
//                        mLlCode.setVisibility(View.GONE);
                    }else {
//                        mLlCode.setVisibility(View.GONE);
                    }
                }
            }
        });
        mImgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone.setText("");
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && requestCode == CODE_REQUEST) {
            String bean = data.getStringExtra("data");
            mTvAddresscode.setText("+" + bean);
        }

    }

    @OnClick({R.id.tv_addresscode, R.id.img_close, R.id.img_switch_pwd, R.id.btn_login, R.id.tv_register, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addresscode:
                Intent intent2 = new Intent(mContext, RegionActivity.class);
                intent2.putExtra("areastatus", 1);
                startActivityForResult(intent2, CODE_REQUEST);
                break;
            case R.id.img_close:
                finish();
                break;
            case R.id.img_switch_pwd:
                mImgSwitchPwd.setSelected(!mImgSwitchPwd.isSelected());
                switchPassword(mPassword, mImgSwitchPwd);
                break;
            case R.id.btn_login:
                validate();
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
        }
    }

    private void validate() {
        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            ToastUtils.showToast(getString(R.string.pleaseentryaccount));
            return;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_pwd));
            return;
        }
        VerifyActivity.startSimpleVerifyUI(LoginActivity.this, VerifyType.NOCAPTCHA, "0335", null, new IActivityCallback() {
            @Override
            //返回按钮回调
            public void onNotifyBackPressed() {
//                TreeMap params = new TreeMap<>();
//                params.put("act", ConstantUrl.USER_LOGIN);
//                params.put("username", mPhone.getText().toString().trim());
//                params.put("areaCode",  mTvAddresscode.getText().toString().trim());
//                params.put("password", mPassword.getText().toString().trim());
//                mPresenter.getData(DataUtil.sign(params));
            }

            @Override
            public void onResult(int retInt, Map code) {
                LoginActivity.this.verifyDidFinishedWithResult(retInt, code);
            }
        });
    }

    /**
     * 验签回调功能函数
     *
     * @param retInt 返回码
     * @param code   返回内容
     */
    public void verifyDidFinishedWithResult(int retInt, Map code) {
        switch (retInt) {
            case VerifyActivity.VERIFY_SUCC:
                TreeMap params = new TreeMap<>();
                params.put("act", ConstantUrl.USER_LOGIN);
                params.put("username", mPhone.getText().toString().trim());
                params.put("areaCode", mTvAddresscode.getText().toString().trim());
                params.put("password", mPassword.getText().toString().trim());
                mPresenter.getData2(DataUtil.sign2(params));
                break;

            case VerifyActivity.VERIFY_FAILED:
                Log.e("sb", (String) code.get("errorCode"));
                Log.e("sb", (String) (null != code.get("errorMsg") ? code.get("errorMsg") : ""));
                break;
        }
    }


    @Override
    public void requestSuccess(String msg) {



    }

    @Override
    public void requestSuccess2(CommonDataBean.DataBean data) {
        SPUtils.putString(Constants.TOKEN, data.getToken());
        SPUtils.putString(Constants.USER_ID, data.getUser_id());
        Event event = new Event();
        event.setCode(Constants.LOGINSUCCESS);
        EventBus.getDefault().post(event);
        ToastUtils.showToast(getString(R.string.loginsuccess));
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void sendMsgSuccess(String msg) {

    }
}
