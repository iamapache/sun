package com.madaex.exchange.ui.finance.vote.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.common.SBData;
import com.madaex.exchange.ui.finance.c2c.bean.TransationInfo;
import com.madaex.exchange.ui.finance.vote.bean.VoteCoin;
import com.madaex.exchange.ui.finance.vote.contract.VoteCoinContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  VoteCoinPresenter.java
 * 时间：  2018/9/10 16:46
 * 描述：  ${TODO}
 */

public class VoteCoinPresenter extends RxPresenter<VoteCoinContract.View> implements VoteCoinContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public VoteCoinPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, VoteCoin>() {
                    @Override
                    public VoteCoin apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            VoteCoin user = new VoteCoin();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            VoteCoin commonBean = gson.fromJson(data, VoteCoin.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<VoteCoin>(mView) {
                    @Override
                    public void onNext(VoteCoin commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMsg() + "");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        } else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }

    @Override
    public void getData2(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, SBData>() {
                    @Override
                    public SBData apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        SBData commonBean = gson.fromJson(data, SBData.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<SBData>(mView, true) {
                    @Override
                    public void onNext(SBData commonBean) {
                        if(commonBean.getCode()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getMsg()+"");
                        }else {
                            mView.requestSuccess(commonBean.getMsg()+"");
                        }
                    }
                }));
    }

    @Override
    public void getGRC(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, TransationInfo>() {
                    @Override
                    public TransationInfo apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        TransationInfo commonBean = gson.fromJson(data, TransationInfo.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<TransationInfo>(mView) {
                    @Override
                    public void onNext(TransationInfo commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.sendViewSuccess(commonBean);
                        }
                    }
                }));
    }
}
