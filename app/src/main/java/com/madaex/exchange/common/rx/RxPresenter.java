package com.madaex.exchange.common.rx;

import android.content.Context;

import com.madaex.exchange.common.base.BaseContract;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by JUGG on 2016/12/10.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    private Context mContext;

    @Inject
    public RxPresenter(Context mContext) {
        this.mContext = mContext;
    }

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    public RxPresenter() {
    }

    protected void unDisposable() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unDisposable();
    }

    public T getView() {
        return mView;
    }


}
