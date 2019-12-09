package com.madaex.exchange.ui.common;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.Base64Utils;
import com.madaex.exchange.common.util.FileEncryptionManager;
import com.madaex.exchange.common.util.rsa;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    public void getData(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
//                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
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
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData()+"");
                        }
                    }
                }));

    }

    @Override
    public void getMsgCode(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
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
                        if(commonBean.getStatus()==0){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.sendMsgSuccess(commonBean.getData());
                        }
                    }
                }));
    }
}
