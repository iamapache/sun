package com.madaex.exchange.ui.finance.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.bean.TransaList;
import com.madaex.exchange.ui.finance.contract.TransaContract;
import com.madaex.exchange.ui.finance.presenter.TransaPreaenter;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  TransaListActivity.java
 * 时间：  2018/8/31 10:13
 * 描述：  ${TODO}
 */

public class TransaListActivity extends BaseNetActivity <TransaPreaenter> implements TransaContract.View{
    ArrayList<TransaList.DataBean> testBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    private int CODE_REQUEST = 0x002;
    String type="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_transalist;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        String xnb_name = getIntent().getStringExtra("xnb_name");
         type = getIntent().getStringExtra(ConstantUrl.TYPE);
        if(type.equals(ConstantUrl.TRANS_TYPE_SELLER)){
            mTitleView.setText(getString(R.string.Paymoneyrecord) + xnb_name);
        }else {
            mTitleView.setText(getString(R.string.Chargemoneyrecord) + xnb_name);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<TransaList.DataBean, BaseViewHolder>(R.layout.item_bill_transaion, testBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final TransaList.DataBean item) {
                if (type.equals(ConstantUrl.TRANS_TYPE_SELLER)) {
                    helper.setText(R.id.name, "-"+item.getNum()+item.getCoinname());
                } else {
                    helper.setText(R.id.name, "+"+item.getNum()+item.getCoinname());
                }
                helper.setText(R.id.time, item.getAddtime())
                        .setText(R.id.address, item.getUsername());
                helper.getView(R.id.ll_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = getIntent();
                        intent.setClass(mContext, TransationDetailActivity.class);
                        intent.putExtra("id",item.getId());
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerview.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.white)));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mAdapter.setEmptyView(R.layout.view_empty_history, (ViewGroup) mRecyclerview.getParent());
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void initDatas() {

        if(type.equals(ConstantUrl.TRANS_TYPE_SELLER)){
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_CASH_LIST);
            params.put("xnb", getIntent().getStringExtra("xnb"));
            mPresenter.getData(DataUtil.sign(params));
        }else {
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_RECHARGE_LIST);
            params.put("xnb", getIntent().getStringExtra("xnb"));
            mPresenter.getData(DataUtil.sign(params));
        }

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
    public void requestError(String msg) {

    }


    @Override
    public void requestSuccess(TransaList commonBean) {
        mAdapter.setNewData(commonBean.getData());
    }
}
