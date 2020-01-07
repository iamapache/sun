package com.madaex.exchange.ui.finance.bank.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.bank.contract.Bank;
import com.madaex.exchange.ui.finance.bank.contract.BankContract;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  BankPresenter.java
 * 时间：  2018/8/29 14:57
 * 描述：  ${TODO}
 */

public class BankPresenter extends RxPresenter<BankContract.View> implements BankContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public BankPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, Bank>() {
                    @Override
                    public Bank apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();


                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            Bank user = new Bank();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            Bank commonBean = gson.fromJson(data, Bank.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Bank>(mView) {
                    @Override
                    public void onNext(Bank commonBean) {
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
                            mView.deleteError(commonBean.getData()+"");
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getMessage()+"");
                        }else {
                            mView.deleteSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));
    }
}
