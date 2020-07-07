package com.madaex.exchange.ui.finance.contracts.activity;

import android.support.v4.content.ContextCompat;
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
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.OpenHole;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;
import com.madaex.exchange.ui.finance.contracts.contract.ContractContract;
import com.madaex.exchange.ui.finance.contracts.presenter.ContractPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * 项目：  sun
 * 类名：  HuaRecordActivity.java
 * 时间：  2020/1/9 22:34
 * 描述：
 */
public class HuaRecordActivity extends BaseNetActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    private BaseQuickAdapter mAdapter;
    int pageNum = 1;
    boolean isRefresh = true;
    private ContractAsset.DataBean.XnbListBean mParcelableExtra;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huarecord;
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
                helper.setText(R.id.type, item.getType_name() + "")
                        .setText(R.id.cion, item.getCoin_name())
                        .setText(R.id.count, item.getNum());

            }
        };
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bills.DataBean.ItemBean item = (Bills.DataBean.ItemBean) adapter.getItem(position);
//                Intent intent = new Intent(mContext, C2CEntrustDetailActivity.class);
//                startActivity(intent);
            }
        });

        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isRefresh = true;
                getData();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getData();
            }
        }, mRecyclerview);
    }

    @Override
    protected void initDatas() {
        getData();
    }

    private String type = "";

    private void getData() {
        TreeMap params = new TreeMap<Object, Object>();
        params.put("act", ConstantUrl.Trade_transfer_bills);
        params.put("type", type);
        params.put("curPage", pageNum + "");
        params.put("wallet_id", mParcelableExtra.getWallet_id() + "");
        mPresenter.gethuaRecord(DataUtil.sign(params));
    }

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_btn_ll:
                finish();
                break;
            case R.id.toolbar_right_btn_ll:
                onSinglePicker();
                break;
        }
    }


    public void onSinglePicker() {
        List<String> data = new ArrayList<>();
        data.add(getString(R.string.all));
        data.add(getString(R.string.out));
        data.add(getString(R.string.in));
        SinglePicker<String> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setCycleDisable(true);
        picker.setTopBackgroundColor(ContextCompat.getColor(this, R.color.common_black));
        picker.setTitleText(getString(R.string.Transferh));
        picker.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        picker.setTitleTextSize(12);
        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.white));
        picker.setCancelTextSize(13);
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.title_color));
        picker.setSubmitTextSize(13);

        WheelView.DividerConfig config = new WheelView.DividerConfig();
        config.setColor(ContextCompat.getColor(this, R.color.common_black));//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        picker.setDividerConfig(config);
        picker.setTextColor(ContextCompat.getColor(this, R.color.title_color), ContextCompat.getColor(this, R.color.white));
        picker.setBackgroundColor(ContextCompat.getColor(this, R.color.common_black));
        picker.setCanceledOnTouchOutside(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                if(index==0){
                    type ="";
                    pageNum = 1;
                    isRefresh = true;
                    getData();
                }else  if(index==1){
                    type="out";
                    pageNum = 1;
                    isRefresh = true;
                    getData();
                }else  if(index==2){
                    type="in";
                    pageNum = 1;
                    isRefresh = true;
                    getData();
                }
            }
        });
        picker.show();
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {

    }

    @Override
    public void requestSuccess(String commonBean) {

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

    @Override
    public void requestSuccess(AlscInfo commonBean) {

    }
    @Override
    public void requestSuccess(Bills commonBean) {
        mSwiperefreshlayout.setRefreshing(false);
        if (commonBean != null) {
            setData(isRefresh, commonBean.getData().getList());

        } else {
            mAdapter.setEnableLoadMore(true);
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
    public void requestErrorcontract(String s) {

    }
}
