package com.madaex.exchange.ui.finance.contracts.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.common.DataBean;
import com.madaex.exchange.ui.finance.contracts.bean.AlscInfo;
import com.madaex.exchange.ui.finance.contracts.bean.Bills;
import com.madaex.exchange.ui.finance.contracts.bean.ContractAsset;
import com.madaex.exchange.ui.finance.contracts.bean.USDTinfo;
import com.madaex.exchange.ui.finance.contracts.bean.WalletInfo;
import com.madaex.exchange.ui.finance.contracts.contract.ContractContract;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  AssetPresenter.java
 * 时间：  2018/8/30 15:29
 * 描述：  ${TODO}
 */

public class ContractPresenter extends RxPresenter<ContractContract.View> implements ContractContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public ContractPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, ContractAsset>() {
                    @Override
                    public ContractAsset apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        ContractAsset commonBean = gson.fromJson(data, ContractAsset.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<ContractAsset>(mView, true) {
                    @Override
                    public void onNext(ContractAsset commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else if (commonBean.getStatus() == -1) {

                            mView.nodata("");
                        } else {
                            if (mView != null) {
                                mView.requestSuccess(commonBean);
                            }

                        }
                    }
                }));
    }

    @Override
    public void bills(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, Bills>() {
                    @Override
                    public Bills apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        Bills commonBean = gson.fromJson(data, Bills.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Bills>(mView, true) {
                    @Override
                    public void onNext(Bills commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else if (commonBean.getStatus() == -1) {

                            mView.nodata("");
                        } else {
                            if (mView != null) {
                                mView.requestSuccess(commonBean);
                            }

                        }
                    }
                }));
    }


    @Override
    public void getAlscInfo(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, AlscInfo>() {
                    @Override
                    public AlscInfo apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        AlscInfo commonBean = gson.fromJson(data, AlscInfo.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<AlscInfo>(mView, true) {
                    @Override
                    public void onNext(AlscInfo commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        } else {
                            if (mView != null) {
                                mView.requestSuccess(commonBean);
                            }

                        }
                    }
                }));
    }

    @Override
    public void open_contract(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, DataBean>() {
                    @Override
                    public DataBean apply(@NonNull String data) throws Exception {
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        DataBean CommonDataBean = gson.fromJson(data, DataBean.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<DataBean>(mView, true) {
                    @Override
                    public void onNext(DataBean CommonDataBean) {
                        if(CommonDataBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestErrorcontract(CommonDataBean.getMessage()+"");
                        }else  if(CommonDataBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.requestSuccess(CommonDataBean.getMessage());
                        }
                    }
                }));
    }

    @Override
    public void hua(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
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
                            mView.requestErrorcontract(CommonDataBean.getMessage()+"");
                        }else  if(CommonDataBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.requestSuccess(CommonDataBean.getMessage());
                        }
                    }
                }));
    }

    @Override
    public void wallet_info(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, WalletInfo>() {
                    @Override
                    public WalletInfo apply(@NonNull String data) throws Exception {
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        WalletInfo CommonDataBean = gson.fromJson(data, WalletInfo.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<WalletInfo>(mView, true) {
                    @Override
                    public void onNext(WalletInfo CommonDataBean) {
                        if(CommonDataBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestErrorcontract(CommonDataBean.getMessage()+"");
                        }else  if(CommonDataBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.requestSuccess(CommonDataBean);
                        }
                    }
                }));
    }

    @Override
    public void gethuaRecord(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, Bills>() {
                    @Override
                    public Bills apply(@NonNull String data) throws Exception {
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        Bills CommonDataBean = gson.fromJson(data, Bills.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Bills>(mView, true) {
                    @Override
                    public void onNext(Bills CommonDataBean) {
                        if(CommonDataBean.getStatus()== Constant.RESPONSE_ERROR){
                            mView.requestErrorcontract(CommonDataBean.getMessage()+"");
                        }else  if(CommonDataBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            mView.requestSuccess(CommonDataBean);
                        }
                    }
                }));
    }

    @Override
    public void getUSDTinfo(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, USDTinfo>() {
                    @Override
                    public USDTinfo apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        USDTinfo commonBean = gson.fromJson(data, USDTinfo.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<USDTinfo>(mView, true) {
                    @Override
                    public void onNext(USDTinfo commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.onUnLogin();
                        }else {
                            if (mView != null) {
                                mView.requestSuccess(commonBean);
                            }

                        }
                    }
                }));
    }
}
