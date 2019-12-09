package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.DataUtil;
import com.madaex.exchange.ui.market.contract.KLineContract;
import com.orhanobut.logger.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目：  madaexchange
 * 类名：  KLinePresenter.java
 * 时间：  2018/9/14 14:05
 * 描述：  ${TODO}
 */

public class KLinePresenter extends RxPresenter<KLineContract.View> implements KLineContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;
    private static Disposable mDisposable;
    @Inject
    public KLinePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(final Map map) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), DataUtil.sign(map));
        Observable.interval(1, 60, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
                 /*
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        addSubscribe((Disposable) rxApi.getKLineResult(body)
                                .compose(new DefaultTransformer2())
                                .subscribeWith(new CommonSubscriber<String>(mView, true) {
                                    @Override
                                    public void onNext(String commonBean) {
                                        if(mView!=null){
                                            mView.requestSuccess(commonBean);
                                        }
                                    }
                                }));
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable=d;
            }
            @Override
            public void onNext(Long value) {
                Logger.i("<==>dataonNext:对Complete事件作出响应");
            }

            @Override
            public void onError(Throwable e) {  cancel();
            }

            @Override
            public void onComplete() {  cancel();
                Logger.i("<==>data:对Complete事件作出响应");
            }
        });

//        addSubscribe((Disposable) rxApi.getKLineResult(map)
//                .compose(new DefaultTransformer2())
//                .subscribeWith(new CommonSubscriber<String>(mView, true) {
//                    @Override
//                    public void onNext(String commonBean) {
//                        mView.requestSuccess(commonBean);
//                    }
//                }));

    }
    @Override
    public  void cancel(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
            Logger.i("<==>data:====定时器取消======");
        }
    }

}
