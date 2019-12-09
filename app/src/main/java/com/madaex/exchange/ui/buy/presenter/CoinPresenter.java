package com.madaex.exchange.ui.buy.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.Base64Utils;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.common.util.FileEncryptionManager;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.market.bean.LineDetail;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目：  madaexchange
 * 类名：  CoinPresenter.java
 * 时间：  2018/9/5 12:07
 * 描述：  ${TODO}
 */

public class CoinPresenter extends RxPresenter<CoinContract.View> implements CoinContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public CoinPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CoinList>() {
                    @Override
                    public CoinList apply(@NonNull String data) throws Exception {
                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
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
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.sendMsgSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void deal(String str) {
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
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError(commonBean.getData()+"");
                        }else {
                            mView.sendDealSuccess(commonBean.getData());
                        }
                    }
                }));
    }


    @Override
    public void getJavaLineDetail(Map map) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), DataUtil.sign(map));
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, LineDetail>() {
                    @Override
                    public LineDetail apply(@NonNull String data) throws Exception {
                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
                        Logger.i("<==>data:getJavaLineDetail" + paramsStr);
                        Gson gson = new Gson();
                        LineDetail commonBean = gson.fromJson(paramsStr, LineDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<LineDetail>(mView, true) {
                    @Override
                    public void onNext(LineDetail commonBean) {
                        if (mView != null) {
                            if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                                mView.requestError(commonBean.getData() + "");
                            } else {
                                mView.requestDetailSuccess(commonBean.getData());
                            }
                        }
                    }
                }));
    }


    @Override
    public void getMsgInfo(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, DealInfo>() {
                    @Override
                    public DealInfo apply(@NonNull String data) throws Exception {
                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();

                        CommonBaseBean commonBaseBean = gson.fromJson(paramsStr, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(paramsStr, CommonBean.class);
                            DealInfo user = new DealInfo();
                            user.setMsg(commonBean.getData());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            DealInfo commonBean = gson.fromJson(paramsStr, DealInfo.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<DealInfo>(mView, true) {
                    @Override
                    public void onNext(DealInfo commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                        }else {
                            mView.sendMsgSuccess(commonBean);
                        }
                    }
                }));
    }
}
