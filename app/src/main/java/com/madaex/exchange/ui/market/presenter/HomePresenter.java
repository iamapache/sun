package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.market.bean.HomeData;
import com.madaex.exchange.ui.market.bean.TitleBean;
import com.madaex.exchange.ui.market.contract.HomeContract;
import com.orhanobut.logger.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  HomePresenter.java
 * 时间：  2018/9/13 16:00
 * 描述：  ${TODO}
 */

public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;
    private static Disposable disposable;

    @Inject
    public HomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getTitleData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, TitleBean>() {
                    @Override
                    public TitleBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        TitleBean commonBean = gson.fromJson(data, TitleBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<TitleBean>(mView, true) {
                    @Override
                    public void onNext(TitleBean commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getData() + "");
                        } else {
                            mView.SuccessTitle(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void getData(Map body) {
        disposable = Observable.interval(0, 7, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                              @Override
                              public void accept(Long integer) throws Exception {
                      rxApi.getTestResult(body)
                                .map(new Function<String, HomeData>() {
                                    @Override
                                    public HomeData apply(@NonNull String data) throws Exception {
                                        Gson gson = new Gson();
                                        HomeData commonBean = gson.fromJson(data, HomeData.class);
                                        return commonBean;
                                    }
                                })
                                .compose(new DefaultTransformer2())
                                .subscribeWith(new CommonSubscriber<HomeData>(mView, true) {
                                    @Override
                                    public void onNext(HomeData commonBean) {
                                        if (mView != null) {
                                            if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
//                                                mView.requestError(commonBean.getData() + "");
                                            } else {

                                                mView.requestSuccess(commonBean.getData());
                                            }
                                        }
                                    }
                                });
//                                  RetrofitHelper.getKLineAPI().getKLineResult(body).compose(new DefaultTransformer2())
//                                          .subscribe(new DefaultObserver<String>() {
//                                              @Override
//                                              public void onNext(String jsonStr) {
//                                                  FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
//                                                  String paramsStr = null;
//                                                  try {
//                                                      paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(jsonStr)));
//                                                  } catch (Exception e) {
//                                                      e.printStackTrace();
//                                                  }
//                                                  Logger.i("<==>data:kLineData" + paramsStr);
//                                                  Gson gson = new Gson();
//                                                  HomeData commonBean = gson.fromJson(paramsStr, HomeData.class);
//                                                  if (mView != null) {
//                                                      if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
////                                                mView.requestError(commonBean.getData() + "");
//                                                      } else {
//
//                                                          mView.requestSuccess(commonBean.getData());
//                                                      }
//                                                  }
//
//                                              }
//
//                                              @Override
//                                              public void onError(Throwable e) {
//
//                                              }
//
//                                              @Override
//                                              public void onComplete() {
//
//                                              }
//                                          });
                              }
                          }
                ).subscribe();

    }

    @Override
    public void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            detachView();
            disposable.dispose();
            Logger.i("<==>data:====定时器取消======");
        }
    }

    @Override
    public void collection(Map body) {
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
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestSuccess(commonBean.getMessage() + "");
                        } else {
                            mView.requestError(commonBean.getMessage() + "");
                        }
                    }
                }));
    }

    @Override
    public void getMessageCount(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonDataBean commonBaseBean = gson.fromJson(data, CommonDataBean.class);
                        return commonBaseBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView) {
                    @Override
                    public void onNext(CommonDataBean commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.requestError(commonBean.getMessage()+"");
                        }else {
                            mView.requestMessageCountSuccess(commonBean.getData().getTotal()+"");
                        }
                    }
                }));
    }
}
