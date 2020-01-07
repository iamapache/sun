package com.madaex.exchange.ui.finance.c2c.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.c2c.contract.AppealContract;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

/**
 * 项目：  sun
 * 类名：  AppealPresenter.java
 * 时间：  2019/5/16 10:59
 * 描述：  ${TODO}
 */
public class AppealPresenter extends RxPresenter<AppealContract.View> implements AppealContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public AppealPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        RequestBody body;
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
                            mView.requestError(commonBean.getMessage()+"");
                        }else {
                            mView.deleteSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));
    }
}
