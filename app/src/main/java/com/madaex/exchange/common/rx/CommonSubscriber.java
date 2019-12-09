package com.madaex.exchange.common.rx;

import android.text.TextUtils;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.net.ResponeThrowable;
import com.madaex.exchange.common.progress.ProgressCallBack;
import com.madaex.exchange.common.progress.SubscriberCallBack;
import com.orhanobut.logger.Logger;


/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> extends DefaultSubscriber<T> {
    private BaseContract.BaseView mView;
    private String mErrorMsg = "";
    private boolean isShowErrorState = true;
    private boolean isShowDialog = false;
    private ProgressCallBack fileCallBack;
    SubscriberCallBack subscriberCallBack;


    protected CommonSubscriber(BaseContract.BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseContract.BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseContract.BaseView view, boolean isShowDialog) {
        this.mView = view;
        this.isShowDialog = isShowDialog;
    }

    protected CommonSubscriber(BaseContract.BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseContract.BaseView view, ProgressCallBack fileCallBack) {
        this.mView = view;
        this.fileCallBack = fileCallBack;
    }

    protected CommonSubscriber(BaseContract.BaseView view, SubscriberCallBack subscriberCallBack) {
        this.mView = view;
        this.subscriberCallBack = subscriberCallBack;
    }

    @Override
    public void onStart() {
        if (isShowDialog) {
            if (mView != null) {
                mView.showDialog();
            }
            if (subscriberCallBack != null) {
                subscriberCallBack.onStart();
            }
        }
    }

    @Override
    public void onComplete() {
        if (mView != null) {
            mView.dismissDialog();
        }
        if (subscriberCallBack != null) {
            subscriberCallBack.onCompleted();
        }
    }


    @Override
    public void onError(ResponeThrowable e) {

        if (mView == null) {
            return;
        }
        mView.dismissDialog();
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showMsg(mErrorMsg);
        }
        if (e instanceof ResponeThrowable) {
            Logger.i("ResponeThrowable<==>" + e.getCode() + e.getMessage());
            if (e.getCode() == Constant.RESPONSE_UN_LOGIN) {
                mView.onUnLogin();
            } else if (e.getCode() == Constant.RESPONSE_FREEZE) {
                mView.onFreeze();
            } else if (e.getCode() == Constant.RESPONSE_ERROR) {
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
