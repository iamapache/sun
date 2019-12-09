package com.madaex.exchange.common.progress;

/**
 * 项目：  frame
 * 类名：  ProgressCallBack.java
 * 时间：  2018/7/10 18:40
 * 描述：  ${TODO}
 */
public abstract class SubscriberCallBack<T> {

    public abstract void onNext(T t);
    public abstract void onStart();

    public abstract void onCompleted();

}
