package com.madaex.exchange.common.util;


import com.madaex.exchange.common.base.BaseBean;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.net.ExceptionHandle;
import com.madaex.exchange.common.net.ServerException;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {



    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseBean<T>, T> handleMyResult() {   //compose判断结果
        return new FlowableTransformer<BaseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseBean<T> tMyHttpResponse) {
                        if(tMyHttpResponse.getStatus() == Constant.RESPONSE_SUCCESS) {
                            return createData(tMyHttpResponse.getData());
                        } else {
                            Logger.i("ResponeThrowable Flowable.error" );
                            return Flowable.error(new ServerException(tMyHttpResponse.getData()+"", tMyHttpResponse.getStatus()));
                        }
                    }
                }).onErrorResumeNext(new Function<Throwable, Publisher<? extends T>>() {
                    @Override
                    public Publisher<? extends T> apply(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Logger.i("ResponeThrowable ExceptionHandle.handleException" );
                        return Flowable.error(ExceptionHandle.handleException(throwable));
                    }
                });
            }
        };
    }


    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
