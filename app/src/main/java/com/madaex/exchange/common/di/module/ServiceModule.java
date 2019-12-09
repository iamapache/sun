package com.madaex.exchange.common.di.module;

import android.app.Service;
import android.content.Context;

import com.madaex.exchange.common.di.scope.ContextLife;
import com.madaex.exchange.common.di.scope.ServiceScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JUGG on 2016/12/9.
 */

@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @ServiceScope
    @ContextLife("Service")
    public Context ProvideServiceContext() {
        return mService;
    }
}
