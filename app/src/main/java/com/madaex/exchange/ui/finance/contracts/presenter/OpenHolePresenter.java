package com.madaex.exchange.ui.finance.contracts.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.finance.contracts.bean.OpenHoleDestail;
import com.madaex.exchange.ui.finance.contracts.contract.OpenHoleContract;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  sun
 * 类名：  OpenHolePresenter.java
 * 时间：  2020/7/7 11:28
 * 描述：
 */
public class OpenHolePresenter extends RxPresenter<OpenHoleContract.View> implements OpenHoleContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public OpenHolePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, OpenHoleDestail>() {
                    @Override
                    public OpenHoleDestail apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        OpenHoleDestail commonBean = gson.fromJson(data, OpenHoleDestail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<OpenHoleDestail>(mView, true) {
                    @Override
                    public void onNext(OpenHoleDestail commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                            mView.onUnLogin();
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            if (mView != null) {
                                mView.requestSuccess(commonBean);
                            }

                        }
                    }
                }));
    }

    @Override
    public void transfer(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        CommonDataBean CommonDataBean = gson.fromJson(data, CommonDataBean.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView, true) {
                    @Override
                    public void onNext(CommonDataBean CommonDataBean) {
                        if (CommonDataBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(CommonDataBean.getMessage());
                        } else if (CommonDataBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                            mView.onUnLogin();
                        } else if (CommonDataBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.requestSuccess(CommonDataBean.getMessage());
                        }
                    }
                }));
    }
}
