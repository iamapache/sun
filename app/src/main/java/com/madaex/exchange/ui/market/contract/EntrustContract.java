package com.madaex.exchange.ui.market.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.market.bean.EntrustDetail;
import com.madaex.exchange.ui.market.bean.EntrustList;

import java.util.Map;

/**
 * 项目：  madaexchange
 * 类名：  EntrustContract.java
 * 时间：  2018/9/5 15:33
 * 描述：  ${TODO}
 */

public class EntrustContract {
    public interface View extends BaseContract.BaseView {
        void requestSuccess(EntrustList bean);
        void nodata(String msg);
        void requestError(String msg);
        void deleteSuccess(String msg);

        void deleteError(String msg);
        void requestEntrustDetailSuccess(EntrustDetail bean);
    }

    public interface Presenter extends BaseContract.BasePresenter<EntrustContract.View> {
        void getData(Map body);
        void delete(Map body);

        void getDataDetail(Map body);
    }
}
