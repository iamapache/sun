package com.madaex.exchange.ui.finance.contracts.activity;

import android.app.Activity;
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
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.OpenHoleDestail;
import com.madaex.exchange.ui.finance.contracts.contract.OpenHoleContract;
import com.madaex.exchange.ui.finance.contracts.presenter.OpenHolePresenter;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  OpenHoleActivity.java
 * 时间：  2020/7/7 11:27
 * 描述：
 */
public class OpenHoleActivity extends BaseNetActivity<OpenHolePresenter> implements OpenHoleContract.View {

    @BindView(R.id.difference)
    TextView mDifference;
    @BindView(R.id.newblacknum)
    TextView mNewBlackNum;
    @BindView(R.id.zrtotal)
    TextView mZrTotal;
    @BindView(R.id.zctotal)
    TextView mZcTotal;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    private BaseQuickAdapter mAdapter;
    int pageNum = 1;
    boolean isRefresh = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_holedetail;
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
        mAdapter = new BaseQuickAdapter<OpenHoleDestail.DataBean.ArrBlackBean, BaseViewHolder>(R.layout.item_recenthistory) {
            @Override
            protected void convert(BaseViewHolder helper, final OpenHoleDestail.DataBean.ArrBlackBean item) {
                helper.setText(R.id.type, item.getType_name() + "")
                        .setText(R.id.cion, item.getCoin_name())
                        .setText(R.id.count, item.getNum());

            }
        };
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                initDatas();
            }
        });
    }

    @Override
    protected void initDatas() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TradeRelease_blackHoleInfo);
        mPresenter.getData(DataUtil.sign(params));
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.submit, R.id.allrecords, R.id.shifangseller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.submit:
                finish();
                break;

            case R.id.allrecords:
//                Intent intent = getIntent();
//                intent.setClass(mContext, AllHistoryActivity.class);
//                startActivity(intent);
                break;

            case R.id.shifangseller:
                if (EmptyUtils.isNotEmpty(commonBean)) {
                    ContractAsset.DataBean.XnbListBean mParcelableExtra = getIntent().getParcelableExtra("bean");
                    String num = getIntent().getStringExtra("num");
                    if (Double.valueOf(num) > 0) {
                        TreeMap params = new TreeMap<>();
                        params.put("act", ConstantUrl.TradeRelease_transferToBlackHole);
                        params.put("num", num + "");
                        params.put("wallet_id", mParcelableExtra.getWallet_id() + "");
                        mPresenter.transfer(DataUtil.sign(params));
                    } else {
                        ToastUtils.showToast(getString(R.string.blackholes));
                    }
                }
                break;
        }
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        setResult(Activity.RESULT_OK);
        isRefresh = true;
        initDatas();
    }

    OpenHoleDestail commonBean;

    @Override
    public void requestSuccess(OpenHoleDestail commonBean) {
        this.commonBean = commonBean;
        mDifference.setText(commonBean.getData().getDifference());
        mNewBlackNum.setText(commonBean.getData().getNew_black_num());
        mZrTotal.setText(commonBean.getData().getZr_total());
        mZcTotal.setText(commonBean.getData().getZc_total());
        mSwiperefreshlayout.setRefreshing(false);
        if (commonBean != null) {
            mAdapter.setNewData(commonBean.getData().getArr_black());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
