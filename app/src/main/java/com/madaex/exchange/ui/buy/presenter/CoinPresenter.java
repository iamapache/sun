package com.madaex.exchange.ui.buy.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.buy.bean.DealInfo;
import com.madaex.exchange.ui.buy.contract.CoinContract;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.common.SB2Data;
import com.madaex.exchange.ui.market.bean.EntrustList;
import com.madaex.exchange.ui.market.bean.LineDetail;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

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
    public void getData(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, CoinList>() {
                    @Override
                    public CoinList apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:" + data);
                        Gson gson = new Gson();
                        CoinList commonBean = gson.fromJson(data, CoinList.class);
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
    public void deal(Map map) {
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
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.sendDealSuccess(commonBean.getMessage());
                        }
                    }
                }));
    }


    @Override
    public void getJavaLineDetail(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, LineDetail>() {
                    @Override
                    public LineDetail apply(@NonNull String data) throws Exception {
                        Logger.i("<==>data:getJavaLineDetail" + data);
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
    public void getToken(Map map) {
        addSubscribe((Disposable) rxApi.getTestResult(map)
                .map(new Function<String, SB2Data>() {
                    @Override
                    public SB2Data apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        SB2Data commonBean = gson.fromJson(data, SB2Data.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<SB2Data>(mView, true) {
                    @Override
                    public void onNext(SB2Data commonBean) {
                        if (mView != null) {
                            if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                                mView.requestError(commonBean.getData() + "");
                            } else {
                                mView.requestToken(commonBean.getData());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getDataenn(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, EntrustList>() {
                    @Override
                    public EntrustList apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();


                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            EntrustList user = new EntrustList();
                            user.setMsg(commonBean.getMessage());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            EntrustList commonBean = gson.fromJson(data, EntrustList.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<EntrustList>(mView) {
                    @Override
                    public void onNext(EntrustList commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMsg());
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void deleteenn(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
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
                            mView.deleteError(commonBean.getMessage()+"");
                        }else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.deleteSuccess(commonBean.getMessage()+"");
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
                        Logger.i("<==>data:" + data);
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
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                        }else {
                            mView.sendMsgSuccess(commonBean);
                        }
                    }
                }));
    }
}
