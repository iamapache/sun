package com.madaex.exchange.ui.finance.c2c.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.c2c.bean.OrderFee;
import com.madaex.exchange.ui.finance.c2c.contract.TransationContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  TransationPresenter.java
 * 时间：  2018/9/25 15:32
 * 描述：  ${TODO}
 */

public class TransationPresenter  extends RxPresenter<TransationContract.View> implements TransationContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public TransationPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView, true) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getData()+"");
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData()+"");
                        }
                    }
                }));
    }

    @Override
    public void getView(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, OrderFee>() {
                    @Override
                    public OrderFee apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        OrderFee commonBean = gson.fromJson(data, OrderFee.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<OrderFee>(mView) {
                    @Override
                    public void onNext(OrderFee commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.sendViewSuccess(commonBean);
                        }
                    }
                }));
    }
}
