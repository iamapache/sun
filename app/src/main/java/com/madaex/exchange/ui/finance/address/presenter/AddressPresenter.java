package com.madaex.exchange.ui.finance.address.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.finance.address.bean.WalletAddress;
import com.madaex.exchange.ui.finance.address.contract.AddressContract;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  AddressPresenter.java
 * 时间：  2018/8/30 18:09
 * 描述：  ${TODO}
 */

public class AddressPresenter extends RxPresenter<AddressContract.View> implements AddressContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public AddressPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, WalletAddress>() {
                    @Override
                    public WalletAddress apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        WalletAddress commonBean = gson.fromJson(data, WalletAddress.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<WalletAddress>(mView) {
                    @Override
                    public void onNext(WalletAddress commonBean) {
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
}
