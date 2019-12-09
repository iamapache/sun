package com.madaex.exchange.ui.finance.transfer.presenter;

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
import com.madaex.exchange.ui.finance.transfer.contract.CoinList;
import com.madaex.exchange.ui.finance.transfer.contract.NcContract;
import com.madaex.exchange.ui.finance.transfer.contract.Ncrecord;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目：  sunn
 * 类名：  NcPresenter.java
 * 时间：  2019/5/23 10:26
 * 描述：  ${TODO}
 */
public class NcPresenter extends RxPresenter<NcContract.View> implements NcContract.Presenter
{
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public NcPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, Ncrecord>() {
                    @Override
                    public Ncrecord apply(@NonNull String data) throws Exception {
                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data), rsa.PUBLIC_KEY));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();
                        Ncrecord commonBean = gson.fromJson(paramsStr, Ncrecord.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Ncrecord>(mView, true) {
                    @Override
                    public void onNext(Ncrecord commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getData() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }
    @Override
    public void getData2(String str) {
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
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data), rsa.PUBLIC_KEY));
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
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getData() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else {
                            mView.requestSuccess(commonBean.getData() + "");
                        }
                    }
                }));
    }

    @Override
    public void saveUserHeadImage(String str, ArrayList<String> pathList) {
        //        MultipartBody multipartBody = ImageUploadUtil.filesToMultipartBody(pathList);
        File file = new File(pathList.get(0));
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), str);
        addSubscribe((Disposable) rxApi.saveUserHeadImage(body, multipartBody)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data), rsa.PUBLIC_KEY));
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
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getData() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else {
                            mView.requestSuccess(commonBean.getData() + "");
                        }
                    }
                }));
    }

    @Override
    public void getList(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CoinList>() {
                    @Override
                    public CoinList apply(@NonNull String data) throws Exception {
                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data), rsa.PUBLIC_KEY));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();
                        CoinList commonBean = gson.fromJson(paramsStr, CoinList.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CoinList>(mView, true) {
                    @Override
                    public void onNext(CoinList commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getData() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }
}
