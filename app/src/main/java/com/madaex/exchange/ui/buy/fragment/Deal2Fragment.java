package com.madaex.exchange.ui.buy.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.buy.presenter.CoinPresenter;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.activity.EntrustActivity;
import com.madaex.exchange.ui.market.activity.EntrustDetailActivity;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.madaex.exchange.ui.market.bean.Home;
import com.wc.widget.dialog.IosDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目：  exchange
 * 类名：  MineFragment.java
 * 时间：  2018/8/22 10:24
 * 描述：  ${TODO}
 */

public class Deal2Fragment extends BaseNetLazyFragment<CoinPresenter> implements CoinContract.View, OnTabSelectListener {
    @BindView(R.id.tab_layout)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTv;
    Unbinder unbinder;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mViewList = new ArrayList<>();
    private String[] mTitles_2 = new String[2];
    private String one_xnb = "";
    private String two_xnb = "";


    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    //    @BindView(R.id.swiperefreshlayout)
//    SwipeRefreshLayout mSwiperefreshlayout;
    private String status = "0";

    public static Deal2Fragment newInstance(int string) {
        Deal2Fragment fragment = null;
        if (fragment == null) {
            fragment = new Deal2Fragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, string);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dealtwo;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        mTitles_2[0] = this.getString(R.string.item_buy);
        mTitles_2[1] = this.getString(R.string.item_seller);
        one_xnb = SPUtils.getString(Constants.ONE_XNB, "BAT");
        two_xnb = SPUtils.getString(Constants.TWO_XNB, "ETH");
        mToolbarTitleTv.setText(one_xnb + "/" + two_xnb);
        int str = (int) getArguments().get(Constants.ARGS);
        Buy2Fragment fragment1 = Buy2Fragment.newInstance(ConstantUrl.TRANS_TYPE_BUY, one_xnb, two_xnb);
        Buy2Fragment fragment2 = Buy2Fragment.newInstance(ConstantUrl.TRANS_TYPE_SELLER, one_xnb, two_xnb);
        mViewList.add(fragment1);
        mViewList.add(fragment2);


        mPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mTabLayout.setTabData(mTitles_2);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (getActivity().getIntent().hasExtra("market_type")) {
            market_type = getActivity().getIntent().getStringExtra("market_type");
        } else {
            market_type = SPUtils.getString("market_type", "0");
        }

        getdata();
    }


    private String market_type = "0";

    boolean isRefresh = true;
    private String mEntrusttype = "1";
    int pageNum = 1;

    private void getdata() {
        if (market_type.equals("0")) {
            TreeMap params = new TreeMap<>();
            if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                params.put("act", ConstantUrl.TRADE_CURRENT_ENTRUST);
            } else {
                params.put("act", ConstantUrl.TRADE_HISTORY_ENTRUST);
            }

            params.put("one_xnb", one_xnb);
            params.put("two_xnb", two_xnb);
            params.put("status", status);
            params.put("curPage", pageNum + "");
            mPresenter.getDataenn(DataUtil.sign(params));
        } else {
            TreeMap params = new TreeMap<>();
            if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                params.put("act", ConstantUrl.Contract_CURRENT_ENTRUST);
            } else {
                params.put("act", ConstantUrl.Contract_HISTORY_ENTRUST);
            }

