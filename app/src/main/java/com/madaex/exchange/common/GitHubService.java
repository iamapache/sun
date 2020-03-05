package com.madaex.exchange.common;

import com.madaex.exchange.common.net.Constant;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 项目：  sun
 * 类名：  GitHubService.java
 * 时间：  2020/3/5 21:50
 * 描述：
 */
public interface GitHubService {

    @POST(Constant.APP_APIS)
    @FormUrlEncoded
    Observable<String> getTestResult2(@FieldMap Map<String, String> map);

}
