package com.madaex.exchange.ui.finance.address.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.address.bean.WalletAddress;
import com.madaex.exchange.ui.finance.address.contract.AddressContract;
import com.madaex.exchange.ui.finance.address.presenter.AddressPresenter;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目：  madaexchange
 * 类名：  AddressListActivity.java
 * 时间：  2018/8/23 10:22
 * 描述：  ${TODO}
 */

public class AddressListActivity extends BaseNetActivity<AddressPresenter> implements AddressContract.View {
    ArrayList<WalletAddress.DataBean> testBeans = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    private int CODE_REQUEST = 0x002;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addresslist;
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

        mAdapter = new BaseQuickAdapter<WalletAddress.DataBean, BaseViewHolder>(R.layout.item_address, testBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final WalletAddress.DataBean item) {
                helper.setText(R.id.name, item.getName())
                        .setText(R.id.address, item.getAddr());
                CheckBox checkBox = helper.getView(R.id.cb_number);
                if (item.isCheck) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                helper.getView(R.id.cb_number).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (WalletAddress.DataBean dataBean : testBeans) {
                            dataBean.isCheck = false;
                        }
                        if (checkBox.isChecked()) {
                            item.isCheck = true;
                        }
                        notifyDataSetChanged();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("address", item.getAddr());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WalletAddress.DataBean dataBean = (WalletAddress.DataBean) mAdapter.getItem(position);
                Intent intent = getIntent();
                intent.setClass(mContext, EditAddressActivity.class);
                intent.putExtra("id", dataBean.getId());
                intent.putExtra("address", dataBean.getAddr());
                startActivityForResult(intent, CODE_REQUEST);
            }
        });
        mAdapter.setEmptyView(R.layout.view_empty_history, (ViewGroup) mRecyclerview.getParent());
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void initDatas() {

        String xnb_name = getIntent().getStringExtra("xnb_name");
        mTitleView.setText(getString(R.string.paymentaddress) + xnb_name);
        getData();
    }

    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_COIN_ADDRESS_LIST);
        params.put("xnb", getIntent().getStringExtra("xnb"));
        params.put(ConstantUrl.TYPE, getIntent().getStringExtra(ConstantUrl.TYPE));
        mPresenter.getData(DataUtil.sign(params));
    }

    @OnClick({R.id.submit, R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Intent intent = getIntent();
                intent.setClass(mContext, AddAddressActivity.class);
                startActivityForResult(intent, CODE_REQUEST);
                break;
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK
                && requestCode == CODE_REQUEST) {
            getData();
        }

    }

    @Override
    public void nodata(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(WalletAddress commonBean) {
        mAdapter.setNewData(commonBean.getData());
        testBeans.addAll(commonBean.getData());
    }
}
