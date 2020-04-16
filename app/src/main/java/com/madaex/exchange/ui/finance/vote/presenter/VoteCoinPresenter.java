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
import com.madaex.exchange.ui.common.DataBean;
import com.madaex.exchange.ui.finance.bean.Asset;
import com.madaex.exchange.ui.finance.vote.bean.NOWVOTE;
import com.madaex.exchange.ui.finance.vote.bean.VoteCoin;
import com.madaex.exchange.ui.finance.vote.bean.issue;
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
                .map(new Function<String, DataBean>() {
                    @Override
                    public DataBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        DataBean commonBean = gson.fromJson(data, DataBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<DataBean>(mView, true) {
                    @Override
                    public void onNext(DataBean commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getMessage()+"");
                        }else {
                            mView.requestSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));
    }

    @Override
    public void getGRC(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, Asset>() {
                    @Override
                    public Asset apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        Asset commonBean = gson.fromJson(data, Asset.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Asset>(mView) {
                    @Override
                    public void onNext(Asset commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.sendViewSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void go_issue(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, issue>() {
                    @Override
                    public issue apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        issue commonBean = gson.fromJson(data, issue.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<issue>(mView) {
                    @Override
                    public void onNext(issue commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.sendViewSuccess(commonBean);
                        }
                    }
                }));
    }

    public void NOWVOTE(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, NOWVOTE>() {
                    @Override
                    public NOWVOTE apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        NOWVOTE commonBean = gson.fromJson(data, NOWVOTE.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<NOWVOTE>(mView) {
                    @Override
                    public void onNext(NOWVOTE commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else {
                            mView.sendViewSuccess(commonBean);
                        }
                    }
                }));
    }
}
