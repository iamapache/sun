package com.madaex.exchange.common.rx;


import com.madaex.exchange.common.base.BaseBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 项目：  loan
 * 包名：  com.app.loan.transformer
 * 类名：  DefaultTransformer.java
 * 作者：  彭决
 * 时间：  2017/9/27 11:14
 * 描述：  ${TODO}
 */

public class DefaultTransformer<T>  implements ObservableTransformer<BaseBean<T>,T> {

    @Override
    public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(ErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
