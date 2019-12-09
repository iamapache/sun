package com.madaex.exchange.common.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.madaex.exchange.common.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JUGG on 2016/12/9.
 */

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Fragment provideFragment() {
        return mFragment;
    }
}
