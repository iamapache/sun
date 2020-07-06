package com.madaex.exchange.common.di.module;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.madaex.exchange.common.util.EncryptUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 项目：  sun
 * 类名：  CustomGsonResponseConverter.java
 * 时间：  2020/7/6 11:01
 * 描述：
 */
class CustomGsonResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String originalBody = value.string();
            // 解密
            String body = EncryptUtils.encryptMD2ToString(originalBody);
            // 获取json中的code，对json进行预处理
            JSONObject json = new JSONObject(body);
            int code = json.optInt("code");
            // 当code不为0时，设置data为{}，这样转化就不会出错了
            if (code != 0) {
                json.put("data", new JSONObject());
                body = json.toString();
            }

            return adapter.fromJson(body);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}