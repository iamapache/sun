package com.madaex.exchange.ui.finance.c2c.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.finance.c2c.activity.AppealActivity;
import com.madaex.exchange.ui.finance.c2c.activity.ImageActivity;
import com.madaex.exchange.ui.finance.c2c.activity.ImageBankActivity;
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;
import com.madaex.exchange.ui.finance.c2c.bean.MyEntrust;
import com.madaex.exchange.ui.finance.c2c.contract.MyEntrustContract;
import com.madaex.exchange.ui.finance.c2c.presenter.MyEntrustPresenter;
import com.wc.widget.dialog.IosDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目：  sun
 * 类名：  EntrustOneFragment.java
 * 时间：  2019/5/13 19:30
 * 描述：  ${TODO}
 */
public class EntrustTwoFragment extends BaseNetFragment<MyEntrustPresenter> implements MyEntrustContract.View {

    int pageNum = 1;
    boolean isRefresh = true;
    private BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    Unbinder unbinder;

    public static EntrustTwoFragment newInstance(String string, int status) {
        EntrustTwoFragment fragment = null;
        if (fragment == null) {
            fragment = new EntrustTwoFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ENTRUSTCURRENT, string);
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entrustone;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        int status = getArguments().getInt("status");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mAdapter = new BaseQuickAdapter<EntrusTwo.DataBean.GoodsListBean, BaseViewHolder>(R.layout.item_myentrust) {
            @Override
            protected void convert(BaseViewHolder helper, final EntrusTwo.DataBean.GoodsListBean item) {

                helper.setText(R.id.num, item.getCoin_ename() + item.getTotal_num())
                        .setText(R.id.orderid, item.getOrder_no())
                        .setText(R.id.coinname, item.getCoin_ename())
                        .setText(R.id.number, item.getNum())
                        .setText(R.id.ordertime, item.getAddtime().split("-")[1] + "-"+ item.getAddtime().split("-")[2]  )
                        .setText(R.id.price, "￥" + item.getPrice())
                        .setText(R.id.coinname, item.getCoin_ename())
                        .setText(R.id.total, "￥" + item.getTotal())
                        .setText(R.id.id, item.getHead_right() + "");
                helper.setGone(R.id.ll_rk, true)
                        .setText(R.id.mark, item.getRemark_num());
                if (item.getHead_left().equals("1")) {
                    helper.setTextColor(R.id.num, getResources().getColor(R.color.common_red));
                    helper.setText(R.id.ustype, R.string.sellers);
                    helper.setText(R.id.type, getString(R.string.buy) + "");
                } else if (item.getHead_left().equals("2")) {
                    helper.setTextColor(R.id.num, getResources().getColor(R.color.common_green));
                    helper.setText(R.id.ustype, R.string.buyer);
                    helper.setText(R.id.type, getString(R.string.seller) + "");
                }
                if (status == 1) {
                    helper.setText(R.id.ustype, R.string.buyer);
                    helper.setText(R.id.type, getString(R.string.seller) + "");
                    if (item.getStatus().equals("0")) {
                        helper.setText(R.id.status, R.string.unpaids);
                        helper.setGone(R.id.ll_appeal, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("1")) {
                        helper.setText(R.id.status, R.string.paid);
                        helper.setGone(R.id.ll_appeal, true);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("2")) {
                        helper.setText(R.id.status, R.string.yok);
                        helper.setGone(R.id.ll_appeal, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("3")) {
                        helper.setText(R.id.status, R.string.cancels);
                        helper.setGone(R.id.ll_appeal, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("4")) {
                        helper.setText(R.id.status, R.string.closes);
                        helper.setGone(R.id.ll_appeal, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    }
                } else if (status == 0) {
                    helper.setText(R.id.ustype, R.string.sellers);
                    helper.setText(R.id.type, getString(R.string.buy) + "");
                    if (item.getStatus().equals("0")) {
                        helper.setText(R.id.status, R.string.unpaids);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, true);
                        helper.setGone(R.id.pay, true);
                    } else if (item.getStatus().equals("1")) {
                        helper.setText(R.id.status, R.string.paid);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("2")) {
                        helper.setText(R.id.status, R.string.yok);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("3")) {
                        helper.setText(R.id.status, R.string.cancels);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("4")) {
                        helper.setText(R.id.status, R.string.closes);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    }
                } else if (status == 2) {

                    if (item.getStatus().equals("0")) {
                        helper.setText(R.id.status, R.string.unpaids);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("1")) {
                        helper.setText(R.id.status, R.string.paid);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("2")) {
                        helper.setText(R.id.status, R.string.yok);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("3")) {
                        helper.setText(R.id.status, R.string.cancels);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("4")) {
                        helper.setText(R.id.status, R.string.closes);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    } else if (item.getStatus().equals("5")) {
                        helper.setText(R.id.status, R.string.complaint);
                        helper.setGone(R.id.submit, false);
                        helper.setGone(R.id.ll_pay, false);
                        helper.setGone(R.id.pay, false);
                    }
                }
                helper.getView(R.id.mark).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard2 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData2 = ClipData.newPlainText(null, item.getRemark_num());
                        clipboard2.setPrimaryClip(clipData2);
                        ToastUtils.showToast(getString(R.string.copyaddress));
                    }
                });
                helper.getView(R.id.submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new IosDialog.Builder(mContext).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                                .setMessage(R.string.Confirmreceiptofpayment).setMessageSize(14)
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
                                .setPositiveButton(R.string.sure, new IosDialog.OnClickListener() {
                                    @Override
                                    public void onClick(IosDialog dialog, View v) {
                                        dialog.dismiss();
                                        TreeMap params = new TreeMap<Object, Object>();
                                        params.put("act", ConstantUrl.FIAT_ORDER_CONFIRM);
                                        params.put("order_id", item.getId());
                                        mPresenter.order_confirm(DataUtil.sign(params));
                                    }
                                }).build();
                        dialog.show();
                    }
                });
                helper.getView(R.id.pay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Dialog dialog = new IosDialog.Builder(mContext).setTitle(getString(R.string.Confirmpayment)).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
                                    .setMessage("").setMessageSize(14)
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
                                    .setPositiveButton(R.string.sure, new IosDialog.OnClickListener() {
                                        @Override
                                        public void onClick(IosDialog dialog, View v) {
                                            dialog.dismiss();

                                            TreeMap params = new TreeMap<Object, Object>();
                                            params.put("act", ConstantUrl.FIAT_ORDER_PAY);
                                            params.put("order_id", item.getId());

//                        params.put("pay_way", 3 + "");
                                            mPresenter.order_pay(DataUtil.sign(params));
                                        }
                                    }).build();
                            dialog.show();
                    }
                });

                helper.getView(R.id.appeal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AppealActivity.class);
                        intent.putExtra("id", item.getId());
                        startActivityForResult(intent, 200);
                    }
                });
                Button wx = helper.getView(R.id.wx);
                Button zfb = helper.getView(R.id.zfb);
                Button bank = helper.getView(R.id.bank);
                if (EmptyUtils.isNotEmpty(item.getPayment_list())) {
                    for (EntrusTwo.DataBean.GoodsListBean.PaymentListBean paymentListBean : item.getPayment_list()) {
                        if (paymentListBean.getType().equals("1")) {
                            wx.setVisibility(View.VISIBLE);
                            wx.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, ImageActivity.class);
                                    intent.putExtra("bean", paymentListBean);
                                    startActivity(intent);
                                }
                            });
                        }
                        if (paymentListBean.getType().equals("2")) {
                            zfb.setVisibility(View.VISIBLE);
                            zfb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, ImageActivity.class);
                                    intent.putExtra("bean", paymentListBean);
                                    startActivity(intent);
                                }
                            });
                        }
                        if (paymentListBean.getType().equals("3")) {
                            bank.setVisibility(View.VISIBLE);
                            bank.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, ImageBankActivity.class);
                                    intent.putExtra("bean", paymentListBean);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }
        };
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                getData();
            }
        }, mRecyclerview);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
    }

    private void getData() {
        TreeMap params = new TreeMap<Object, Object>();
        params.put("act", ConstantUrl.FIAT_MY_ORDER_IN);
        params.put("coin_ename", coin_ename);
        params.put("curPage", pageNum + "");
        params.put("status", getArguments().getInt("status") + "");
        mPresenter.getData2(DataUtil.sign(params));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.buy) {
            String coin = event.getMsg();
            pageNum = 1;
            isRefresh = true;
            getData();
        }
        if (event != null && event.getCode() == Constants.sell) {
            String coin = event.getMsg();
            pageNum = 1;
            isRefresh = true;
            getData();
        }
    }

    private String coin_ename = "";

    @Override
    protected void initDatas() {
        coin_ename = getArguments().getString(Constants.ENTRUSTCURRENT, "GRC");
        getData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);
        pageNum = 1;
        isRefresh = true;
        getData();
    }

    @Override
    public void requestSuccess(MyEntrust msg) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && requestCode == 200) {
            pageNum = 1;
            isRefresh = true;
            getData();
        } else if (resultCode == Activity.RESULT_OK
                && requestCode == 20) {
            if (data != null) {
            }
        }

    }

    @Override
    public void requestSuccess(EntrusTwo msg) {
        mSwiperefreshlayout.setRefreshing(false);
        if (msg != null) {
            setData(isRefresh, msg.getData().getGoods_list());

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


    @Override
    public void requestError(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
        ToastUtils.showToast(msg);
    }

    @Override
    public void nodata(String msg) {

    }
}