            params.put("one_xnb", one_xnb);
            params.put("two_xnb", two_xnb);
            params.put("status", status);
            params.put("curPage", pageNum + "");
            mPresenter.getDataenn(DataUtil.sign(params));
        }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_2[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mViewList.get(position);
        }
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

        mAdapter = new BaseQuickAdapter<EntrustList.DataBean, BaseViewHolder>(R.layout.item_entrust2) {
            @Override
            protected void convert(BaseViewHolder helper, final EntrustList.DataBean item) {
                helper.setText(R.id.price, new BigDecimal(String.valueOf(item.getNum())).stripTrailingZeros().toPlainString()).
                        setText(R.id.num, item.getOne_xnb() + new BigDecimal(String.valueOf(item.getPrice())).stripTrailingZeros().toPlainString())
                        .setText(R.id.createtime, item.getAddtime())
                        .setText(R.id.coinname, getString(R.string.price) + "(" + item.getTwo_xnb() + ")")
                        .setText(R.id.coinprice, getString(R.string.amount) + "（VDS）")
                        .setText(R.id.name, item.getOne_xnb() + "/" + item.getTwo_xnb())
                        .setText(R.id.mum, new BigDecimal(String.valueOf(item.getPrice())).stripTrailingZeros().toPlainString());
                ProgressBar progressBar = helper.getView(R.id.preview_progressBar);

                helper.setText(R.id.type, item.getStatuss()).setText(R.id.deal_type, item.getStatuss());
                if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                    helper.setGone(R.id.ll_line, false).setGone(R.id.ll_history, false)
                            .setGone(R.id.img_delete, true).setText(R.id.deal, item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getDeal())).stripTrailingZeros().toPlainString());
                    if (!TextUtils.isEmpty(item.getDeal()) || !TextUtils.isEmpty(item.getNum())) {
                        int pb = (int) (Double.valueOf(item.getDeal()) / Double.valueOf(item.getNum()));
                        progressBar.setProgress(pb);
                    }
                } else {
                    helper.setGone(R.id.ll_line, false).setGone(R.id.ll_history, true)
                            .setGone(R.id.img_delete, false).setText(R.id.deal,
                            item.getOne_xnb() + new BigDecimal(String.valueOf(item.getNum())).stripTrailingZeros().toPlainString() + "=" + item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getDeal_money())).stripTrailingZeros().toPlainString());
                    if (!TextUtils.isEmpty(item.getAdd_time())) {
                        helper.setText(R.id.time, item.getAdd_time().split(" ")[0]).setText(R.id.time_hour, item.getAdd_time().split(" ")[1]);
                    }
                    if (item.getStatus() == 2) {
                        helper.setGone(R.id.ll_revoke, true);
                        helper.setText(R.id.deal, item.getOne_xnb() + "0=" + item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getDeal_money())).stripTrailingZeros().toPlainString());
                    } else {
                        helper.setGone(R.id.ll_revoke, false);
                    }
                    helper.setText(R.id.revoke, item.getOne_xnb() + new BigDecimal(String.valueOf(item.getCancel_number())).stripTrailingZeros().toPlainString());
                }

                helper.getView(R.id.img_delete).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dialog dialog = new IosDialog.Builder(mContext).setTitle(R.string.submitdelete).setTitleColor(ContextCompat.getColor(mContext, R.color.common_text_1)).setTitleSize(20)
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
                                        .setPositiveButton(R.string.submit, new IosDialog.OnClickListener() {
                                            @Override
                                            public void onClick(IosDialog dialog, View v) {
                                                dialog.dismiss();
                                                if (market_type.equals("0")) {
                                                    TreeMap params = new TreeMap<>();
                                                    params.put("act", ConstantUrl.TRADE_REVOKE);
                                                    params.put("one_xnb", item.getOne_xnb());
                                                    params.put("two_xnb", item.getTwo_xnb());
                                                    params.put("id", item.getId());
                                                    mPresenter.deleteenn(DataUtil.sign(params));
                                                } else {
                                                    TreeMap params = new TreeMap<>();
                                                    params.put("act", ConstantUrl.Contract_REVOKE);
                                                    params.put("one_xnb", item.getOne_xnb());
                                                    params.put("two_xnb", item.getTwo_xnb());
                                                    params.put("id", item.getId());
                                                    mPresenter.deleteenn(DataUtil.sign(params));
                                                }

                                            }
                                        }).build();
                                dialog.show();


                            }
                        });
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                    EntrustList.DataBean item = (EntrustList.DataBean) adapter.getItem(position);
                    Intent intent = new Intent();
                    intent.setClass(mContext, EntrustDetailActivity.class);
                    intent.putExtra("bean", item);
                    intent.putExtra("market_type", market_type);
                    if (item.getType().equals("1")) {
                        intent.putExtra("type", 1);
                    } else if (item.getType().equals("2")) {
                        intent.putExtra("type", 2);
                    }
                    startActivityForResult(intent, 250);
                }
            }
        });
