package com.madaex.exchange.ui.buy.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.contract.DealContract;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.market.bean.LineDetail;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  DealPresenter.java
 * 时间：  2018/9/5 9:46
 * 描述：  ${TODO}
 */

public class DealPresenter extends RxPresenter<DealContract.View> implements DealContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public DealPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
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
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else if(commonBean.getStatus()== Constant.RESPONSE_SUCCESS){
                            mView.requestSuccess(commonBean.getMessage()+"");
                        }
                    }
                }));
    }

    @Override
    public void getMsgInfo(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, DealInfo>() {
                    @Override
                    public DealInfo apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();

                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            DealInfo user = new DealInfo();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            DealInfo commonBean = gson.fromJson(data, DealInfo.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<DealInfo>(mView, true) {
                    @Override
                    public void onNext(DealInfo commonBean) {
                        Logger.i("<==>2222222222222");
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestError( "");
                        }else if(commonBean.getStatus()== Constant.RESPONSE_SUCCESS){
                            mView.sendMsgSuccess(commonBean);
                        }
                    }
                }));
    }
    @Override
    public void getJavaLineDetail(Map map) {
//        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), DataUtil.sign(map));
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, LineDetail>() {
                    @Override
                    public LineDetail apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        LineDetail commonBean = gson.fromJson(data, LineDetail.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<LineDetail>(mView, true) {
                    @Override
                    public void onNext(LineDetail commonBean) {
                        if (mView != null) {
                            Logger.i("<==>111111111111111");
                            if(commonBean.getStatus()== Constant.RESPONSE_SUCCESS){
                                mView.requestDetailSuccess(commonBean.getData());
                            } else  {
                                mView.requestError(commonBean.getData() + "");
                            }
                        }
                    }
                }));
    }

    @Override
    public void getToken(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView, true) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (mView != null) {
                            if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                                mView.requestError(commonBean.getData() + "");
                            }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                                mView.onUnLogin();
                            }else if(commonBean.getStatus()== Constant.RESPONSE_SUCCESS){
                                mView.requestToken(commonBean.getMessage());
                            }
                        }
                    }
                }));
    }
}
