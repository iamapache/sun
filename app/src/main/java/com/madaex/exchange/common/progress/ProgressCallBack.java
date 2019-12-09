package com.madaex.exchange.common.progress;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * 项目：  frame
 * 类名：  ProgressCallBack.java
 * 时间：  2018/7/10 18:40
 * 描述：  ${TODO}
 */
public abstract class ProgressCallBack<T> {

    private String destFileDir;
    private String destFileName;

    public ProgressCallBack() {
        subscribeLoadProgress();
    }

    public abstract void progress(long progress, long total, int percent);

    public abstract void onStart();

    public abstract void onCompleted();


    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }

    /**
     * 订阅加载的进度条
     */
    public void subscribeLoadProgress() {
        Disposable disposable = RxBus.getIntanceBus().doSubscribe(ProgressEvent.class, new Consumer<ProgressEvent>() {
            @Override
            public void accept(ProgressEvent fileLoadEvent) throws Exception {
                progress(fileLoadEvent.getProgress(), fileLoadEvent.getTotal(), fileLoadEvent.getPercent());
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
        RxBus.getIntanceBus().addSubscription(this, disposable);
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    public void unsubscribe() {
        RxBus.getIntanceBus().unSubscribe(this);
    }
}
