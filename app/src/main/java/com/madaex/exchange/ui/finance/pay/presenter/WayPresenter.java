package com.madaex.exchange.ui.finance.pay.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.net.Constant;
import com.madaex.exchange.common.rx.CommonSubscriber;
import com.madaex.exchange.common.rx.DefaultTransformer2;
import com.madaex.exchange.common.rx.RxPresenter;
import com.madaex.exchange.ui.common.CommonBean;
import com.madaex.exchange.ui.common.CommonDataBean;
import com.madaex.exchange.ui.finance.pay.bean.ImgCheck;
import com.madaex.exchange.ui.finance.pay.bean.Payway;
import com.madaex.exchange.ui.finance.pay.bean.UploadIdcard;
import com.madaex.exchange.ui.finance.pay.contract.WayContract;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目：  sun
 * 类名：  WayPresenter.java
 * 时间：  2019/5/13 18:36
 * 描述：  ${TODO}
 */
public class WayPresenter extends RxPresenter<WayContract.View> implements WayContract.Presenter {
    private Context mContext;
    @Inject
    ApiService rxApi;

    @Inject
    public WayPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getData(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult2(body)
                .map(new Function<String, CommonDataBean>() {
                    @Override
                    public CommonDataBean apply(@NonNull String data) throws Exception {
//                        String paramsStr = new String(mFileEncryptionManager.decryptByPublicKey(Base64Utils.decode(data)));
//                        String paramsStr = new String(rsa.decryptPublicKey(Base64Utils.decode(data),rsa.PUBLIC_KEY));
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        CommonDataBean CommonDataBean = gson.fromJson(data, CommonDataBean.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonDataBean>(mView, true) {
                    @Override
                    public void onNext(CommonDataBean CommonDataBean) {
                        if (CommonDataBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(CommonDataBean.getMessage() + "");
                        } else if (CommonDataBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
//                            mView.nodata(CommonDataBean.getMessage()+"");
                        } else if (CommonDataBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.requestSuccess(CommonDataBean.getMessage() + "");
                        }
                    }
                }));
    }

    @Override
    public void submit(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, Payway>() {
                    @Override
                    public Payway apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        Payway commonBean = gson.fromJson(data, Payway.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<Payway>(mView, true) {
                    @Override
                    public void onNext(Payway commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getData() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.requestSuccess(commonBean);
                        }
                    }
                }));
    }

    @Override
    public void delete(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView, true) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMessage() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.requestSuccess(commonBean.getMessage() + "");
                        }
                    }
                }));
    }

    @Override
    public void getPayWay(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView, true) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMessage() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.requestPayWaySuccess(commonBean.getMessage() + "");
                        }
                    }
                }));
    }

    @Override
    public void edit(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult(body)
                .map(new Function<String, CommonBean>() {
                    @Override
                    public CommonBean apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        CommonBean commonBean = gson.fromJson(data, CommonBean.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<CommonBean>(mView, true) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMessage() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.requestSuccess(commonBean.getMessage() + "");
                        }
                    }
                }));
    }

    @Override
    public void saveUserHeadImage(Map body, String pathList) {
//        MultipartBody multipartBody = ImageUploadUtil.filesToMultipartBody(pathList);
        File file = new File(pathList);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
// MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        addSubscribe((Disposable) rxApi.saveUserHeadImage(body, multipartBody)
                .map(new Function<String, UploadIdcard>() {
                    @Override
                    public UploadIdcard apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        UploadIdcard commonBean = gson.fromJson(data, UploadIdcard.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<UploadIdcard>(mView, true) {
                    @Override
                    public void onNext(UploadIdcard commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMessage() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.uploadIdcardSuccess(commonBean);
                        }
                    }
                }));
    }
    @Override
    public void saveUserHeadImage2(Map body, String pathList) {
//        MultipartBody multipartBody = ImageUploadUtil.filesToMultipartBody(pathList);
        File file = new File(pathList);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
// MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        addSubscribe((Disposable) rxApi.saveUserHeadImage(body, multipartBody)
                .map(new Function<String, UploadIdcard>() {
                    @Override
                    public UploadIdcard apply(@NonNull String data) throws Exception {
                        Gson gson = new Gson();
                        UploadIdcard commonBean = gson.fromJson(data, UploadIdcard.class);
                        return commonBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<UploadIdcard>(mView, true) {
                    @Override
                    public void onNext(UploadIdcard commonBean) {
                        if (commonBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestError(commonBean.getMessage() + "");
                        } else if (commonBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
                        } else if (commonBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.uploadIdcardSuccess2(commonBean);
                        }
                    }
                }));
    }
    @Override
    public void uploadIdcard(Map body) {
        addSubscribe((Disposable) rxApi.getTestResult2(body)
                .map(new Function<String, ImgCheck>() {
                    @Override
                    public ImgCheck apply(@NonNull String data) throws Exception {
                        Logger.i("<====>paramsStr:" + data);
                        Gson gson = new Gson();
                        ImgCheck CommonDataBean = gson.fromJson(data, ImgCheck.class);
                        return CommonDataBean;
                    }
                })
                .compose(new DefaultTransformer2())
                .subscribeWith(new CommonSubscriber<ImgCheck>(mView, true) {
                    @Override
                    public void onNext(ImgCheck CommonDataBean) {
                        if (CommonDataBean.getStatus() == Constant.RESPONSE_ERROR) {
                            mView.requestidcardImgCheckError(CommonDataBean.getMessage() + "");
                        } else if (CommonDataBean.getStatus() == Constant.RESPONSE_EXCEPTION) {
//                            mView.requestidcardImgCheckError(CommonDataBean.getMessage() + "");
                        } else if (CommonDataBean.getStatus() == Constant.RESPONSE_SUCCESS) {
                            mView.idcardImgCheck(CommonDataBean);
                        }
                    }
                }));
    }

    private RequestBody convertToRequestBody(String param){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }
}
