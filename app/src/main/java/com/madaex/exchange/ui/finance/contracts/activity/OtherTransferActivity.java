package com.madaex.exchange.ui.finance.contracts.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.OpenHole;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;
import com.madaex.exchange.ui.finance.contracts.contract.ContractContract;
import com.madaex.exchange.ui.finance.contracts.presenter.ContractPresenter;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  TransferActivity.java
 * 时间：  2020/1/9 13:58
 * 描述：
 */
public class OtherTransferActivity extends BaseNetActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.cny)
    TextView mCny;
    @BindView(R.id.dollar)
    TextView mDollar;
    @BindView(R.id.avail)
    TextView mAvail;
    @BindView(R.id.frozen)
    TextView mFrozen;
    @BindView(R.id.locking)
    TextView mLocking;
    @BindView(R.id.allrecords)
    TextView mAllrecords;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    @BindView(R.id.today)
    TextView mToday;
    @BindView(R.id.tomorrow)
    TextView mTomorrow;
    @BindView(R.id.income1)
    TextView mIncome1;
    @BindView(R.id.income2)
    TextView mIncome2;
    private BaseQuickAdapter mAdapter;
    int pageNum = 1;
    boolean isRefresh = true;
    private ContractAsset.DataBean.XnbListBean mParcelableExtra;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_othertransfer;
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        mParcelableExtra = getIntent().getParcelableExtra("bean");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mAdapter = new BaseQuickAdapter<Bills.DataBean.ItemBean, BaseViewHolder>(R.layout.item_recenthistory) {
            @Override
            protected void convert(BaseViewHolder helper, final Bills.DataBean.ItemBean item) {
                helper.setText(R.id.type, item.getType_name()+ "")
                        .setText(R.id.cion, item.getCoin_name() )
                        .setText(R.id.count, item.getNum());

            }
        };
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bills.DataBean.ItemBean item = (Bills.DataBean.ItemBean) adapter.getItem(position);
            }
        });

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isRefresh =true;
                initDatas();
            }
        });
    }
    private void getData() {
        TreeMap params = new TreeMap<Object, Object>();
        params.put("act", ConstantUrl.Trade_contract_bills);
        params.put("type","recent" );
        params.put("curPage", pageNum+"");
        params.put("wallet_id", mParcelableExtra.getWallet_id() + "");
        mPresenter.bills(DataUtil.sign(params));
    }

    @OnClick({R.id.toolbar_left_btn_ll,R.id.submit,R.id.allrecords,R.id.shifangseller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.submit:
                TreeMap params = new TreeMap<Object, Object>();
                params.put("act", ConstantUrl.Trade_is_open_contract_trade);
                params.put("wallet_id", mParcelableExtra.getWallet_id() + "");
                mPresenter.open_contract(DataUtil.sign(params));
                break;

            case R.id.allrecords:
                Intent intent =  getIntent();
                intent.setClass(mContext, AllHistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.shifangseller:
                Intent intent2 =  getIntent();
                intent2.setClass(mContext, ShifangSellerActivity.class);
                intent2.putExtra("mavail",mAvail.getText().toString().trim());
                startActivityForResult(intent2,1);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            pageNum = 1;
            isRefresh =true;
            initDatas();
        }
    }

    @Override
    protected void initDatas() {
        getData();
        mTitleView.setText(mParcelableExtra.getXnb_name());
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.Trade_contract_coin_info);
        params.put("wallet_id", mParcelableExtra.getWallet_id() + "");
        mPresenter.getAlscInfo(DataUtil.sign(params));
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestErrorcontract(String s) {
        ToastUtils.showToast(s);
        Intent intent =  getIntent();
        if(EmptyUtils.isNotEmpty(commonBean)){
            intent.putExtra("market_name",commonBean.getData().getMarket_name());
        }
        intent.setClass(mContext, OpenContractActivity.class);
        startActivityForResult(intent,1);
    }
    @Override
    public void requestSuccess(String commonBean) {

        Intent intent =  getIntent();
        intent.setClass(mContext, HuaActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    public void requestSuccess(ContractAsset commonBean) {

    }

    @Override
    public void requestSuccess(WalletInfo commonBean) {

    }

    @Override
    public void requestSuccess(USDTinfo commonBean) {

    }
    AlscInfo commonBean;
    @Override
    public void requestSuccess(AlscInfo commonBean) {
        this.commonBean =commonBean;
        mCny.setText(commonBean.getData().getAssets().getUsdt().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        mDollar.setText("≈ ¥ "+commonBean.getData().getAssets().getRmb().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        mAvail.setText(commonBean.getData().getCon_usable_assets() + "");
        mFrozen.setText(commonBean.getData().getCon_frozen_assets() + "");
        mLocking.setText(commonBean.getData().getLock_assets() + "");
        if(EmptyUtils.isNotEmpty(commonBean.getData().getToday())){
            mIncome1.setText(commonBean.getData().getToday().getIncome()+"");
            mToday.setText(commonBean.getData().getToday().getRelease() + "");

        }
        if(EmptyUtils.isNotEmpty(commonBean.getData().getTomorrow())) {
            mIncome2.setText(commonBean.getData().getTomorrow().getIncome()+"");
            mTomorrow.setText(commonBean.getData().getTomorrow().getRelease() + "");
        }
    }

    @Override
    public void requestSuccess(Bills commonBean) {
        mSwiperefreshlayout.setRefreshing(false);
        if (commonBean != null) {
            mAdapter.setNewData(commonBean.getData().getList());
        }
    }

    @Override
    public void requestSuccess(OpenHole commonBean) {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
