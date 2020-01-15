package com.madaex.exchange.ui.finance.contracts.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetActivity;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.finance.contracts.adapter.ContractRecyclerviewAdapter;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;
import com.madaex.exchange.ui.finance.contracts.contract.ContractContract;
import com.madaex.exchange.ui.finance.contracts.presenter.ContractPresenter;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目：  sun
 * 类名：  ContractActivity.java
 * 时间：  2020/1/7 9:25
 * 描述：
 */
public class ContractActivity extends BaseNetActivity<ContractPresenter> implements ContractContract.View {
    @BindView(R.id.usdt)
    TextView mUsdt;
    @BindView(R.id.rmb)
    TextView mRmb;
    @BindView(R.id.search_asset)
    EditText mSearchAsset;
    @BindView(R.id.cb_number)
    CheckBox mCbNumber;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    @BindView(R.id.gone)
    ImageView mGone;
    private ContractRecyclerviewAdapter mAdapter;
    ArrayList<ContractAsset.DataBean.XnbListBean> testBeans = new ArrayList<>();
    private Handler handler = new Handler();
    private boolean isshow = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract;
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
        String wallet_type = getIntent().getStringExtra("wallet_type");
        mAdapter = new ContractRecyclerviewAdapter(this, testBeans, wallet_type);
        mRecyclerview.setAdapter(mAdapter);

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

        mCbNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    isshow = true;
                    mAdapter.filter(mSearchAsset.getText().toString(), isshow);
                } else {
                    isshow = false;
                    mAdapter.filter(mSearchAsset.getText().toString(), isshow);
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
                mAdapter.filter(editable.toString(), isshow);
            }
        });
    }

    private void getData() {
        TreeMap params = new TreeMap<>();
        params.put("act", ConstantUrl.TRADE_ASSETS_LIST);
        params.put("wallet_type", getIntent().getStringExtra("wallet_type"));
        mPresenter.getData(DataUtil.sign(params));
    }

    @Override
    protected void initDatas() {
        getData();
    }

    @Override
    public void nodata(String msg) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(getString(R.string.loginexpired));
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void requestSuccess(String commonBean) {

    }

    @Override
    public void requestSuccess(ContractAsset commonBean) {
        mSwiperefreshlayout.setRefreshing(false);
//        mAdapter.setNewData(commonBean.getData().getXnb_list());
        testBeans.clear();
        if (EmptyUtils.isNotEmpty(commonBean.getData().getXnb_list())) {
            testBeans.addAll(commonBean.getData().getXnb_list().get(0));
            mAdapter.notifyDataSetChanged();
        }
        if (EmptyUtils.isNotEmpty(commonBean.getData().getAssetsArr())) {
            mUsdt.setText(commonBean.getData().getAssetsArr().getUsdt() + "USDT");
            mRmb.setText("≈ ¥ " + commonBean.getData().getAssetsArr().getRmb() + "");
        }
        mGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isgone) {
                    isgone =false;
                    mUsdt.setText(commonBean.getData().getAssetsArr().getUsdt() + "USDT");
                    mRmb.setText("≈ ¥ " + commonBean.getData().getAssetsArr().getRmb() + "");
                    mGone.setImageResource(R.mipmap.denglu_zy);
                } else {
                    isgone =true;
                    mUsdt.setText("***********");
                    mRmb.setText("***********");
                    mGone.setImageResource(R.mipmap.denglu_by);
                }
            }
        });

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

    }

    @Override
    public void requestErrorcontract(String s) {

    }

    private boolean isgone = false;

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
}
