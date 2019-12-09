package com.madaex.exchange.common.di.module;

import android.app.Activity;
import android.content.Context;

import com.madaex.exchange.common.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JUGG on 2016/12/9.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Context ProvideActivityContext() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    public Activity ProvideActivity() {
        return mActivity;
    }


}
