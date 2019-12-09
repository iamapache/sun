package com.madaex.exchange.ui.finance.transfer.contract;

import com.madaex.exchange.common.base.BaseContract;
import com.madaex.exchange.ui.finance.pay.bean.PlatRecord;

import java.util.ArrayList;

/**
 * 项目：  sunn
 * 类名：  NcContract.java
 * 时间：  2019/5/23 10:26
 * 描述：  ${TODO}
 */
public class NcContract {
    public interface View extends BaseContract.BaseView {

        void requestError(String msg);

        void requestSuccess(String commonBean);
        void requestSuccess(PlatRecord commonBean);
        void requestSuccess(Ncrecord commonBean);

        void requestSuccess(CoinList commonBean);
    }

    public interface Presenter extends BaseContract.BasePresenter<NcContract.View> {
        void getData(String str);
        void getData2(String str);
        void submit(String str);
        void saveUserHeadImage(String str, ArrayList<String> pathList);
        void getList(String str);
    }
}
