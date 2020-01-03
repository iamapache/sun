package com.madaex.exchange.ui.finance.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.bean.TransaList;
import com.madaex.exchange.ui.finance.contract.TransaContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  TransaPreaenter.java
 * 时间：  2018/9/4 17:28
 * 描述：  ${TODO}
 */

public class TransaPreaenter extends RxPresenter<TransaContract.View> implements TransaContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public TransaPreaenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, TransaList>() {
                    @Override
                    public TransaList apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            TransaList user = new TransaList();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            TransaList commonBean = gson.fromJson(data, TransaList.class);
                            return commonBean;
                        }

                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<TransaList>(mView,true) {
                    @Override
                    public void onNext(TransaList commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR||commonBean.getStatus() == -1) {
                            mView.requestError("");
                        } else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }
}
