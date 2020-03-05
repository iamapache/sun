package com.madaex.exchange.common;

import com.madaex.exchange.common.base.BaseApplication;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * 项目：  sun
 * 类名：  CacheProviders.java
 * 时间：  2020/3/5 21:44
 * 描述：
 */
public class CacheProviders {

    private static UserCacheProviders userCacheProviders;

    public synchronized static UserCacheProviders getUserCache() {
        if (userCacheProviders == null) {
            userCacheProviders = new RxCache.Builder()
                    .persistence(BaseApplication.getInstance().getExternalCacheDir(), new GsonSpeaker())//缓存文件的配置、数据的解析配置
                    .using(UserCacheProviders.class);//这些配置对应的缓存接口
        }
        return userCacheProviders;
    }
}