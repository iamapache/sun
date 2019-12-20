package com.madaex.exchange.ui.common;

import com.madaex.exchange.common.base.BaseContract;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  CommonContract.java
 * 时间：  2018/8/29 10:00
 * 描述：  ${TODO}
 */

public class CommonContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(String msg);
        void requestSuccess2(CommonDataBean.DataBean data);
        void requestError(String msg);
        void nodata(String msg);
        void sendMsgSuccess(String msg);
    }

    public interface Presenter extends BaseContract.BasePresenter<CommonContract.View> {
        void getData(Map str);
        void getData2(Map str);
        void getMsgCode(Map str);
    }
}
