package com.madaex.exchange.ui.finance.c2c.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.c2c.contract.AppealContract;
import com.madaex.exchange.ui.finance.c2c.presenter.AppealPresenter;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  ImageActivity.java
 * 时间：  2019/5/15 16:36
 * 描述：  ${TODO}
 */
public class AppealActivity extends BaseNetActivity<AppealPresenter> implements AppealContract.View {

    @BindView(R.id.cb_one)
    CheckBox mCbOne;
    @BindView(R.id.cb_two)
    CheckBox mCbTwo;
    @BindView(R.id.cb_three)
    CheckBox mCbThree;
    @BindView(R.id.content)
    EditText mContent;
    @BindView(R.id.submit)
    Button mSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appeal;
    }

    @Override
    protected void initInjector() {

        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mCbOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reason = 1;
                    mCbTwo.setChecked(false);
                    mCbThree.setChecked(false);
                } else {
                    reason = 0;
                }
            }
        });
        mCbTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reason = 2;
                    mCbOne.setChecked(false);
                    mCbThree.setChecked(false);
                } else {
                    reason = 0;
                }
            }
        });
        mCbThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    reason = 3;
                    mCbOne.setChecked(false);
                    mCbTwo.setChecked(false);
                } else {
                    reason = 0;
                }
            }
        });
    }

    @Override
    protected void initDatas() {
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                validate();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    private int reason = 0;

    private void validate() {
        if (reason == 0) {
            ToastUtils.showToast(getString(R.string.selectProblem));
            return;
        }
        if (TextUtils.isEmpty(mContent.getText().toString())) {
            ToastUtils.showToast(getString(R.string.ProblemDescription));
            return;
        }
        String bean = getIntent().getStringExtra("id");
        TreeMap params = new TreeMap<Object, Object>();
        params.put("act", ConstantUrl.FIAT_SUBMIT_APPEAL);
        params.put("order_id", bean);
        params.put("reason", reason + "");
        params.put("des", mContent.getText().toString().trim());
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void deleteSuccess(String msg) {
        ToastUtils.showToast(msg);
        setResult(RESULT_OK);
        finish();
    }
}
