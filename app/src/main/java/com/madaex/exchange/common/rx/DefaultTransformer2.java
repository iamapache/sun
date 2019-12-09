package com.madaex.exchange.common.rx;


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

public class DefaultTransformer2  implements ObservableTransformer {

    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
