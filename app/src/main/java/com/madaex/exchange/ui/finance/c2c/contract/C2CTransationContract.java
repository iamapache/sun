package com.madaex.exchange.ui.finance.c2c.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransation;
import com.madaex.exchange.ui.finance.c2c.bean.C2CTransationDetail;
import com.madaex.exchange.ui.finance.c2c.bean.TransationDetail;

import java.util.List;
import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  C2CTransationContract.java
 * 时间：  2018/8/30 12:27
 * 描述：  ${TODO}
 */

public class C2CTransationContract {

    public interface View extends BaseContract.BaseView {
        void requestSuccess(List<C2CTransation.DataBean> commonBean);
        void requestSuccess2(List<C2CTransation.DataBean> commonBean);
        void requestSuccess3(TransationDetail commonBean);
        void requestError(String msg);
        void nodata(String msg);
        void requestSuccess(C2CTransationDetail commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<C2CTransationContract.View> {
        void getData(Map map);
        void getData2(Map map);
        void getDetail(Map map);
        void getc2cDetail(Map map);
    }
}
