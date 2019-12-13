package com.madaex.exchange.ui.finance.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.finance.bean.BillList;
import com.madaex.exchange.ui.finance.contract.BillContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  BillPresenter.java
 * 时间：  2018/9/4 17:58
 * 描述：  ${TODO}
 */

public class BillPresenter extends RxPresenter<BillContract.View> implements BillContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public BillPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, BillList>() {
                    @Override
                    public BillList apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        BillList commonBean = gson.fromJson(data, BillList.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<BillList>(mView,false) {
                    @Override
                    public void onNext(BillList commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }
}
