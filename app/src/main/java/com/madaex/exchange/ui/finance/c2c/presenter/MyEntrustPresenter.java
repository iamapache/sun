package com.madaex.exchange.ui.finance.c2c.presenter;

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
import com.madaex.exchange.ui.finance.c2c.bean.EntrusTwo;
import com.madaex.exchange.ui.finance.c2c.bean.MyEntrust;
import com.madaex.exchange.ui.finance.c2c.contract.MyEntrustContract;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目：  sun
 * 类名：  EntrustPresenter.java
 * 时间：  2019/5/14 11:39
 * 描述：  ${TODO}
 */
public class MyEntrustPresenter extends RxPresenter<MyEntrustContract.View> implements MyEntrustContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public MyEntrustPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, MyEntrust>() {
                    @Override
                    public MyEntrust apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        MyEntrust commonBean = gson.fromJson(data, MyEntrust.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<MyEntrust>(mView, true) {
                    @Override
                    public void onNext(MyEntrust commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void cancel(Map map) {
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
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }

    @Override
    public void getData2(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, EntrusTwo>() {
                    @Override
                    public EntrusTwo apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        EntrusTwo commonBean = gson.fromJson(data, EntrusTwo.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<EntrusTwo>(mView, true) {
                    @Override
                    public void onNext(EntrusTwo commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void order_pay(Map map) {
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
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }

    @Override
    public void saveUserHeadImage(Map body, ArrayList<String> pathList) {
//        MultipartBody multipartBody = ImageUploadUtil.filesToMultipartBody(pathList);
        File file = new File(pathList.get(0));
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData(" pay_img", file.getName(), requestFile);
        addSubscribe((Disposable) rxApi.saveUserHeadImage(body,multipartBody)
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

    @Override
    public void order_confirm(Map map) {
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
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }
}
