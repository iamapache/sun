package com.madaex.exchange.common.rx;

import android.text.TextUtils;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.net.ResponeThrowable;
import com.madaex.exchange.common.progress.ProgressCallBack;
import com.orhanobut.logger.Logger;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by codeest on 2017/2/23.
 */

public abstract class Common2Subscriber<T> extends ResourceSubscriber<T> {
    private BaseContract.BaseView mView;
    private String mErrorMsg = "";
    private boolean isShowErrorState = true;
    private boolean isShowDialog = false;
    private ProgressCallBack fileCallBack;


    protected Common2Subscriber(BaseContract.BaseView view) {
        this.mView = view;
    }

    protected Common2Subscriber(BaseContract.BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected Common2Subscriber(BaseContract.BaseView view, boolean isShowDialog) {
        this.mView = view;
        this.isShowDialog = isShowDialog;
    }

    protected Common2Subscriber(BaseContract.BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    protected Common2Subscriber(BaseContract.BaseView view, ProgressCallBack fileCallBack) {
        this.mView = view;
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onStart() {
        if (isShowDialog) {
            if (mView != null) {
                mView.showDialog();
            }
        }
    }

    @Override
    public void onComplete() {
        if (mView != null) {
            mView.dismissDialog();
        }
    }


    @Override
    public void onError(Throwable e) {

        if (mView == null) {
            return;
        }
        mView.dismissDialog();
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showMsg(mErrorMsg);
        }
        if (e instanceof ResponeThrowable) {
            ResponeThrowable throwable =(ResponeThrowable) e;
            Logger.i("ResponeThrowable" + throwable.getCode() + e.getMessage());
            if (throwable.getCode() == Constant.RESPONSE_UN_LOGIN) {
                mView.onUnLogin();
            } else if (throwable.getCode() == Constant.RESPONSE_FREEZE) {
                mView.onFreeze();
            } else if (throwable.getCode() == Constant.RESPONSE_ERROR) {
                mView.showMsg(e.getMessage());
            } else {
                mView.showMsg(e.getMessage());
            }
        } else {
            mView.showMsg("未知错误!!");
        }
        if (isShowErrorState) {
            mView.stateError(0, e.getMessage());
        }
    }

}
