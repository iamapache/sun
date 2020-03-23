package com.madaex.exchange.ui.finance.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.madaex.exchange.ui.finance.bean.BillList;
import com.madaex.exchange.ui.finance.contract.BillContract;
import com.madaex.exchange.ui.finance.presenter.BillPresenter;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  exchange
 * 类名：  BuyCoinActivity.java
 * 时间：  2018/8/22 18:09
 * 描述：  ${TODO}
 */

public class BillActivity extends BaseNetActivity<BillPresenter> implements BillContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    BaseQuickAdapter mAdapter;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    int pageNum = 1;
    boolean isRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill;
    }

    @Override
    protected void initInjector() {
getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

        String str = getIntent().getStringExtra("xnb");
        String xnb_name = getIntent().getStringExtra("xnb_name");
        mTitleView.setText("" + xnb_name + getString(R.string.bill));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<BillList.DataBean.ListBean, BaseViewHolder>(R.layout.item_bill) {
            @Override
            protected void convert(BaseViewHolder helper, BillList.DataBean.ListBean item) {
                helper.setText(R.id.time1, item.getAddtime().split(" ")[0])
                        .setText(R.id.time2, item.getAddtime().split(" ")[1]).setText(R.id.number, item.getAdd_subtract()+item.getNum())
                        .setText(R.id.cointype, item.getCoin_ename())
                        .setText(R.id.type, item.getType_name());
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, BillDetailActivity.class);
//                intent.putExtra("bean",(BillList.DataBean) mAdapter.getItem(position));
//                startActivity(intent);
            }
        });
        mRecyclerview.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.common_ggray)));
        mAdapter.setEmptyView(R.layout.view_empty_bill, (ViewGroup) mRecyclerview.getParent());
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getData();
            }
        }, mRecyclerview);

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isRefresh = true;
                getData();
            }
        });
    }


    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_BILL_LIST);
        params.put("xnb", getIntent().getStringExtra("xnb"));
        params.put("curPage", pageNum + "");
        params.put("wallet_type", getIntent().getStringExtra("wallet_type"));
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    protected void initDatas() {
        getData();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void requestError(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void requestSuccess(BillList commonBean) {
        mSwiperefreshlayout.setRefreshing(false);
        if (commonBean != null) {
            setData(isRefresh, commonBean.getData().getList());

        } else {
            mAdapter.setEnableLoadMore(true);
        }
    }


    private void setData(boolean isRefresh, List data) {
        pageNum++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            mAdapter.setEnableLoadMore(true);
            mAdapter.setNewData(data);

        } else {
            if (size > 0) {
                mAdapter.addData(data);

            }
        }
        if (size < 6) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }
}
