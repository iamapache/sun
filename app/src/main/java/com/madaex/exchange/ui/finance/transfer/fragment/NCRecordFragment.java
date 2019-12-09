package com.madaex.exchange.ui.finance.transfer.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.RecycleViewDivider;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.transfer.contract.CoinList;
import com.madaex.exchange.ui.finance.transfer.contract.NcContract;
import com.madaex.exchange.ui.finance.transfer.contract.Ncrecord;
import com.madaex.exchange.ui.finance.transfer.presenter.NcPresenter;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * 项目：  madaexchange
 * 类名：  TransationBuyFragment.java
 * 时间：  2018/8/30 10:19
 * 描述：  ${TODO}
 */

public class NCRecordFragment extends BaseNetLazyFragment<NcPresenter> implements NcContract.View{
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    int pageNum = 1;
    boolean isRefresh = true;
    public static NCRecordFragment newInstance(int string) {
        NCRecordFragment fragment = null;
        if (fragment == null) {
            fragment = new NCRecordFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ncrecord;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<Ncrecord.DataBean.ListBean, BaseViewHolder>(R.layout.item_platrecord) {
            @Override
            protected void convert(BaseViewHolder helper, final Ncrecord.DataBean.ListBean item) {
                if(type==1){
                    helper.setText(R.id.coinname,item.getCoinname()).setText(R.id.price,item.getUser_info())
                            .setText(R.id.coinprice,  item.getNum()) .setText(R.id.mum, item.getAddtime());
                }else {
                    helper.setText(R.id.coinname,item.getCoinname()).setText(R.id.price, item.getUser_info())
                            .setText(R.id.coinprice,  item.getNum()) .setText(R.id.mum, item.getAddtime());
                }

            }
        };
        mRecyclerview.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.white)));
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
        mAdapter.setEmptyView(R.layout.view_empty_history, (ViewGroup) mRecyclerview.getParent());
        mRecyclerview.setAdapter(mAdapter);
    }
    int type =1;
    @Override
    protected void initDatas() {
        getData();
    }

    private void getData() {
        type =  getArguments().getInt(Constants.ARGS);
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_INTERNAL_TRANSFER_LIST);
        params.put("type", type+"");
        params.put("curPage", pageNum + "");
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
//        startActivity(new Intent(mContext, C2CTransationListActivity.class));
    }

    @Override
    public void requestSuccess(PlatRecord commonBean) {
    }

    @Override
    public void requestSuccess(Ncrecord commonBean) {
        mSwiperefreshlayout.setRefreshing(false);
        if (commonBean != null) {
            setData(isRefresh, commonBean.getData().getList());

        } else {
            mAdapter.setEnableLoadMore(true);
        }
    }

    @Override
    public void requestSuccess(CoinList commonBean) {

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

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }


    private String one_xnb = "";

}
