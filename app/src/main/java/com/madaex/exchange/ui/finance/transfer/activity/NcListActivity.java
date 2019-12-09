package com.madaex.exchange.ui.finance.transfer.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.transfer.contract.CoinList;
import com.madaex.exchange.ui.finance.transfer.contract.NcContract;
import com.madaex.exchange.ui.finance.transfer.contract.Ncrecord;
import com.madaex.exchange.ui.finance.transfer.presenter.NcPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sunn
 * 类名：  NcActivity.java
 * 时间：  2019/5/23 9:50
 * 描述：  ${TODO}
 */
public class NcListActivity extends BaseNetActivity<NcPresenter> implements NcContract.View {
    private List<String> mDatalist = new ArrayList<>();
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ncrecord;
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

        mAdapter = new BaseQuickAdapter<PlatRecord.DataBean, BaseViewHolder>(R.layout.item_platrecord) {
            @Override
            protected void convert(BaseViewHolder helper, final PlatRecord.DataBean item) {
                    helper.setText(R.id.coinname, item.getCoinname()).setText(R.id.price, getString(R.string.number)+item.getNum())
                            .setText(R.id.coinprice, item.getAddress()) .setText(R.id.mum, item.getAddtime()+"");

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
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.COIN_COIN_CZ_LIST);
        params.put("user_id", SPUtils.getString(Constants.TOKEN, ""));
        mPresenter.getData2(DataUtil.sign(params));
    }


    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String commonBean) {
        ToastUtils.showToast(commonBean);
    }

    @Override
    public void requestSuccess(PlatRecord commonBean) {
        mAdapter.setNewData(commonBean.getData());
    }

    @Override
    public void requestSuccess(Ncrecord commonBean) {

    }

    @Override
    public void requestSuccess(CoinList commonBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.toolbar_left_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
        }
    }

}
