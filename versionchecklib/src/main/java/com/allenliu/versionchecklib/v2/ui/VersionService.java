package com.allenliu.versionchecklib.v2.ui;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.callback.DownloadListener;
import com.allenliu.versionchecklib.core.DownloadManager;
import com.allenliu.versionchecklib.core.PermissionDialogActivity;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.utils.AllenEventBusUtil;
import com.allenliu.versionchecklib.utils.AppUtils;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.RequestVersionBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.allenliu.versionchecklib.v2.eventbus.AllenEventType;
import com.allenliu.versionchecklib.v2.eventbus.CommonEvent;
import com.allenliu.versionchecklib.v2.net.DownloadMangerV2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class VersionService extends Service {
    public static DownloadBuilder builder;
//    private static DownloadBuilder tempBuilder;

    private BuilderHelper builderHelper;
    private NotificationHelper notificationHelper;
    private boolean isServiceAlive = false;
    private ExecutorService executors;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ALog.e("version service create");
//        builder = tempBuilder;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ALog.e("version service destroy");
        builder = null;
        builderHelper = null;
        if (notificationHelper != null)
            notificationHelper.onDestroy();
        notificationHelper = null;
        isServiceAlive = false;
        if(executors!=null)
        executors.shutdown();
        stopForeground(true);
        AllenHttp.getHttpClient().dispatcher().cancelAll();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    private NotificationManager notificationManager;
    private String notificationId = "channelId";
    private String notificationName = "channelName";
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建NotificationChannel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1,getNotification());
    }
    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle("下载服务")
                .setContentText("正在运行");
        //设置Notification的ChannelID,否则不能正常显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void enqueueWork(final Context context) {
        //清除之前的任务，如果有
