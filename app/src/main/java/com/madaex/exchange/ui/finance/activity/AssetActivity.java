package com.madaex.exchange.ui.finance.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.adapter.RecyclerviewAdapter;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  exchange
 * 类名：  AssetActivity.java
 * 时间：  2018/8/22 15:22
 * 描述：  ${TODO}
 */

public class AssetActivity extends BaseNetActivity<AssetPresenter> implements AssetContract.View {
    ArrayList<Asset.DataBean.XnbListBean> testBeans = new ArrayList<>();
//    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.cny)
    TextView mCny;
    @BindView(R.id.dollar)
    TextView mDollar;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    @BindView(R.id.search_asset)
    EditText mSearchAsset;
    @BindView(R.id.cb_number)
    CheckBox mCbNumber;
    private Handler handler = new Handler();
    private RecyclerviewAdapter mAdapter;
    private boolean isshow =false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asset;
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
        mAdapter = new RecyclerviewAdapter(this,testBeans);
        mRecyclerview.setAdapter(mAdapter);
//        mAdapter = new BaseQuickAdapter<Asset.DataBean.XnbListBean, BaseViewHolder>(R.layout.item_asset, testBeans) {
//            @Override
//            protected void convert(BaseViewHolder helper, final Asset.DataBean.XnbListBean item) {
//                helper.setText(R.id.asset_name, item.getXnb_name())
//                        .setText(R.id.avail, "折合 " + item.getAvail())
//                        .setText(R.id.frozen, "冻结￥ " + item.getFrozen())
//                        .setText(R.id.assets, item.getAssets() + "");
//                helper.getView(R.id.ll_buycoin).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        if (!item.getType().equals("qbb")) {
//                            Intent intent = new Intent();
//                            intent.setClass(mContext, BuyCoinActivity.class);
//                            intent.putExtra("address", "11");
//                            intent.putExtra("xnb", item.getXnb());
//                            intent.putExtra("xnb_name", item.getXnb_name());
//                            startActivity(intent);
//                        } else {
//                            Intent intent = new Intent();
//                            intent.setClass(mContext, BuyBillActivity.class);
//                            intent.putExtra("address", "11");
//                            intent.putExtra("xnb", item.getXnb());
//                            intent.putExtra("xnb_name", item.getXnb_name());
//                            startActivity(intent);
//                        }
//                    }
//                });
//                helper.getView(R.id.ll_sellercoin).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, SellerCoinActivity.class);
//                        intent.putExtra("xnb", item.getXnb());
//                        intent.putExtra("xnb_name", item.getXnb_name());
//                        startActivity(intent);
//                    }
//                });
//
//                helper.getView(R.id.ll_bill).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, BillActivity.class);
//                        intent.putExtra("xnb", item.getXnb());
//                        intent.putExtra("xnb_name", item.getXnb_name());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//        };
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                getData();
//            }
//        }, mRecyclerview);
//        mRecyclerview.setAdapter(mAdapter);
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                }, 2000);
            }
        });

        mCbNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    isshow = true;
                    mAdapter.filter(mSearchAsset.getText().toString(),isshow);
                }else{
                    isshow = false;
                    mAdapter.filter(mSearchAsset.getText().toString(),isshow);
                }
            }
        });
        mSearchAsset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mAdapter.filter(editable.toString(),isshow);
            }
        });
    }

    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_ASSETS_LIST);
        params.put("wallet_type",getIntent().getStringExtra("wallet_type"));
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    protected void initDatas() {
        getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    public void nodata(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void requestSuccess(Asset commonBean) {
        mSwiperefreshlayout.setRefreshing(false);
//        mAdapter.setNewData(commonBean.getData().getXnb_list());
        testBeans.clear();
        testBeans.addAll(commonBean.getData().getXnb_list());
        mAdapter.notifyDataSetChanged();
        mCny.setText("￥  " + commonBean.getData().getAssets().getCny() + "");
        mDollar.setText("$  " + commonBean.getData().getAssets().getDollar() + "");
    }
}
