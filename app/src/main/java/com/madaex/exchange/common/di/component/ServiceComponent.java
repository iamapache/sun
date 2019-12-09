package com.madaex.exchange.common.di.component;

import android.content.Context;

import com.madaex.exchange.common.di.module.ServiceModule;
import com.madaex.exchange.common.di.scope.ContextLife;
import com.madaex.exchange.common.di.scope.ServiceScope;

import dagger.Component;


/**
 * Created by JUGG on 2016/12/9.
 */

@ServiceScope
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
