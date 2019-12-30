package com.madaex.exchange.ui.common;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  CommonPresenter.java
 * 时间：  2018/8/29 10:01
 * 描述：  ${TODO}
 */

public class CommonPresenter extends RxPresenter<CommonContract.View> implements CommonContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public CommonPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map str) {
        addSubscribe((Disposable) rxApi.getTestResult2(str)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
//                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
//                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data),rsa.PUBLIC_KEY));
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        CommonDataBean CommonDataBean = gson.fromJson(data, CommonDataBean.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView, true) {
                    @Override
                    public void onNext(CommonDataBean CommonDataBean) {
                        if(CommonDataBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(CommonDataBean.getMessage()+"");
                        }else  if(CommonDataBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(CommonDataBean.getMessage()+"");
                        }else {
                            mView.requestSuccess(CommonDataBean.getData()+"");
                        }
                    }
                }));

    }

    @Override
    public void getData2(Map str) {
        addSubscribe((Disposable) rxApi.getTestResult2(str)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        CommonDataBean CommonDataBean = gson.fromJson(data, CommonDataBean.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView, true) {
                    @Override
                    public void onNext(CommonDataBean CommonDataBean) {
                        if(CommonDataBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError(CommonDataBean.getMessage()+"");
                        }else  if(CommonDataBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(CommonDataBean.getMessage()+"");
                        }else {
                            mView.requestSuccess2(CommonDataBean.getData());
                        }
                    }
                }));

    }

    @Override
    public void getMsgCode(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult2(body)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        CommonDataBean CommonDataBean = gson.fromJson(data, CommonDataBean.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView, true) {
                    @Override
                    public void onNext(CommonDataBean CommonDataBean) {
                        if(CommonDataBean.getStatus()==0){
                            mView.requestError(CommonDataBean.getMessage()+"");
                        }else {
                            mView.sendMsgSuccess(CommonDataBean.getMessage());
                        }
                    }
                }));
    }
}
