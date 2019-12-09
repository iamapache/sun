package com.madaex.exchange.common.di.component;

import android.content.Context;

import com.madaex.exchange.common.ApiService;
import com.madaex.exchange.common.base.BaseApplication;
import com.madaex.exchange.common.di.module.ApiModule;
import com.madaex.exchange.common.di.module.ApplicationModule;
import com.madaex.exchange.common.di.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by JUGG on 2016/12/9.
 */
@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();

    ApiService getApiService();

    void inject(BaseApplication app);
}