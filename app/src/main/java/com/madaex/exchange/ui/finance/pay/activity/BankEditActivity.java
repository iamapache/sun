package com.madaex.exchange.ui.finance.pay.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.ImgCheck;
import com.madaex.exchange.ui.finance.pay.bean.Payway;
import com.madaex.exchange.ui.finance.pay.bean.UploadIdcard;
import com.madaex.exchange.ui.finance.pay.contract.WayContract;
import com.madaex.exchange.ui.finance.pay.presenter.WayPresenter;
import com.wc.widget.dialog.IosDialog;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  BankAddActivity.java
 * 时间：  2019/5/13 18:55
 * 描述：  ${TODO}
 */
public class BankEditActivity extends BaseNetActivity<WayPresenter> implements WayContract.View{

    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.password)
    TextView mPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bankedit;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }
    Payway.DataBean dataBean;
    @Override
    protected void initDatas() {

        dataBean = getIntent().getParcelableExtra("bean");
        mAddr.setText(dataBean.getBank());
        mName.setText(dataBean.getName());
        mPassword.setText(dataBean.getAccount());
    }


    @OnClick({R.id.delete, R.id.toolbar_left_btn_ll, R.id.edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete:
                Dialog dialog = new IosDialog.Builder(this).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                        .setMessage(R.string.Confirmthedeletion).setMessageSize(14)
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
                        .setPositiveButton(R.string.delete, new IosDialog.OnClickListener() {
                            @Override
                            public void onClick(IosDialog dialog, View v) {
                                TreeMap params = new TreeMap<Object, Object>();
                                params.put("act", ConstantUrl.USER_PAYMENTDEL);
                                params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
                                params.put("id", dataBean.getId());
                                mPresenter.delete(DataUtil.sign(params));
                            }
                        }).build();
                dialog.show();
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.edit:
                Intent intent =   getIntent();
                intent.setClass(mContext, BankAddActivity.class);
                intent.putExtra("bean",dataBean);
                startActivity(intent);
                finish();
                break;
        }
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
    public void requestSuccess(Payway commonBean) {

    }

    @Override
    public void requestPayWaySuccess(String commonBean) {

    }

    @Override
    public void uploadIdcardSuccess(UploadIdcard commonBean) {

    }

    @Override
    public void uploadIdcardSuccess2(UploadIdcard commonBean) {

    }

    @Override
    public void requestidcardImgCheckError(String msg) {

    }

    @Override
    public void idcardImgCheck(ImgCheck commonBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
