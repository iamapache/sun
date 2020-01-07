package com.madaex.exchange.ui.finance.c2c.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.c2c.bean.EntractDetail;
import com.madaex.exchange.ui.finance.c2c.contract.C2CEntrustContract;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  EntrustPresenter.java
 * 时间：  2018/9/5 15:33
 * 描述：  ${TODO}
 */

public class C2CEntrustPresenter extends RxPresenter<C2CEntrustContract.View> implements C2CEntrustContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public C2CEntrustPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, EntrustList>() {
                    @Override
                    public EntrustList apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();


                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            EntrustList user = new EntrustList();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            EntrustList commonBean = gson.fromJson(data, EntrustList.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<EntrustList>(mView) {
                    @Override
                    public void onNext(EntrustList commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMsg());
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                        }
                    }
                }));
    }

    @Override
    public void delete(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
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
                            mView.deleteError(commonBean.getMessage()+"");
                        }else {
                            mView.deleteSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));
    }

    @Override
    public void getDataDetail(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, EntractDetail>() {
                    @Override
                    public EntractDetail apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        EntractDetail commonBean = gson.fromJson(data, EntractDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<EntractDetail>(mView, true) {
                    @Override
                    public void onNext(EntractDetail commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestEntrustDetailSuccess(commonBean);
                        }
                    }
                }));
    }
}
