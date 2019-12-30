package com.madaex.exchange.ui.mine.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.progress.ProgressCallBack;
import com.madaex.exchange.common.progress.StorageUtil;
import com.madaex.exchange.common.progress.UpgradeUtil;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.common.util.ToastUtils;
import com.madaex.exchange.ui.common.CommonBaseBean;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.mine.bean.User;
import com.madaex.exchange.ui.mine.bean.update;
import com.madaex.exchange.ui.mine.contract.MineContract;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 项目：  madaexchange
 * 类名：  MinePresenter.java
 * 时间：  2018/8/29 15:06
 * 描述：  ${TODO}
 */

public class MinePresenter extends RxPresenter<MineContract.View> implements MineContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public MinePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
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
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void getMessageCount(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonDataBean commonBaseBean = gson.fromJson(data, CommonDataBean.class);
                            return commonBaseBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView) {
                    @Override
                    public void onNext(CommonDataBean commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getMessage()+"");
                        }else {
                            mView.requestMessageCountSuccess(commonBean.getMessage());
                        }
                    }
                }));
    }

    @Override
    public void update(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, update>() {
                    @Override
                    public update apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                            update commonBean = gson.fromJson(data, update.class);
                            return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<update>(mView) {
                    @Override
                    public void onNext(update commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                        } else  if(commonBean.getStatus()== Constant.RESPONSE_EXCEPTION){
                            mView.nodata(commonBean.getData()+"");
                        }else {
                            mView.requestupdate(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void load(String msg) {
        final String version = "1";
        UpgradeUtil.deleteOldApk();
        if (UpgradeUtil.isApkFileDownloaded(version)) {
            return;
        }
        final ProgressCallBack progressCallBack =  new ProgressCallBack() {
            @Override
            public void progress(long progress, long total, int percent) {
                Log.i("==", "" + percent);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }
        };
        addSubscribe(rxApi.download(msg)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) {
                        return StorageUtil.saveApk(progressCallBack,responseBody.byteStream(), version);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new CommonSubscriber<File>(mView,progressCallBack ) {
                    @Override
                    public void onNext(File file) {
                        if (file != null) {
                            UpgradeUtil.installApk(file);
                        }
                        ToastUtils.showToast(mContext.getString(R.string.loadsuccess));
                    }
                }));
    }


}
