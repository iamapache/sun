package com.madaex.exchange.common;

import com.madaex.exchange.common.net.Constant;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 项目：  sun
 * 类名：  GitHubServiceManager.java
 * 时间：  2020/3/5 21:50
 * 描述：
 */
public class GitHubServiceManager {

    private GitHubService service;

    public GitHubServiceManager() {
        init();
    }

    private void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build();

        service = new Retrofit.Builder()
                .baseUrl(Constant.HTTP)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 添加Rx适配器
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubService.class);
    }

    public Observable<String> getTestResult2(Map user){
        return service.getTestResult2(user);
    }
}