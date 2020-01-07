package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.market.bean.CoinDetail;
import com.madaex.exchange.ui.market.bean.HistoryDetail;
import com.madaex.exchange.ui.market.contract.CoinDetailContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  CoinDetailPresenter.java
 * 时间：  2018/10/22 10:56
 * 描述：  ${TODO}
 */

public class CoinDetailPresenter extends RxPresenter<CoinDetailContract.View> implements CoinDetailContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public CoinDetailPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CoinDetail>() {
                    @Override
                    public CoinDetail apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CoinDetail commonBean = gson.fromJson(data, CoinDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CoinDetail>(mView) {
                    @Override
                    public void onNext(CoinDetail commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void HistoryDetail(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, HistoryDetail>() {
                    @Override
                    public HistoryDetail apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        HistoryDetail commonBean = gson.fromJson(data, HistoryDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<HistoryDetail>(mView) {
                    @Override
                    public void onNext(HistoryDetail commonBean) {
//                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
//                            mView.requestError("");
//                        } else {
                            mView.requestSuccess(commonBean);
//                        }
                    }
                }));
    }
}
