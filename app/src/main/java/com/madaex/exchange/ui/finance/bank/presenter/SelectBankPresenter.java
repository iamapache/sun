package com.madaex.exchange.ui.finance.bank.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.Base64Utils;
import com.madaex.exchange.common.util.FileEncryptionManager;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.finance.bank.contract.SelectBank;
import com.madaex.exchange.ui.finance.bank.contract.SelectBankContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目：  madaexchange
 * 类名：  SelectBankPresenter.java
 * 时间：  2018/9/28 11:30
 * 描述：  ${TODO}
 */

public class SelectBankPresenter extends RxPresenter<SelectBankContract.View> implements SelectBankContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public SelectBankPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, SelectBank>() {
                    @Override
                    public SelectBank apply(@NonNull String data) throws Exception {
                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();

                        CommonBaseBean commonBaseBean = gson.fromJson(paramsStr, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(paramsStr, CommonBean.class);
                            SelectBank user = new SelectBank();
                            user.setMsg(commonBean.getData());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            SelectBank commonBean = gson.fromJson(paramsStr, SelectBank.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<SelectBank>(mView) {
                    @Override
                    public void onNext(SelectBank commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError("");
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean.getData());
                        }
                    }
                }));
    }
}
