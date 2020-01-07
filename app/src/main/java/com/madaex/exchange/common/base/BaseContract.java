package com.madaex.exchange.common.base;

/**
 * 项目：  frame
 * 类名：  BaseContract.java
 * 时间：  2018/6/28 11:05
 * 描述：  ${TODO}
 */
public interface BaseContract {
    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView extends View {
        void onError(String message);

        void showMsg(String msg);

        void stateError(int resid, String errormsg);

        void stateEmpty();

        void stateLoading();

        void stateMain();


    }
}
