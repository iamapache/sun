package com.madaex.exchange.common.rx;

import com.madaex.exchange.common.net.ExceptionHandle;
import com.madaex.exchange.common.net.ResponeThrowable;
import com.orhanobut.logger.Logger;

import io.reactivex.observers.DisposableObserver;


/**
 * 项目：  YiZhiBang
 * 包名：  com.henji.yunyi.yizhibang.common.base.rx
 * 类名：  MyRxSubscriber.java
 * 作者：  彭决
 * 时间：  2018/1/10 14:46
 * 描述：  ${TODO}
 */

public abstract class DefaultSubscriber<T > extends DisposableObserver<T> {

    public abstract void onError(ResponeThrowable e);

    @Override
    public void onError(Throwable e) {
        Logger.i("onError<==>" + e.getMessage());
        if(e instanceof Exception){
            //访问获得对应的Exception
            onError(ExceptionHandle.handleException(e));
//            onError((ResponeThrowable) e);
        }else {
            //将Throwable 和 未知错误的status code返回
            onError(new ResponeThrowable(e,0));
            Logger.i("ResponeThrowable<==>错误信息:" + e.getMessage());
        }
    }
}
