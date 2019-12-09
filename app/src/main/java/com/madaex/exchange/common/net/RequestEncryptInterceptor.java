package com.madaex.exchange.common.net;


import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 项目：  frame
 * 类名：  RequestEncryptInterceptor.java
 * 时间：  2018/8/3 20:18
 * 描述：  ${TODO}
 */
public class RequestEncryptInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String paramsStr = buffer.readString(charset);
        try {
//            paramsStr = Base64Utils.encode(RSAHelper.decryptByPublicKey(paramsStr.getBytes(),RSAHelper.KEY_PUBLIC));
        } catch (Exception e) {
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), paramsStr);
        request = request.newBuilder()
                .post(requestBody)
                .build();
        return chain.proceed(request);
    }
}
