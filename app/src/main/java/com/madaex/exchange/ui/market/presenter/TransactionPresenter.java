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
import com.madaex.exchange.ui.market.bean.TransactionList;
import com.madaex.exchange.ui.market.contract.TransactionContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  TransactionPresenter.java
 * 时间：  2018/8/31 14:55
 * 描述：  ${TODO}
 */

public class TransactionPresenter extends RxPresenter<TransactionContract.View> implements TransactionContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public TransactionPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, TransactionList>() {
                    @Override
                    public TransactionList apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            TransactionList user = new TransactionList();
                            user.setMsg(commonBean.getData());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            TransactionList commonBean = gson.fromJson(data, TransactionList.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<TransactionList>(mView) {
                    @Override
                    public void onNext(TransactionList commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR||commonBean.getStatus() == -1) {
                        } else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }

}
