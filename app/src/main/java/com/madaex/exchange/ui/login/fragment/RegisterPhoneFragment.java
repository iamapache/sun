package com.madaex.exchange.ui.login.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.verificationsdk.ui.VerifyActivity;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.RegexUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.util.ViewUtil;
import com.madaex.exchange.common.view.TimerText;
import com.madaex.exchange.ui.MainActivity;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.mine.activity.LinkWebViewActivity;
import com.madaex.exchange.ui.mine.activity.RegionActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目：  madaexchange
 * 类名：  RegisterPhoneFragment.java
 * 时间：  2018/8/23 13:49
 * 描述：  ${TODO}
 */

public class RegisterPhoneFragment extends BaseNetLazyFragment<CommonPresenter> implements CommonContract.View, TimerText.OnTimerListener {
    Unbinder unbinder;
    @BindView(R.id.phone)
    EditText mPhone;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.img_switch_pwd)
    ImageView mImgSwitchPwd;
    @BindView(R.id.code)
    EditText mCode;
    @BindView(R.id.getcode)
    TimerText mGetcode;
    @BindView(R.id.recommend)
    EditText mRecommend;
    @BindView(R.id.agree_register)
    CheckBox mAgreeRegister;
    @BindView(R.id.tv_addresscode)
    TextView mTvAddresscode;
    private String verify_encode = "";
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.view2)
    View mView2;
    @BindView(R.id.view3)
    View mView3;
    @BindView(R.id.view4)
    View mView4;
    @BindView(R.id.ll_agree)
    TextView mLlAgree;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_register;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        ViewUtil.setView(mPhone, mView1);
        ViewUtil.setView(mPassword, mView2);
        ViewUtil.setView(mCode, mView3);
        ViewUtil.setView(mRecommend, mView4);
    }

    @Override
    protected void initDatas() {
        if(getActivity().getIntent().hasExtra("invit")){
            String str = getActivity().getIntent().getStringExtra("invit");
            mRecommend.setText(str);
        }

        mGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()) {
                    mGetcode.setOnTimerListener(RegisterPhoneFragment.this);
                    TreeMap params = new TreeMap<>();
                    params.put("act", ConstantUrl.VERIFY_SEND_CODE);
                    params.put("code_type", "mobile");
                    params.put("type", "1");

                    params.put("mobile", mPhone.getText().toString().trim());
                    params.put("areaCode",  mTvAddresscode.getText().toString().trim());
                    mPresenter.getMsgCode(DataUtil.sign(params));
                }
            }
        });

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
        String mSearchKey = getString(R.string.register_agreement);
        String name = mLlAgree.getText().toString().trim();
        int index = name.indexOf(mSearchKey);
        int len = mSearchKey.length();
        String temp = name.substring(0, index)
                + name.substring(index, index + len)
                + "<font color=\"#ffffff\">"
                + name.substring(index + len, name.length()) + "</font>";
        mLlAgree.setText(Html.fromHtml(temp));
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        ToastUtils.showToast(getString(R.string.registsuccess));
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("show", 4);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void sendMsgSuccess(String msg) {
        mGetcode.reset();
        mGetcode.start();
        verify_encode = msg;
        ToastUtils.showToast(getString(R.string.send_msg_already));
    }

    private int CODE_REQUEST = 0x002;

    @OnClick({R.id.tv_addresscode, R.id.img_clear, R.id.btn_regist, R.id.img_switch_pwd, R.id.ll_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addresscode:
                Intent intent2 = new Intent(getActivity(), RegionActivity.class);
                intent2.putExtra("areastatus", 1);
                startActivityForResult(intent2, CODE_REQUEST);
                break;
            case R.id.img_clear:
                mPhone.setText("");
                break;
            case R.id.btn_regist:
                validate();
                break;
            case R.id.img_switch_pwd:
                mImgSwitchPwd.setSelected(!mImgSwitchPwd.isSelected());
                switchPassword(mPassword, mImgSwitchPwd);
                break;
            case R.id.ll_agree:
                Intent intent = new Intent(mContext, LinkWebViewActivity.class);
                intent.putExtra(LinkWebViewActivity.WEB_TITLE, getString(R.string.serviceagreement));
                intent.putExtra(LinkWebViewActivity.WEB_URL, Constant.HTTP + ConstantUrl.greement);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && requestCode == CODE_REQUEST) {
            String bean = data.getStringExtra("data");
            mTvAddresscode.setText("+"+bean);
        }

    }

    private void validate() {
        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_phone));
            return;
        }
        if( mTvAddresscode.getText().toString().trim().equals("+86")){
            if (!RegexUtil.isMobileNumber(mPhone.getText().toString())) {
                ToastUtils.showToast(getString(R.string.input_phone_ok));
                return;
            }
        }else {
            if (18>mPhone.getText().toString().length()||mPhone.getText().toString().length()<5) {
                ToastUtils.showToast(getString(R.string.input_phone_ok));
                return;
            }
        }

        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_pwd));
            return;
        }
        if (!RegexUtil.isMoblePATTERN(mPassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.passwordincorrect));
            return;
        }
        if (TextUtils.isEmpty(mCode.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_msg_code));
            return;
        }
        if (!mAgreeRegister.isChecked()) {
            ToastUtils.showToast(getString(R.string.agreetmt));
            return;
        }

        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_REGISTER);
        params.put("verify_code", mCode.getText().toString().trim());
        params.put("verify_encode", verify_encode);
        params.put("regtype", "mobile");
        params.put("areaCode",  mTvAddresscode.getText().toString().trim());
        params.put("mobile", mPhone.getText().toString().trim());
        params.put("password", mPassword.getText().toString().trim());
        params.put("invit", mRecommend.getText().toString().trim());
        mPresenter.getData2(DataUtil.sign2(params));
