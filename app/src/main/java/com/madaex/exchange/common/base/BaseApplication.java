package com.madaex.exchange.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.alibaba.wireless.security.jaq.SecurityVerification;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.madaex.exchange.common.di.component.ApplicationComponent;
import com.madaex.exchange.common.di.component.DaggerApplicationComponent;
import com.madaex.exchange.common.di.module.ApiModule;
import com.madaex.exchange.common.di.module.ApplicationModule;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;
import com.madaex.exchange.common.util.SPUtils;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.io.InputStream;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * 项目：  frame
 * 类名：  BaseApplication.java
 * 时间：  2018/6/28 11:21
 * 描述：  ${TODO}
 */
public class BaseApplication extends Application {
    private static Stack<Activity> activityStack;
    private static BaseApplication instance;
    private static Context sContext;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    private ApplicationComponent mApplicationComponent;
    private SecurityVerification securityVerification = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sContext = getApplicationContext();
        init();
    }

    public Cache getCache() {
        return new Cache(new File(BaseApplication.getInstance().getCacheDir(), "httpCache"), 1024 * 1024 * 100);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void init() {
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(setCertificates(new OkHttpClient.Builder()).build()));
        CrashReport.initCrashReport(getApplicationContext(), "dbd9e14990", true);
//        配置 WebSocket，必须在 WebSocket 服务启动前设置
//        WebSocketSetting.setConnectUrl(Constant.Websocket);//必选
//
//        WebSocketSetting.setReconnectWithNetworkChanged(true);
////        WebSocketSetting.setResponseProcessDelivery(new AppResponseDispatcher());
//        //启动 WebSocket 服务
//        Intent intent = new Intent(this, WebSocketService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        } else {
//            startService(intent);
//        }
        Config config = new Config.Builder()
                .setShowLog(true)
                .setClient(new OkHttpClient.Builder()
                        .pingInterval(4, TimeUnit.SECONDS) // 设置心跳间隔，这个是3秒检测一次
                        .build())
                .setShowLog(true, "RxWebSocket")
                .setReconnectInterval(4, TimeUnit.SECONDS)//show  log
                .build();
        RxWebSocket.setConfig(config);


        MultiLanguageUtil.init(this);
        SPUtils.init(getApplicationContext());
        initApplicationComponent();
        //在子线程中完成其他初始化
        InitializeService.start(this);

        Context context = this.getApplicationContext();
        try {
            SecurityInit.Initialize(context);
            securityVerification = new SecurityVerification(context);
        } catch (JAQException e) {
            Logger.i("<==>data:" + e.getErrorCode());
            e.printStackTrace();
        }
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
    }

    public static OkHttpClient.Builder setCertificates(OkHttpClient.Builder client) {
        try {
            //Xutils.getSSLContext：获取证书SSLSocketFactory（这个网络上有很多代码，并且我之前的文章里也有写出来，在这里就不过多的描述了）
            SSLSocketFactory sslSocketFactory = getUnsafeOkHttpClient();
            client.sslSocketFactory(sslSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static SSLSocketFactory getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }};             // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);

    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束当前的Activity
     */
    public void finishLastActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void getActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    public interface SocketCallBack {
        void doSomeThing(String message);
    }

    private SocketCallBack mCallBack;

    public SocketCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(SocketCallBack callBack) {
        mCallBack = callBack;
    }
}
