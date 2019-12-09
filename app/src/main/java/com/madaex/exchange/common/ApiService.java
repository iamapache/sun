package com.madaex.exchange.common;


import com.madaex.exchange.common.net.Constant;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 项目：  frame
 * 类名：  ApiService.java
 * 时间：  2018/6/28 11:56
 * 描述：  ${TODO}
 */
public interface ApiService {
    @Multipart
    @POST(Constant.APP_APIS)
    Observable<String> getTestResult(@Part("data") RequestBody body);

    @Multipart
    @POST(Constant.APP_APIS)
    Observable<String> getKLineResult(@Part("data") RequestBody body);



    @Multipart
    @POST(Constant.APP_APIS)
    Observable<String> saveUserHeadImage(@Part("data") RequestBody bodys, @Part MultipartBody.Part file);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);


}
