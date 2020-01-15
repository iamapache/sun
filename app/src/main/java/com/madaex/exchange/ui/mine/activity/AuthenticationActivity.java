package com.madaex.exchange.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.common.CommonContract;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.CommonPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  AuthenticationActivity.java
 * 时间：  2018/8/24 14:39
 * 描述：  ${TODO}
 */

public class AuthenticationActivity extends BaseNetActivity<CommonPresenter> implements CommonContract.View {
    @BindView(R.id.tv_username)
    EditText mTvUsername;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.user)
    EditText mUser;
    @BindView(R.id.tv_cardnumber)
    EditText mTvCardnumber;
    @BindView(R.id.address)
    EditText mAddress;
    @BindView(R.id.city)
    EditText mCity;
    @BindView(R.id.postcode)
    EditText mPostcode;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.ll_1)
    LinearLayout mLl1;
    @BindView(R.id.ll_2)
    LinearLayout mLl2;
    private int CODE_REQUEST = 0x002;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
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

    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        Event event = new Event();
        event.setCode(Constants.MINE);
        EventBus.getDefault().post(event);
        finish();
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
    public void sendMsgSuccess(String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_location, R.id.submit, R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:

                    Intent intent = new Intent(AuthenticationActivity.this, RegionActivity.class);
                    startActivityForResult(intent, CODE_REQUEST);

                break;
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    private void validate() {
        if (TextUtils.isEmpty(mTvUsername.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entryidname));
            return;
        }
        if (TextUtils.isEmpty(mTvCardnumber.getText().toString())) {
            ToastUtils.showToast(getString(R.string.entryidnumber));
            return;
        }
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_REALNAME);
        params.put("country", mTvLocation.getText().toString().trim());
        params.put("truename", mTvUsername.getText().toString().trim());
        params.put("idcard", mTvCardnumber.getText().toString().trim());
//        params.put("firstname", mUser.getText().toString().trim());
//        params.put("lastname", mName.getText().toString().trim());
//        params.put("address", mAddress.getText().toString().trim());
//        params.put("city", mCity.getText().toString().trim());
//        params.put("postalcode", mPostcode.getText().toString().trim());
        mPresenter.getData(DataUtil.sign2(params));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            String bean = data.getStringExtra("data");
            mTvLocation.setText(bean);
            if(bean.equals(getString(R.string.china))){
                mLl1.setVisibility(View.GONE);
                mLl2.setVisibility(View.GONE);
            }else {
                mLl1.setVisibility(View.VISIBLE);
                mLl2.setVisibility(View.VISIBLE);
            }
        }

    }
}
