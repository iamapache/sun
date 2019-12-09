package com.madaex.exchange.common.progress;

/**
 * 项目：  frame
 * 类名：  DownloadProgressEvent.java
 * 时间：  2018/7/10 17:38
 * 描述：  ${TODO}
 */
public class ProgressEvent {
    /**
     * 文件总大小
     */
    private long mTotal;
    /**
     * 当前下载进度
     */
    private long mProgress;

    public ProgressEvent(long total, long progress) {
        mTotal = total;
        mProgress = progress;
    }



    /**
     * 获取百分比,该计算舍去了小数点,如果你想得到更精确的值,请自行计算
     *
     * @return
     */
    public int getPercent() {
        if (getProgress() <= 0 || getTotal() <= 0) {
            return 0;
        }
        return (int) ((100 * getProgress()) / getTotal());
    }


    /**
     * 获取文件总大小
     *
     * @return
     */
    public long getTotal() {
        return mTotal;
    }

    /**
     * @return
     */
    public long getProgress() {
        return mProgress;
    }

    /**
     * 是否还没有下载完成
     *
     * @return
     */
    public boolean isNotDownloadFinished() {
        return mTotal != mProgress;
    }
}