//        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pageNum = 1;
//                isRefresh =true;
//                getdata();
//            }
//        });
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                isRefresh =false;
//                getdata();
//            }
//        }, mRecyclerview);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.view_empty_data, (ViewGroup) mRecyclerview.getParent());
    }

    @SuppressLint("NewApi")
    public static void setProgressDrawable(@NonNull ProgressBar bar, @DrawableRes int resId) {
        Drawable layerDrawable = bar.getResources().getDrawable(resId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable d = getMethod("tileify", bar, new Object[]{layerDrawable, false});
            bar.setProgressDrawable(d);
        } else {
            bar.setProgressDrawableTiled(layerDrawable);
        }
    }

    private static Drawable getMethod(String methodName, Object o, Object[] paras) {
        Drawable newDrawable = null;
        try {
            Class<?> c[] = new Class[2];
            c[0] = Drawable.class;
            c[1] = boolean.class;
            Method method = ProgressBar.class.getDeclaredMethod(methodName, c);
            method.setAccessible(true);
            newDrawable = (Drawable) method.invoke(o, paras);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newDrawable;
    }

    @Override
    protected void initDatas() {

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

    @OnClick({R.id.toolbar_left_btn_ll, R.id.toolbar_right_btn_ll, R.id.history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.history:
                Intent intent = getActivity().getIntent();
                intent.setClass(mContext, EntrustActivity.class);
                intent.putExtra(Constants.ONE_XNB, one_xnb);
                intent.putExtra(Constants.TWO_XNB, two_xnb);
                startActivity(intent);
                break;
            case R.id.toolbar_left_btn_ll:
                FragmentManager fm = getChildFragmentManager();
                CoinListFrament2 editNameDialog = CoinListFrament2.newInstance(Constants.DEAL, one_xnb, two_xnb);
                editNameDialog.show(fm, "fragment_bottom_dialog");
                break;
            case R.id.toolbar_right_btn_ll:
//                Intent intent = new Intent(mContext, DealActivity.class);
//                intent.putExtra("market", one_xnb + "_" + two_xnb);
//                intent.putExtra("one_xnb", one_xnb);
//                intent.putExtra("two_xnb", two_xnb);
//                startActivity(intent);
                FragmentManager fm0 = getFragmentManager();
                HistoryRecordFrament historyRecordFrament = HistoryRecordFrament.newInstance(Constants.MARK, one_xnb, two_xnb);
                historyRecordFrament.show(fm0, "fragment_bottom_dialog");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.DEAL) {
            String coin = event.getMsg();
            mToolbarTitleTv.setText(coin);
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];
            market_type = event.getHeyue();
            pageNum = 1;
            isRefresh = true;
            getdata();
        }

        if (event != null && event.getCode() == Constants.ENTRUST) {
            pageNum = 1;
            isRefresh = true;
            pageNum = 1;
            isRefresh = true;
            getdata();
        }
    }


    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
//        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void sendMsgSuccess(final CoinList msg) {
    }

    @Override
    public void sendDealSuccess(String msg) {

    }

    @Override
    public void requestDetailSuccess(Home baseBean) {

    }

    @Override
    public void requestToken(String baseBean) {

    }


    @Override
    public void sendMsgSuccess(DealInfo msg) {

    }

    @Override
    public void requestSuccess(EntrustList bean) {
        mAdapter.setNewData(bean.getData());
//        mSwiperefreshlayout.setRefreshing(false);
//        if (bean != null) {
//            setData(isRefresh, bean.getData());
//
//        } else {
//            mAdapter.setEnableLoadMore(true);
//        }
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
    public void nodata(String msg) {
//        mSwiperefreshlayout.setRefreshing(false);
    }


    @Override
    public void deleteSuccess(String msg) {
        ToastUtils.showToast(msg);
        pageNum = 1;
        isRefresh = true;
        getdata();
    }

    @Override
    public void deleteError(String msg) {
        ToastUtils.showToast(msg);
    }


    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }
}
