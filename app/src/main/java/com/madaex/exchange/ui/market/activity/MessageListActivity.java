package com.madaex.exchange.ui.market.activity;

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
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.bank.activity.AddBankActivity;
import com.madaex.exchange.ui.finance.bank.contract.Bank;
import com.madaex.exchange.ui.market.contract.MessageContract;
import com.madaex.exchange.ui.market.presenter.MessagePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  BankListActivity.java
 * 时间：  2018/8/28 18:47
 * 描述：  ${TODO}
 */

public class MessageListActivity extends BaseNetActivity<MessagePresenter> implements MessageContract.View {
    ArrayList<Bank.DataBean> mDataBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private int CODE_REQUEST = 0x002;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<Bank.DataBean, BaseViewHolder>(R.layout.item_banklist, mDataBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final Bank.DataBean item) {
//                helper.setText(R.id.name, item.getName()).setText(R.id.bankname, item.getBank()).setText(R.id.banktype, "储蓄卡")
//                        .setText(R.id.number, item.getBankcard()).setImageResource(R.id.bankimg, ViewUtil.setBankImageView(item.getName()));
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, AddBankActivity.class);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });

        mRecyclerview.setAdapter(mAdapter);
        View top = getLayoutInflater().inflate(R.layout.view_empty_data, (ViewGroup) mRecyclerview.getParent(), false);
        TextView textView = top.findViewById(R.id.nodata);
        textView.setText(R.string.nomessage);
        mAdapter.setEmptyView(top);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            getBankList();
        }

    }


    @Override
    protected void initDatas() {
        getBankList();
    }

    private void getBankList() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.FINANCE_USER_BANK_LIST);
        mPresenter.getData(DataUtil.sign(params));
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
    public void requestSuccess(List<Bank.DataBean> bean) {
        mAdapter.setNewData(bean);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

}
