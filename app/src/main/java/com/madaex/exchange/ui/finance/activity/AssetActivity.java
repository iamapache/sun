package com.madaex.exchange.ui.finance.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
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
import com.madaex.exchange.ui.finance.adapter.RecyclerviewAdapter;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.bean.Recharge;
import com.madaex.exchange.ui.finance.bean.auth_check;
import com.madaex.exchange.ui.finance.contract.AssetContract;
import com.madaex.exchange.ui.finance.presenter.AssetPresenter;
import com.madaex.exchange.ui.mine.activity.AuthenticationActivity;
import com.wc.widget.dialog.IosDialog;

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
    private boolean isshow = false;
    @BindView(R.id.toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.gone)
    ImageView mGone;
    Asset.DataBean.XnbListBean mXnbListBean = new Asset.DataBean.XnbListBean();
    String wallet_type = "0";


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
        String xnb_name = getIntent().getStringExtra("title");
        mTitleView.setText(xnb_name);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        wallet_type = getIntent().getStringExtra("wallet_type");
        mAdapter = new RecyclerviewAdapter(this, testBeans, wallet_type);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setItemOnClickInterface(new RecyclerviewAdapter.ItemOnClickInterface() {
            @Override
            public void onItemClick(Asset.DataBean.XnbListBean item, String wallet_type) {
                if (item.getIs_support_cash() == 1) {
                    mXnbListBean = item;
                    TreeMap params = new TreeMap<>();
                    params.put("act", "User.user_auth_check");
                    mPresenter.user_auth_check(DataUtil.sign(params));
                } else if (item.getIs_support_cash() == 0) {
                    ToastUtils.showToast(item.getIs_support_cash_not_open_tips()+"");
                }


            }
        });
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

    private boolean isgone = false;

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

        if (EmptyUtils.isNotEmpty(commonBean.getData().getXnb_list())) {
            testBeans.clear();
            testBeans.addAll(commonBean.getData().getXnb_list());
            mAdapter.notifyDataSetChanged();
        }
        if (EmptyUtils.isNotEmpty(commonBean.getData().getGen_assets())) {
            mCny.setText("" + commonBean.getData().getGen_assets().getUsdt() + "");
            mDollar.setText("≈￥" + commonBean.getData().getGen_assets().getRmb() + "");
        }
        mGone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isgone) {
                    isgone = false;
                    mCny.setText("" + commonBean.getData().getAssets().getUsdt() + "");
                    mDollar.setText("≈￥" + commonBean.getData().getAssets().getRmb() + "");
                    mGone.setImageResource(R.mipmap.denglu_zy);
                } else {
                    isgone = true;
                    mCny.setText("***********");
                    mDollar.setText("***********");
                    mGone.setImageResource(R.mipmap.denglu_by);
                }
            }
        });
    }

    @Override
    public void requestRecharge(Recharge commonBean) {

    }

    @Override
    public void requestSuccess(auth_check commonBean) {
        if (commonBean.getData().getIs_auth() == 0) {

            Dialog dialog = new IosDialog.Builder(this).setTitle(getString(R.string.Warmreminder)).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                    .setMessage(commonBean.getData().getMessage()).setMessageColor(ContextCompat.getColor(mContext, R.color.common_red)).setMessageSize(14)
                    .setNegativeButtonColor(Color.GRAY)
                    .setNegativeButtonSize(18)
                    .setNegativeButton(getString(R.string.cancel), new IosDialog.OnClickListener() {
                        @Override
                        public void onClick(IosDialog dialog, View v) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButtonColor(ContextCompat.getColor(mContext, R.color.common_bule))
                    .setPositiveButtonSize(18)
                    .setPositiveButton(getString(R.string.sure), new IosDialog.OnClickListener() {
                        @Override
                        public void onClick(IosDialog dialog, View v) {
                            startActivity(new Intent(mContext, AuthenticationActivity.class));
                            dialog.dismiss();
                        }
                    }).build();
            dialog.show();
        } else {
            if (mXnbListBean.getIs_support_cash() == 1) {
                Intent intent = new Intent();
                intent.setClass(mContext, TabSellerActivity.class);
                intent.putExtra("xnb", mXnbListBean.getCoin_id() + "");
                intent.putExtra("xnb_name", mXnbListBean.getXnb_name());
                intent.putExtra("wallet_type", wallet_type);
                intent.putExtra("coin_id", mXnbListBean.getCoin_id() + "");
                mContext.startActivity(intent);
            } else if (mXnbListBean.getIs_support_cash() == 2) {
                ToastUtils.showToast(R.string.comingsoon);

            }

        }
    }
}
