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
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransation;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransationDetail;
import com.madaex.exchange.ui.finance.c2c.bean.TransationDetail;
import com.madaex.exchange.ui.finance.c2c.contract.C2CTransationContract;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  C2CTransationPresenter.java
 * 时间：  2018/8/30 12:27
 * 描述：  ${TODO}
 */

public class C2CTransationPresenter extends RxPresenter<C2CTransationContract.View> implements C2CTransationContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public C2CTransationPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, C2CTransation>() {
                    @Override
                    public C2CTransation apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();


                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            C2CTransation user = new C2CTransation();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            C2CTransation commonBean = gson.fromJson(data, C2CTransation.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<C2CTransation>(mView) {
                    @Override
                    public void onNext(C2CTransation commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }
    @Override
    public void getData2(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, C2CTransation>() {
                    @Override
                    public C2CTransation apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            C2CTransation user = new C2CTransation();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            C2CTransation commonBean = gson.fromJson(data, C2CTransation.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<C2CTransation>(mView) {
                    @Override
                    public void onNext(C2CTransation commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess2(commonBean.getData());
                        }
                    }
                }));
    }
    @Override
    public void getDetail(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, TransationDetail>() {
                    @Override
                    public TransationDetail apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        TransationDetail commonBean = gson.fromJson(data, TransationDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<TransationDetail>(mView) {
                    @Override
                    public void onNext(TransationDetail commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        } else {
                            mView.requestSuccess3(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void getc2cDetail(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, C2CTransationDetail>() {
                    @Override
                    public C2CTransationDetail apply(@NonNull String map) throws Exception {
                        Logger.i("<==>data:" + map);
                        Gson gson = new Gson();


                        CommonBaseBean commonBaseBean = gson.fromJson(map, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(map, CommonBean.class);
                            C2CTransationDetail user = new C2CTransationDetail();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            C2CTransationDetail commonBean = gson.fromJson(map, C2CTransationDetail.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<C2CTransationDetail>(mView) {
                    @Override
                    public void onNext(C2CTransationDetail commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMsg());
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }
}
