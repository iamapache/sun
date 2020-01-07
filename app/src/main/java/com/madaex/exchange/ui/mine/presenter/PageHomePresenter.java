package com.madaex.exchange.ui.mine.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.market.bean.HomeData;
import com.madaex.exchange.ui.mine.bean.BannerData;
import com.madaex.exchange.ui.mine.bean.HotCoin;
import com.madaex.exchange.ui.mine.bean.NoticeData;
import com.madaex.exchange.ui.mine.contract.PageHomeContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 项目：  sun
 * 类名：  HomePresenter.java
 * 时间：  2019/12/12 17:26
 * 描述：  ${TODO}
 */
public class PageHomePresenter extends RxPresenter<PageHomeContract.View> implements PageHomeContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public PageHomePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getbanner(body)
                .map(new Function<String, BannerData>() {
                    @Override
                    public BannerData apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        BannerData commonBaseBean = gson.fromJson(data, BannerData.class);
                        return commonBaseBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<BannerData>(mView) {
                    @Override
                    public void onNext(BannerData commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {

                            mView.nodata(commonBean.getMessage()+"");
                        }else  if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS){
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void update(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, HotCoin>() {
                    @Override
                    public HotCoin apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        HotCoin commonBaseBean = gson.fromJson(data, HotCoin.class);
                        return commonBaseBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<HotCoin>(mView) {
                    @Override
                    public void onNext(HotCoin commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.nodata(commonBean.getMessage()+"");
                        }else {
                            mView.requestHotcoin(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void load(Map body) {
        addSubscribe((Disposable) rxApi.getnotice(body)
                .map(new Function<String, NoticeData>() {
                    @Override
                    public NoticeData apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        NoticeData commonBaseBean = gson.fromJson(data, NoticeData.class);
                        return commonBaseBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<NoticeData>(mView) {
                    @Override
                    public void onNext(NoticeData commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.nodata(commonBean.getMessage()+"");
                        }else  if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS){
                            mView.requestupdate(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void getMartketList(Map msg) {
        addSubscribe((Disposable) rxApi.getTestResult(msg)
                .map(new Function<String, HomeData>() {
                    @Override
                    public HomeData apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        HomeData commonBaseBean = gson.fromJson(data, HomeData.class);
                        return commonBaseBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<HomeData>(mView) {
                    @Override
                    public void onNext(HomeData commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
//                            mView.nodata(commonBean.getMessage()+"");
                        }else {
                            mView.requestHotcoin(commonBean);
                        }
                    }
                }));
    }
}