//        VerifyActivity.startSimpleVerifyUI(getActivity(), VerifyType.NOCAPTCHA, "0335", null, new IActivityCallback() {
//            @Override
//            //返回按钮回调
//            public void onNotifyBackPressed() {
////                TreeMap params = new TreeMap<>();
////                params.put("act", ConstantUrl.USER_REGISTER);
////                params.put("verify_code", mCode.getText().toString().trim());
////                params.put("verify_encode", verify_encode);
////                params.put("regtype", "mobile");
////                params.put("areaCode",  mTvAddresscode.getText().toString().trim());
////                params.put("mobile", mPhone.getText().toString().trim());
////                params.put("password", mPassword.getText().toString().trim());
////                params.put("invit", mRecommend.getText().toString().trim());
////                mPresenter.getData(DataUtil.sign(params));
//            }
//
//            @Override
//            public void onResult(int retInt, Map code) {
//                verifyDidFinishedWithResult(retInt, code);
//            }
//        });

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
                params.put("act", ConstantUrl.USER_REGISTER);
                params.put("verify_code", mCode.getText().toString().trim());
                params.put("verify_encode", verify_encode);
                params.put("regtype", "mobile");
                params.put("areaCode",  mTvAddresscode.getText().toString().trim());
                params.put("mobile", mPhone.getText().toString().trim());
                params.put("password", mPassword.getText().toString().trim());
                params.put("invit", mRecommend.getText().toString().trim());
                mPresenter.getData2(DataUtil.sign2(params));
                break;

            case VerifyActivity.VERIFY_FAILED:
                break;
        }
    }

    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInput() {
        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_phone));
            return false;
        }
        if( mTvAddresscode.getText().toString().trim().equals("+86")){
            if (!RegexUtil.isMobileNumber(mPhone.getText().toString())) {
                ToastUtils.showToast(getString(R.string.input_phone_ok));
                return false;
            }
        }else {
            if (18>mPhone.getText().toString().length()||mPhone.getText().toString().length()<5) {
                ToastUtils.showToast(getString(R.string.input_phone_ok));
                return false;
            }
        }
        return true;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mGetcode.setText(millisUntilFinished / 1000 + getString(R.string.second));
    }

    @Override
    public void onFinish() {
        mGetcode.reset();
    }
}
