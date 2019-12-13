package com.madaex.exchange.common;


import com.madaex.exchange.common.net.Constant;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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
    @POST(Constant.APP_APIS)
    @FormUrlEncoded
    Observable<String> getTestResult(@FieldMap Map<String,String> map);
    @POST(Constant.APP_APIS)
    @FormUrlEncoded
    Observable<String> getTestResult2(@FieldMap Map<String,String> map);
//    @Multipart
//    @POST(Constant.APP_APIS)
//    Observable<String> getKLineResult(@Part("data") RequestBody body);
//@Multipart
//@POST(Constant.APP_APIS)
//Observable<String> getTestResult(@Part("data") RequestBody body);
@POST(Constant.APP_APIS)
@FormUrlEncoded
Observable<String> getKLineResult(@FieldMap Map<String,String> map);


//    @Multipart
//    @POST(Constant.APP_APIS)
//    Observable<String> saveUserHeadImage(@Part("data") RequestBody bodys, @Part MultipartBody.Part file);
    @POST(Constant.APP_APIS)
    @Multipart
    Observable<String> saveUserHeadImage(@FieldMap Map<String,String> map, @Part MultipartBody.Part file);
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);


}
