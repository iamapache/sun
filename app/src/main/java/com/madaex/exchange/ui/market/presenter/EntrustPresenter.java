package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.market.bean.EntrustDetail;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.madaex.exchange.ui.market.contract.EntrustContract;

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

public class EntrustPresenter extends RxPresenter<EntrustContract.View> implements EntrustContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public EntrustPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, EntrustList>() {
                    @Override
                    public EntrustList apply(@NonNull String data) throws Exception {
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
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void delete(Map body) {
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
                            mView.deleteError(commonBean.getMessage()+"");
                        }else {
                            mView.deleteSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));
    }

    @Override
    public void getDataDetail(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, EntrustDetail>() {
                    @Override
                    public EntrustDetail apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        EntrustDetail commonBean = gson.fromJson(data, EntrustDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<EntrustDetail>(mView, true) {
                    @Override
                    public void onNext(EntrustDetail commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestEntrustDetailSuccess(commonBean);
                        }
                    }
                }));
    }
}
