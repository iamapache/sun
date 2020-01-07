package com.madaex.exchange.ui.market.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.common.view.CustomPopWindow;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.activity.EntrustDetailActivity;
import com.madaex.exchange.ui.market.bean.EntrustDetail;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.madaex.exchange.ui.market.contract.EntrustContract;
import com.madaex.exchange.ui.market.presenter.EntrustPresenter;
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
 * 项目：  madaexchange
 * 类名：  EntrustCurrentFragment.java
 * 时间：  2018/9/4 16:14
 * 描述：  ${TODO}
 */

public class EntrustCurrentFragment extends BaseNetLazyFragment<EntrustPresenter> implements EntrustContract.View {

    BaseQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.ll_pop)
    LinearLayout mLlPop;
    Unbinder unbinder;
    @BindView(R.id.tv_all)
    TextView mTvAll;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.ll_delete)
    LinearLayout mLlDelete;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    private String one_xnb = "";
    private String two_xnb = "";
    private String status = "0";
    private CustomPopWindow mCustomPopWindow;
    private String mEntrusttype;
    private Handler handler = new Handler();

    public static EntrustCurrentFragment newInstance(String string, String one_xnb, String two_xnb) {
        EntrustCurrentFragment fragment = null;
        if (fragment == null) {
            fragment = new EntrustCurrentFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ENTRUSTCURRENT, string);
        bundle.putString(Constants.ONE_XNB, one_xnb);
        bundle.putString(Constants.TWO_XNB, two_xnb);
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
        if(getActivity().getIntent().hasExtra("market_type")){
            market_type = getActivity().getIntent().getStringExtra("market_type");
        }else {
            market_type =  SPUtils.getString("market_type","0");
        }

        getdata();
    }
    int pageNum = 1;
    private void getdata() {
        if(market_type.equals("0")){
            TreeMap params = new TreeMap<>();
            if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                params.put("act", ConstantUrl.TRADE_CURRENT_ENTRUST);
            } else {
                params.put("act", ConstantUrl.TRADE_HISTORY_ENTRUST);
            }

            params.put("one_xnb", one_xnb);
            params.put("two_xnb", two_xnb);
            params.put("status", status);
            params.put("curPage", pageNum+"");
            mPresenter.getData(DataUtil.sign(params));
        }else {
            TreeMap params = new TreeMap<>();
            if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                params.put("act", ConstantUrl.Contract_CURRENT_ENTRUST);
            } else {
                params.put("act", ConstantUrl.Contract_HISTORY_ENTRUST);
            }

            params.put("one_xnb", one_xnb);
            params.put("two_xnb", two_xnb);
            params.put("status", status);
            params.put("curPage", pageNum+"");
            mPresenter.getData(DataUtil.sign(params));
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entrustcurrent;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        one_xnb = getArguments().getString(Constants.ONE_XNB);
        two_xnb = getArguments().getString(Constants.TWO_XNB);
        mEntrusttype = getArguments().getString(Constants.ENTRUSTCURRENT);
        if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
            mLlDelete.setVisibility(View.VISIBLE);
        } else {
            mLlDelete.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mAdapter = new BaseQuickAdapter<EntrustList.DataBean, BaseViewHolder>(R.layout.item_entrust) {
            @Override
            protected void convert(BaseViewHolder helper, final EntrustList.DataBean item) {
                helper.setText(R.id.price, item.getOne_xnb() + new BigDecimal(String.valueOf(item.getNum())).stripTrailingZeros().toPlainString()).
                        setText(R.id.num, item.getOne_xnb() + new BigDecimal(String.valueOf(item.getPrice())).stripTrailingZeros().toPlainString())
                        .setText(R.id.mum, item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getPrice())).stripTrailingZeros().toPlainString());
                ProgressBar progressBar = helper.getView(R.id.preview_progressBar);

                helper.setText(R.id.type, item.getStatuss()).setText(R.id.deal_type, item.getStatuss());
                if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                    helper.setGone(R.id.ll_line, true).setGone(R.id.ll_history, false)
                            .setGone(R.id.img_delete, true).setText(R.id.deal, item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getDeal())).stripTrailingZeros().toPlainString());
                    if (!TextUtils.isEmpty(item.getDeal()) || !TextUtils.isEmpty(item.getNum())) {
                        int pb = (int) (Double.valueOf(item.getDeal()) / Double.valueOf(item.getNum()));
                        progressBar.setProgress(pb);
                    }
                    helper.setText(R.id.mum, item.getTwo_xnb() + "--");
                } else {
                    helper.setGone(R.id.ll_line, false).setGone(R.id.ll_history, true)
                            .setGone(R.id.img_delete, false).setText(R.id.deal,
                            item.getOne_xnb() + new BigDecimal(String.valueOf(item.getNum())).stripTrailingZeros().toPlainString() + "=" + item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getDeal_money())).stripTrailingZeros().toPlainString());
                    if (!TextUtils.isEmpty(item.getAdd_time())) {
                        helper.setText(R.id.time, item.getAdd_time().split(" ")[0]).setText(R.id.time_hour, item.getAdd_time().split(" ")[1]);
                    }
                    if (item.getStatus() == 2) {
                        helper.setGone(R.id.ll_revoke, true);
                        helper.setText(R.id.deal, item.getOne_xnb() +  "0=" + item.getTwo_xnb() + new BigDecimal(String.valueOf(item.getDeal_money())).stripTrailingZeros().toPlainString());
                    } else {
                        helper.setGone(R.id.ll_revoke, false);
                    }
                    helper.setText(R.id.revoke, item.getOne_xnb() + new BigDecimal(String.valueOf(item.getCancel_number())).stripTrailingZeros().toPlainString());
                }
                if (mEntrusttype.equals(ConstantUrl.ENTRUSTCURRENT)) {
                    if (item.getType().equals("1")) {
                        helper.setText(R.id.type, R.string.buy).setText(R.id.deal_type, R.string.buy);
                        helper.setTextColor(R.id.price, getResources().getColor(R.color.common_red)).setTextColor(R.id.num, getResources().getColor(R.color.common_red))
                                .setTextColor(R.id.deal, getResources().getColor(R.color.common_red)).setBackgroundRes(R.id.deal_type, R.drawable.rect_rounded_red);
                        setProgressDrawable(progressBar, R.drawable.progressbar_red);
                    } else if (item.getType().equals("2")) {
                        helper.setText(R.id.type, R.string.seller).setText(R.id.deal_type, R.string.seller);
                        helper.setTextColor(R.id.price, getResources().getColor(R.color.common_green)).setTextColor(R.id.num, getResources().getColor(R.color.common_green))
                                .setTextColor(R.id.deal, getResources().getColor(R.color.common_green)).setBackgroundRes(R.id.deal_type, R.drawable.rect_rounded_arc);
                        setProgressDrawable(progressBar, R.drawable.progressbar_green);
                    }
                } else {
                    if (item.getType().equals("1")) {
                        helper.setTextColor(R.id.price, getResources().getColor(R.color.common_red)).setTextColor(R.id.num, getResources().getColor(R.color.common_red))
                                .setTextColor(R.id.deal, getResources().getColor(R.color.common_red)).setBackgroundRes(R.id.deal_type, R.drawable.rect_rounded_red);
                        setProgressDrawable(progressBar, R.drawable.progressbar_red);
                        helper.setTextColor(R.id.revoke, getResources().getColor(R.color.common_red));
                    } else if (item.getType().equals("2")) {

                        helper.setTextColor(R.id.price, getResources().getColor(R.color.common_green)).setTextColor(R.id.num, getResources().getColor(R.color.common_green))
                                .setTextColor(R.id.deal, getResources().getColor(R.color.common_green)).setBackgroundRes(R.id.deal_type, R.drawable.rect_rounded_arc);
                        setProgressDrawable(progressBar, R.drawable.progressbar_green);
                        helper.setTextColor(R.id.revoke, getResources().getColor(R.color.common_green));
                    }
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
                                                if(market_type.equals("0")){
                                                    TreeMap params = new TreeMap<>();
                                                    params.put("act", ConstantUrl.TRADE_REVOKE);
                                                    params.put("one_xnb", item.getOne_xnb());
                                                    params.put("two_xnb", item.getTwo_xnb());
                                                    params.put("id", item.getId());
                                                    mPresenter.delete(DataUtil.sign(params));
                                                }else {
                                                    TreeMap params = new TreeMap<>();
                                                    params.put("act", ConstantUrl.Contract_UPTRADE);
                                                    params.put("one_xnb", item.getOne_xnb());
                                                    params.put("two_xnb", item.getTwo_xnb());
                                                    params.put("id", item.getId());
                                                    mPresenter.delete(DataUtil.sign(params));
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
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                isRefresh =true;
                getdata();
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh =false;
                getdata();
            }
        }, mRecyclerview);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.view_empty_data, (ViewGroup) mRecyclerview.getParent());
        mLlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<EntrustList.DataBean> mDataBeans = (ArrayList<EntrustList.DataBean>) mAdapter.getData();
                if (!EmptyUtils.isEmpty(mDataBeans)) {
                    final StringBuffer stringBuffer = new StringBuffer();
                    for (EntrustList.DataBean dataBean : mDataBeans) {
                        stringBuffer.append(dataBean.getId());
                        stringBuffer.append(",");
                    }
                    final String str = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
                    TreeMap params = new TreeMap<>();
                    if(market_type.equals("0")){
                        params.put("act", ConstantUrl.TRADE_REVOKE);
                        params.put("one_xnb", one_xnb);
                        params.put("two_xnb", two_xnb);
                        params.put("id", str);
                    }else {
                        params.put("act", ConstantUrl.Contract_UPTRADE);
                        params.put("one_xnb", one_xnb);
                        params.put("two_xnb", two_xnb);
                        params.put("id", str);
                    }
                    mPresenter.delete(DataUtil.sign(params));
                }
            }
        });
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && requestCode == 250) {
            pageNum = 1;
            isRefresh =true;
            getdata();
        }

    }

    @Override
    protected void initDatas() {

    }
    private String market_type = "0";
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null && event.getCode() == Constants.DEAL) {
            String coin = event.getMsg();
            one_xnb = coin.split("/")[0];
            two_xnb = coin.split("/")[1];
            market_type= event.getHeyue();
            pageNum = 1;
            isRefresh =true;
            getdata();
        }

        if (event != null && event.getCode() == Constants.ENTRUST) {
            pageNum = 1;
            isRefresh =true;
            pageNum = 1;
            isRefresh =true;
            getdata();
        }
    }

    boolean isRefresh = true;
    @Override
    public void requestSuccess(EntrustList bean) {

        mSwiperefreshlayout.setRefreshing(false);
        if (bean != null) {
            setData(isRefresh, bean.getData());

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
    public void nodata(String msg) {
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void deleteSuccess(String msg) {
        ToastUtils.showToast(msg);
        pageNum = 1;
        isRefresh =true;
        getdata();
    }

    @Override
    public void deleteError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void requestEntrustDetailSuccess(EntrustDetail bean) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_all, R.id.tv_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:
                showAllPopMenu();
                break;
            case R.id.tv_price:
                showPricePopMenu();
                break;
        }
    }

    private void showPricePopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_entrust_price, null);
        //处理popWindow 显示内容
        handleLogicPrice(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create()
                .showAsDropDown(mLlPop, 80, 0);
    }

    private void handleLogicPrice(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        pageNum = 1;
                        isRefresh =true;
                        getdata();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
    }

    private void showAllPopMenu() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_entrust_all, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                    }
                })
                .create()
                .showAsDropDown(mLlPop, 0, 0);

    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.ll_item1:
                        status = "0";
                        mTvAll.setText(getString(R.string.all));
                        pageNum = 1;
                        isRefresh =true;
                        getdata();
                        break;
                    case R.id.ll_item2:
                        status = "1";
                        mTvAll.setText(getString(R.string.item_buy));
                        pageNum = 1;
                        isRefresh =true;
                        getdata();
                        break;
                    case R.id.ll_item3:
                        status = "2";
                        mTvAll.setText(getString(R.string.item_seller));
                        pageNum = 1;
                        isRefresh =true;
                        getdata();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_item1).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item2).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_item3).setOnClickListener(listener);
    }
}
