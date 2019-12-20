package com.madaex.exchange.ui.login.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.RegexUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.util.ViewUtil;
import com.madaex.exchange.common.view.TimerText;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;

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

public class ForgetPasswordPhoneFragment extends BaseNetLazyFragment<CommonPresenter> implements CommonContract.View, TimerText.OnTimerListener {
    Unbinder unbinder;
    @BindView(R.id.tv_addresscode)
    TextView mTvAddresscode;
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
    private String verify_encode="";
    @BindView(R.id.view1)
    View mView1;
    @BindView(R.id.view2)
    View mView2;
    @BindView(R.id.view3)
    View mView3;
    @BindView(R.id.view4)
    View mView4;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phone_forget;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        ViewUtil.setView(mPhone,mView1);
        ViewUtil.setView(mPassword,mView2);
        ViewUtil.setView(mRecommend,mView3);
        ViewUtil.setView(mCode,mView4);
    }

    @Override
    protected void initDatas() {
        mGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput()) {
                    mGetcode.setOnTimerListener(ForgetPasswordPhoneFragment.this);
                    TreeMap params = new TreeMap<>();
                    params.put("act", ConstantUrl.VERIFY_SEND_CODE);
                    params.put("code_type", "mobile");
                    params.put("type", "2");
                    params.put("areaCode",  mTvAddresscode.getText().toString().trim());
                    params.put("mobile", mPhone.getText().toString().trim());
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
                if (TextUtils.isEmpty(editable.toString()) ) {
                    mImgClear.setVisibility(View.INVISIBLE);
                } else {
                    mImgClear.setVisibility(View.VISIBLE);
                }
            }
        });
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
        ToastUtils.showToast(msg);
        getActivity().finish();
    }

    @Override
    public void requestSuccess2(CommonDataBean.DataBean data) {

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
//        SPUtils.putString(Constants.TOKEN, msg);
//        startActivity(new Intent(mContext, MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void sendMsgSuccess(String msg) {
        mGetcode.reset();
        mGetcode.start();
        verify_encode = msg;
        ToastUtils.showToast(getString(R.string.send_msg_already));
    }


    private int CODE_REQUEST = 0x002;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && requestCode == CODE_REQUEST) {
            String bean = data.getStringExtra("data");
            mTvAddresscode.setText("+"+bean);
        }

    }



    @OnClick({R.id.tv_addresscode, R.id.img_clear, R.id.btn_regist, R.id.img_switch_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addresscode:

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
        }
    }

    private void validate() {
        if(TextUtils.isEmpty(mPhone.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_phone));
            return ;
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
        if(TextUtils.isEmpty(mPassword.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_pwd));
            return ;
        }
        if(TextUtils.isEmpty(mCode.getText().toString())) {
            ToastUtils.showToast(getString(R.string.input_msg_code));
            return ;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_FINDPASS);
        params.put("verify_code", mCode.getText().toString().trim());
        params.put("verify_encode", verify_encode);
        params.put("find_type", "mobile");
        params.put("mobile", mPhone.getText().toString().trim());
        params.put("password", mPassword.getText().toString().trim());
        params.put("areaCode",  mTvAddresscode.getText().toString().trim());
        mPresenter.getData(DataUtil.sign2(params));
    }

    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInput() {
        if(TextUtils.isEmpty(mPhone.getText().toString())) {
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
        mGetcode.setText(millisUntilFinished/1000 + getString(R.string.second));
    }

    @Override
    public void onFinish() {
        mGetcode.reset();
    }
}
