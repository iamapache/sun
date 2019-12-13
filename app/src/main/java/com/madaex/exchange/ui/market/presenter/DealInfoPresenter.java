package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.buy.bean.CoinList;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.market.bean.LineDetail;
import com.madaex.exchange.ui.market.contract.DealInfoContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  madaexchange
 * 类名：  DealInfoPresenter.java
 * 时间：  2018/9/6 16:20
 * 描述：  ${TODO}
 */

public class DealInfoPresenter extends RxPresenter<DealInfoContract.View> implements DealInfoContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public DealInfoPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CoinList>() {
                    @Override
                    public CoinList apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();

                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            CoinList user = new CoinList();
                            user.setMsg(commonBean.getData());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            CoinList commonBean = gson.fromJson(data, CoinList.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CoinList>(mView, true) {
                    @Override
                    public void onNext(CoinList commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                        }else {
                            mView.sendMsgSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void getJavaLineDetail(Map map) {
//        addSubscribe((Disposable) RetrofitHelper.getLiveAPI().getDetail(map)
//                .compose(new DefaultTransformer<List<Home>>())
//                .subscribeWith(new CommonSubscriber<List<Home>>(mView, true) {
//                    @Override
//                    public void onNext(List<Home> baseBean) {
//                        mView.requestDetailSuccess(baseBean);
//                    }
//                }));

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
                            mView.requestError(commonBean.getData() + "");
                        } else {

                            mView.requestSuccess(commonBean.getData() + "");
                        }
                    }
                }));
    }


}
