package com.madaex.exchange.common.di.module;

import android.content.Context;

import com.madaex.exchange.common.base.BaseApplication;
import com.madaex.exchange.common.di.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JUGG on 2016/12/9.
 */

@Module
public class ApplicationModule {
    private BaseApplication mApplication;

    public ApplicationModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }


}
