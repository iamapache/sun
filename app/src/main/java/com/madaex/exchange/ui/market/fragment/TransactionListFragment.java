package com.madaex.exchange.ui.market.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.activity.BaseNetLazyFragment;
import com.madaex.exchange.common.net.ResponeThrowable;
import com.madaex.exchange.common.net.RetrofitHelper;
import com.madaex.exchange.common.rx.DefaultSubscriber;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.EmptyUtils;
import com.madaex.exchange.common.util.SPUtils;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.buy.bean.Event;
import com.madaex.exchange.ui.constant.ConstantUrl;
import com.madaex.exchange.ui.constant.Constants;
import com.madaex.exchange.ui.market.adapter.KineQuickAdapter;
import com.madaex.exchange.ui.market.bean.Home;
import com.madaex.exchange.ui.market.bean.HomeData;
import com.madaex.exchange.ui.market.bean.TitleBean;
import com.madaex.exchange.ui.market.contract.HomeContract;
import com.madaex.exchange.ui.market.presenter.HomePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目：  exchange
 * 类名：  TransactionListActivity.java
 * 时间：  2018/8/22 13:51
 * 描述：  ${TODO}
 */

public class TransactionListFragment extends BaseNetLazyFragment<HomePresenter> implements HomeContract.View, KineQuickAdapter.OnclikCallBack {
    KineQuickAdapter mAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.sort)
    TextView mSort;
    private int mCurrentPosition = -1;
    @BindView(R.id.search_asset)
    EditText mSearchAsset;
    private String searchAsset = "";

    public static TransactionListFragment newInstance(int parentposition, String string) {
        TransactionListFragment fragment = null;
        if (fragment == null) {
            fragment = new TransactionListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, string);
        bundle.putInt(Constants.INFO, parentposition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction;
    }

    @Override
    public void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        if (isAdded()) {
            mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            int parentposition = getArguments().getInt(Constants.INFO);
            mAdapter = new KineQuickAdapter(parentposition);
            mRecyclerview.setAdapter(mAdapter);

            mAdapter.setCallBack(this);
            mSearchAsset.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mCurrentPosition = -1;
                    searchAsset = editable.toString();
                    mAdapter.filter(editable.toString(), sort, -1, true);
                }
            });
            mSort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sort == 0) {
                        sort = 1;
                        Drawable drawableLeft = mContext.getResources().getDrawable(
                                R.mipmap.sort_top);
                        mSort.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, drawableLeft, null);
                        mSort.setCompoundDrawablePadding(5);
                    } else if (sort == 1) {
                        sort = 2;
                        Drawable drawableLeft = mContext.getResources().getDrawable(
                                R.mipmap.sort_bottom);
                        mSort.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, drawableLeft, null);
                        mSort.setCompoundDrawablePadding(5);
                    } else if (sort == 2) {
                        sort = 0;
                        Drawable drawableLeft = mContext.getResources().getDrawable(
                                R.mipmap.topbottom);
                        mSort.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, drawableLeft, null);
                        mSort.setCompoundDrawablePadding(5);
                    }
                    mCurrentPosition = -1;
                    mAdapter.filter(searchAsset, sort, -1, false);
                }
            });
        }
    }


    @Override
    protected void initDatas() {

    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isVisible = false;
        isPrepared = false;
        getData();

    }

    private void getData() {
        if (isAdded()) {
            String str = getArguments().getString(Constants.ARGS);
            TreeMap params = new TreeMap<>();
            params.put("act", ConstantUrl.TRADE_HOME_INDEX);
            params.put("name", str);
            if (str.equalsIgnoreCase(getString(R.string.favorites))) {
                if (!TextUtils.isEmpty(SPUtils.getString(Constants.TOKEN, ""))) {
                    try {
                        sendData(DataUtil.sign(params));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
//            mPresenter.getData(DataUtil.sign(params));

                try {
                    sendData(DataUtil.sign(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendData(Map params) throws Exception {
        Observable.interval(0, 30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        RetrofitHelper.getKLineAPI()
                                .getTestResult(params).map(new Function<String, HomeData>() {
                            @Override
                            public HomeData apply(@NonNull String data) throws Exception {
                                Gson gson = new Gson();
                                HomeData commonBean = gson.fromJson(data, HomeData.class);
                                return commonBean;
                            }
                        })
                                .compose(bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DefaultSubscriber<HomeData>() {
                                    @Override
                                    public void onError(ResponeThrowable e) {

                                    }

                                    @Override
                                    public void onNext(HomeData commonBean) {
                                        requestSuccess(commonBean.getData());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void requestSuccess(final List<Home> msg) {
        if(EmptyUtils.isNotEmpty(msg)){
            for (int j = 0; j < msg.size(); j++) {
                if (mCurrentPosition == j) {
                    msg.get(j).isShow = true;
                }
            }
            mAdapter.setData(msg);
//        mAdapter.setNewData(msg);
            mAdapter.filter(searchAsset, sort, mCurrentPosition, false);
        }

    }

    private int sort = 0;

    @Override
    public void requestError(String msg) {
        ToastUtils.showToast(msg);
        mCurrentPosition = -1;
        getData();
    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
    }

    @Override
    public void requestSuccess(String msg) {
        ToastUtils.showToast(msg);

    }

    @Override
    public void requestMessageCountSuccess(String msg) {

    }

    @Override
    public void SuccessTitle(TitleBean msg) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if(isAdded()) {
            String str = getArguments().getString(Constants.ARGS);
            if (event != null && event.getCode() == Constants.getMARK) {
                if (str.equalsIgnoreCase(getString(R.string.favorites))) {
//                getData();
                }
            } else if (event != null && event.getCode() == Constants.LOGINSUCCESS) {
                if (str.equalsIgnoreCase(getString(R.string.favorites))) {
                    getData();
                }
            }
        }
    }

    @Override
    public void doSomeThing(Home item) {
        HashMap params = new HashMap<>();
        params.put("act", ConstantUrl.TRADE_IS_COLLECTION);
        params.put("one_xnb", item.getCurrentype());
        params.put("two_xnb", item.getExchangeType());
        params.put("status", item.getCollection() == 0 ? 1 + "" : 0 + "");
        mPresenter.collection(DataUtil.sign(params));

    }

    @Override
    public void doItem(int item) {
        mCurrentPosition = item;
    }



}
