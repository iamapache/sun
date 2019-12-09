package com.madaex.exchange.ui.finance.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseActivity;
import com.madaex.exchange.ui.finance.bean.BillList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  BillDetailActivity.java
 * 时间：  2018/10/17 10:36
 * 描述：  ${TODO}
 */

public class BillDetailActivity extends BaseActivity {
    @BindView(R.id.bill_dealtype)
    TextView mBillDealtype;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.bill_money)
    TextView mBillMoney;
    @BindView(R.id.bill_time)
    TextView mBillTime;
    @BindView(R.id.bill_type)
    TextView mBillType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_billdetail;
    }

    @OnClick({R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {
        BillList.DataBean bean = getIntent().getParcelableExtra("bean");
//        mBillDealtype.setText(bean.getRemark());
//        mNumber.setText(bean.getType()+bean.getFee());
//        mBillMoney.setText(bean.getMum_a());
//        mBillTime.setText(bean.getAddtime());
//        mBillType.setText(bean.getCoinname());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