//        AllenVersionChecker.getInstance().cancelAllMission(context);
        Intent intent = new Intent(context, VersionService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            // Pre-O behavior.
            context.startService(intent);
        }
    }


    protected void onHandleWork() {
        if (checkWhetherNeedRequestVersion()) {
            requestVersion();
        } else {
            downloadAPK();
        }
    }

    /**
     * 请求版本接口
     */
    private void requestVersion() {
        RequestVersionBuilder requestVersionBuilder = builder.getRequestVersionBuilder();
        OkHttpClient client = AllenHttp.getHttpClient();
        HttpRequestMethod requestMethod = requestVersionBuilder.getRequestMethod();
        Request request = null;
        switch (requestMethod) {
            case GET:
                request = AllenHttp.get(requestVersionBuilder).build();
                break;
            case POST:
                request = AllenHttp.post(requestVersionBuilder).build();
                break;
            case POSTJSON:
                request = AllenHttp.postJson(requestVersionBuilder).build();
                break;
        }
        final RequestVersionListener requestVersionListener = requestVersionBuilder.getRequestVersionListener();
        Handler handler = new Handler(Looper.getMainLooper());
        if (requestVersionListener != null) {
            try {
                final Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    if (!isServiceAlive)
                        return;
                    final String result = response.body().string();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            UIData versionBundle = requestVersionListener.onRequestVersionSuccess(result);
                            builder.setVersionBundle(versionBundle);
                            downloadAPK();
                        }
                    });

                } else {
                    if (!isServiceAlive)
                        return;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
                            requestVersionListener.onRequestVersionFailure(response.message());
                        }
                    });
                }
            } catch (final IOException e) {
                e.printStackTrace();
                if (!isServiceAlive)
                    return;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
                        requestVersionListener.onRequestVersionFailure(e.getMessage());
                    }
                });

            }
        } else {
            throw new RuntimeException("using request version function,you must set a requestVersionListener");
        }
    }


    private boolean checkWhetherNeedRequestVersion() {
        if (builder.getRequestVersionBuilder() != null)
            return true;
        else
            return false;
    }

    private void downloadAPK() {
        if (builder.getVersionBundle() != null) {
            if (builder.isDirectDownload()) {
                AllenEventBusUtil.sendEventBus(AllenEventType.START_DOWNLOAD_APK);
            } else {
                if (builder.isSilentDownload()) {
                    requestPermissionAndDownload();
                } else {
                    showVersionDialog();
                }
            }
        } else {
            AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
        }
    }


    /**
     * 开启UI展示界面
     */
    private void showVersionDialog() {
        if (builder != null) {
            Intent intent = new Intent(this, UIActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void showDownloadingDialog() {
        if (builder != null && builder.isShowDownloadingDialog()) {
            Intent intent = new Intent(this, DownloadingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void updateDownloadingDialogProgress(int progress) {
        CommonEvent commonEvent = new CommonEvent();
        commonEvent.setEventType(AllenEventType.UPDATE_DOWNLOADING_PROGRESS);
        commonEvent.setData(progress);
        commonEvent.setSuccessful(true);
        EventBus.getDefault().post(commonEvent);
    }

    private void showDownloadFailedDialog() {
        if (builder != null) {
            Intent intent = new Intent(this, DownloadFailedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void requestPermissionAndDownload() {
        if (builder != null) {
            Intent intent = new Intent(this, PermissionDialogActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void install() {
        AllenEventBusUtil.sendEventBus(AllenEventType.DOWNLOAD_COMPLETE);
        final String downloadPath = getDownloadFilePath();
        if (builder.isSilentDownload()) {
            showVersionDialog();
        } else {
            builderHelper.checkForceUpdate();
            AppUtils.installApk(getApplicationContext(), new File(downloadPath));
        }
    }

    private String getDownloadFilePath() {
        return builder.getDownloadAPKPath() + getString(R.string.versionchecklib_download_apkname, builder.getApkName() != null ? builder.getApkName() : getPackageName());
    }

    @WorkerThread
    private void startDownloadApk() {
        //判断是否缓存并且是否强制重新下载
        final String downloadPath = getDownloadFilePath();
        if (DownloadManager.checkAPKIsExists(getApplicationContext(), downloadPath, builder.getNewestVersionCode()) && !builder.isForceRedownload()) {
            ALog.e("using cache");
            install();
            return;
        }
        builderHelper.checkAndDeleteAPK();
        String downloadUrl = builder.getDownloadUrl();
        if (downloadUrl == null && builder.getVersionBundle() != null) {
            downloadUrl = builder.getVersionBundle().getDownloadUrl();
        }
        if (downloadUrl == null) {
            AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
            throw new RuntimeException("you must set a download url for download function using");
        }
        ALog.e("downloadPath:" + downloadPath);

        DownloadMangerV2.download(downloadUrl, builder.getDownloadAPKPath(), getString(R.string.versionchecklib_download_apkname, builder.getApkName() != null ? builder.getApkName() : getPackageName()), new DownloadListener() {
            @Override
            public void onCheckerDownloading(int progress) {
                if (isServiceAlive) {
                    if (!builder.isSilentDownload()) {
                        notificationHelper.updateNotification(progress);
                        updateDownloadingDialogProgress(progress);
                    }
                    if (builder.getApkDownloadListener() != null)
                        builder.getApkDownloadListener().onDownloading(progress);
                }
            }

            @Override
            public void onCheckerDownloadSuccess(File file) {
                if (isServiceAlive) {
                    if (!builder.isSilentDownload())
                        notificationHelper.showDownloadCompleteNotifcation(file);
                    if (builder.getApkDownloadListener() != null)
                        builder.getApkDownloadListener().onDownloadSuccess(file);
                    install();
                }
            }

            @Override
            public void onCheckerDownloadFail() {

                if (!isServiceAlive)
                    return;
                if (builder.getApkDownloadListener() != null)
                    builder.getApkDownloadListener().onDownloadFail();

                if (!builder.isSilentDownload()) {
                    AllenEventBusUtil.sendEventBus(AllenEventType.CLOSE_DOWNLOADING_ACTIVITY);
                    if (builder.isShowDownloadFailDialog()) {
                        showDownloadFailedDialog();
                    }
                    notificationHelper.showDownloadFailedNotification();
                } else {
                    AllenVersionChecker.getInstance().cancelAllMission(getApplicationContext());
                }

            }

            @Override
            public void onCheckerStartDownload() {
                ALog.e("start download apk");
                if (!builder.isSilentDownload()) {
                    notificationHelper.showNotification();
                    showDownloadingDialog();
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(CommonEvent commonEvent) {
        switch (commonEvent.getEventType()) {
            case AllenEventType.START_DOWNLOAD_APK:
                requestPermissionAndDownload();
                break;
            case AllenEventType.REQUEST_PERMISSION:
                boolean permissionResult = (boolean) commonEvent.getData();
                if (permissionResult)
                    startDownloadApk();
                else
                    stopSelf();
                break;
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public synchronized void onReceiveDownloadBuilder(DownloadBuilder downloadBuilder) {
        builder = downloadBuilder;
        if (builder != null) {
            isServiceAlive = true;
            builderHelper = new BuilderHelper(getApplicationContext(), builder);
            notificationHelper = new NotificationHelper(getApplicationContext(), builder);
            startForeground(1, notificationHelper.getServiceNotification());
            executors = Executors.newSingleThreadExecutor();
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    onHandleWork();
                }
            });

        }
        EventBus.getDefault().removeStickyEvent(downloadBuilder);
    }


}
