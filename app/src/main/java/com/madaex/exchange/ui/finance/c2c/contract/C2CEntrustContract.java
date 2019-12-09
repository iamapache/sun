package com.madaex.exchange.ui.finance.c2c.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.c2c.bean.EntractDetail;

/**
 * 项目：  madaexchange
 * 类名：  EntrustContract.java
 * 时间：  2018/9/5 15:33
 * 描述：  ${TODO}
 */

public class C2CEntrustContract {
    public interface View extends BaseContract.BaseView {
        void nodata(String msg);
        void requestError(String msg);
        void deleteSuccess(String msg);

        void deleteError(String msg);
        void requestEntrustDetailSuccess(EntractDetail bean);
    }

    public interface Presenter extends BaseContract.BasePresenter<C2CEntrustContract.View> {
        void getData(String str);
        void delete(String str);

        void getDataDetail(String str);
    }
}
