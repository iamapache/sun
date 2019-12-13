package com.madaex.exchange.ui;

import android.content.Context;

import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.rx.RxPresenter;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目：  frame
 * 类名：  MainPresenter.java
 * 时间：  2018/6/28 15:16
 * 描述：  ${TODO}
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public MainPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map map) {
//        addSubscribe((Disposable) rxApi.getTestResult(map)
//                .compose(new DefaultTransformer<TestBean>())
//                .subscribeWith(new CommonSubscriber<TestBean>(mView, true) {
//                    @Override
//                    public void onNext(TestBean baseBean) {
//                        mView.showMsg(baseBean.getApikey()+"");
//                    }
//                }));

        addSubscribe( rxApi.getTestResult(map)
                .subscribeOn(Schedulers.io())        // 在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理获取食品列表的请求结果
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String foodList) throws Exception {
                        // 先根据获取食品列表的响应结果做一些操作
                        mView.showMsg(foodList+"");
                    }
                }));



    }


}
