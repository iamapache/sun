package com.madaex.exchange.common.net;


import com.madaex.exchange.common.util.GZIPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 项目：  frame
 * 类名：  NetworkInterceptor.java
 * 时间：  2018/8/3 21:33
 * 描述：  ${TODO}
 */
public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        boolean checked = true;
        //这里是网络拦截器，可以做错误处理
        MediaType mediaType = response.body().contentType();
        //当调用此方法java.lang.IllegalStateException: closed，原因为OkHttp请求回调中response.body().string()只能有效调用一次
        //String content = response.body().string();
        byte[] data = response.body().bytes();
        if (GZIPUtils.isGzip(response.headers())) {
            //请求头显示有gzip，需要解压
            data = GZIPUtils.uncompress(data);
        }
        //获取签名
        String sdkSign = response.header("sdkSign");
        try {
            //效验签名
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建一个新的responseBody，返回进行处理
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, data))
                .build();
    }

}
