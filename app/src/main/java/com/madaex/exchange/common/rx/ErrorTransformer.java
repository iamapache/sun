package com.madaex.exchange.common.rx;


import com.madaex.exchange.common.base.BaseBean;
import com.madaex.exchange.common.net.ServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 项目：  loan
 * 包名：  com.app.loan.transformer
 * 类名：  ErrorTransformer.java
 * 作者：  彭决
 * 时间：  2017/9/27 10:59
 * 描述：  ${TODO}
 */

public class ErrorTransformer<T> implements ObservableTransformer<BaseBean<T>, T> {

    @Override
    public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
        // 对服务器端给出Json数据进行校验
        return upstream.map(new Function<BaseBean<T>, T>() {
            @Override
            public T apply(BaseBean<T> httpResponse) throws Exception {
                if (httpResponse.isResult() != true) {
                    //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                    throw new ServerException(httpResponse.getMessage()+"", httpResponse.getStatus());
                }
                //服务器请求数据成功，返回里面的数据实体
                return httpResponse.getData();
            }
            // 对请求服务器出现错误信息进行拦截
        })
//                .onErrorResumeNext(new Function<Throwable, Observable<? extends T>>() {
//            @Override
//            public Observable<? extends T> apply(Throwable throwable) throws Exception {
//                Logger.i("<==>onErrorResumeNext:" + throwable.getMessage());
//                throwable.printStackTrace();
//                return Observable.error(ExceptionHandle.handleException(throwable));
//            }
//        })
                ;
    }

//

    public static <T> ErrorTransformer<T> create() {
        return new ErrorTransformer<>();
    }

    private static ErrorTransformer instance = null;

    private ErrorTransformer() {
    }

    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T> ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer();
                }
            }
        }
        return instance;
    }


}
