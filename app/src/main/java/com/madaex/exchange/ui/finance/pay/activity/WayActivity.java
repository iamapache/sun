package com.madaex.exchange.ui.finance.pay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.Payway;
import com.madaex.exchange.ui.finance.pay.contract.WayContract;
import com.madaex.exchange.ui.finance.pay.presenter.WayPresenter;
import com.orhanobut.logger.Logger;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  WayActivity.java
 * 时间：  2019/5/13 18:00
 * 描述：  ${TODO}
 */
public class WayActivity extends BaseNetActivity<WayPresenter> implements WayContract.View {
    @BindView(R.id.tv_zfb)
    TextView mTvZfb;
    @BindView(R.id.tv_wx)
    TextView mTvWx;
    @BindView(R.id.tv_bank)
    TextView mTvBank;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_way;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mTvBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BankAddActivity.class));
            }
        });   mTvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, WXActivity.class));
            }
        });   mTvZfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ZFBActivity.class));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.USER_PAYMENTLIST);
        params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
        mPresenter.submit(DataUtil.sign(params));
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

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.tv_zfb:
//                startActivity(new Intent(mContext, ZFBActivity.class));
//                break;
//            case R.id.tv_wx:
//                startActivity(new Intent(mContext, WXActivity.class));
//                break;
//            case R.id.tv_bank:
//                startActivity(new Intent(mContext, BankAddActivity.class));
//                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(String commonBean) {

    }

    @Override
    public void requestSuccess(Payway commonBean) {
        for (Payway.DataBean dataBean : commonBean.getData()) {
            if (dataBean.getType()==3) {
                Logger.i("<==>data:" + "BankEditActivity");
                mTvBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, BankEditActivity.class);
                        intent.putExtra("bean", dataBean);
                        startActivity(intent);
                    }
                });
            } else   if (dataBean.getType()!=3) {
                Logger.i("<==>data:" + "BankAddActivity");


            }
            if (dataBean.getType()==1) {
                mTvWx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WXActivity.class);
                        intent.putExtra("bean", dataBean);
                        startActivity(intent);
                    }
                });
            } else  if (dataBean.getType()!=1){

            }
            if (dataBean.getType()==2) {
                mTvZfb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ZFBActivity.class);
                        intent.putExtra("bean", dataBean);
                        startActivity(intent);
                    }
                });
            } else  if (dataBean.getType()!=2){


            }


        }
    }

    @Override
    public void requestPayWaySuccess(String commonBean) {

    }
}
