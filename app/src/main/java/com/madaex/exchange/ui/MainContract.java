package com.madaex.exchange.ui;

import com.madaex.exchange.common.base.BaseContract;

/**
 * 项目：  frame
 * 类名：  MainContract.java
 * 时间：  2018/6/28 15:16
 * 描述：  ${TODO}
 */
public  class MainContract {
    public interface View extends BaseContract.BaseView {
    }

    public interface Presenter extends BaseContract.BasePresenter<View> {
        void getData(String map);
    }

    public interface Model {
        void getData();
    }
}
