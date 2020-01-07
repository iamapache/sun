package com.madaex.exchange.ui.finance.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.finance.contract.ConfirmTransaContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  ConfirmTransaPresenter.java
 * 时间：  2018/9/30 15:07
 * 描述：  ${TODO}
 */

public class ConfirmTransaPresenter extends RxPresenter<ConfirmTransaContract.View> implements ConfirmTransaContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public ConfirmTransaPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonDataBean commonBean = gson.fromJson(data, CommonDataBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView, true) {
                    @Override
                    public void onNext(CommonDataBean commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus()== -1){
                            mView.requestError(commonBean.getMessage()+"");
                        }else {
                            mView.requestSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));

    }

    @Override
    public void getMsgCode(Map body) {
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
                        if(commonBean.getStatus()==0){
                            mView.requestMsgError(commonBean.getMessage()+"");
                        }else {
                            mView.sendMsgSuccess(commonBean.getData().getToken());
                        }
                    }
                }));
    }
}
