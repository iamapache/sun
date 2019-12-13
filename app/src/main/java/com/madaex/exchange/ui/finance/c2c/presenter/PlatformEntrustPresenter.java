package com.madaex.exchange.ui.finance.c2c.presenter;

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
import com.madaex.exchange.ui.finance.c2c.bean.PlatformEntrust;
import com.madaex.exchange.ui.finance.c2c.contract.PlatformEntrustContract;
import com.madaex.exchange.ui.mine.bean.User;
import com.orhanobut.logger.Logger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目：  sun
 * 类名：  PlatformEntrustPresenter.java
 * 时间：  2019/5/9 14:57
 * 描述：  ${TODO}
 */
public class PlatformEntrustPresenter extends RxPresenter<PlatformEntrustContract.View> implements PlatformEntrustContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public PlatformEntrustPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, PlatformEntrust>() {
                    @Override
                    public PlatformEntrust apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        PlatformEntrust commonBean = gson.fromJson(data, PlatformEntrust.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<PlatformEntrust>(mView, true) {
                    @Override
                    public void onNext(PlatformEntrust commonBean) {
                        if(commonBean.getStatus()== Constant.RESPONSE_ERROR||commonBean.getStatus() == -1){
                            mView.requestError("");
                        }else {
                            mView.sendMsgSuccess(commonBean.getData());
                        }
                    }
                }));
    }

    @Override
    public void load(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, User>() {
                    @Override
                    public User apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBaseBean commonBaseBean = gson.fromJson(data, CommonBaseBean.class);
                        if (commonBaseBean.getStatus() == 0||commonBaseBean.getStatus() == -1) {
                            CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                            User user = new User();
                            user.setMsg(commonBean.getData());
                            user.setStatus(commonBean.getStatus());
                            return user;
                        } else {
                            User commonBean = gson.fromJson(data, User.class);
                            return commonBean;
                        }
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<User>(mView) {
                    @Override
                    public void onNext(User commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }
}
