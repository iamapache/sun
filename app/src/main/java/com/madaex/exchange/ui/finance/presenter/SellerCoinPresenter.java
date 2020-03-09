package com.madaex.exchange.ui.finance.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.finance.bean.SellerCoin;
import com.madaex.exchange.ui.finance.bean.TransaList;
import com.madaex.exchange.ui.finance.contract.SellerCoinContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  SellerCoinPresenter.java
 * 时间：  2018/8/30 15:54
 * 描述：  ${TODO}
 */

public class SellerCoinPresenter extends RxPresenter<SellerCoinContract.View> implements SellerCoinContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public SellerCoinPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, SellerCoin>() {
                    @Override
                    public SellerCoin apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        SellerCoin commonBean = gson.fromJson(data, SellerCoin.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<SellerCoin>(mView) {
                    @Override
                    public void onNext(SellerCoin commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void getCashCoin(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, TransaList>() {
                    @Override
                    public TransaList apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        TransaList commonBean = gson.fromJson(data, TransaList.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<TransaList>(mView) {
                    @Override
                    public void onNext(TransaList commonBean) {
//                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
//                            mView.requestError("");
//                        } else {
//                            mView.requestSuccess(commonBean);
//                        }
                    }
                }));
    }
}
