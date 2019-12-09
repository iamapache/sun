package com.madaex.exchange.ui.finance.pay.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.Base64Utils;
import com.madaex.exchange.common.util.rsa;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;
import com.madaex.exchange.ui.finance.pay.contract.PlatContrct;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目：  sun
 * 类名：  PlatPresenter.java
 * 时间：  2019/5/15 10:44
 * 描述：  ${TODO}
 */
public class PlatPresenter extends RxPresenter<PlatContrct.View> implements PlatContrct.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public PlatPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, PlatRecord>() {
                    @Override
                    public PlatRecord apply(@NonNull String data) throws Exception {
                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data),rsa.PUBLIC_KEY));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();
                        PlatRecord commonBean = gson.fromJson(paramsStr, PlatRecord.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<PlatRecord>(mView, true) {
                    @Override
                    public void onNext(PlatRecord commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getData()+"");
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void submit(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data),rsa.PUBLIC_KEY));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();
                        CommonBean commonBean = gson.fromJson(paramsStr, CommonBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView, true) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(commonBean.getData()+"");
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                        }else {
                            mView.requestSuccess(commonBean.getData()+"");
                        }
                    }
                }));
    }
}
