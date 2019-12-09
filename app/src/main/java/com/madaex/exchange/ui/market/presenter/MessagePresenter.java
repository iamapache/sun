package com.madaex.exchange.ui.market.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.Base64Utils;
import com.madaex.exchange.common.util.FileEncryptionManager;
import com.madaex.exchange.ui.market.bean.Message;
import com.madaex.exchange.ui.market.contract.MessageContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目：  madaexchange
 * 类名：  MessagePresenter.java
 * 时间：  2018/8/31 11:40
 * 描述：  ${TODO}
 */

public class MessagePresenter extends RxPresenter<MessageContract.View> implements MessageContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public MessagePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), str);
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, Message>() {
                    @Override
                    public Message apply(@NonNull String data) throws Exception {
                        FileEncryptionManager mFileEncryptionManager = FileEncryptionManager.getInstance();
                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
                        Logger.i("<==>data:" + paramsStr);
                        Gson gson = new Gson();
                        Message commonBean = gson.fromJson(paramsStr, Message.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Message>(mView) {
                    @Override
                    public void onNext(Message commonBean) {
//                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
//                            mView.requestError("");
//                        } else {
//                            mView.requestSuccess(commonBean.getData());
//                        }
                    }
                }));
    }
}
