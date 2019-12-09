package com.madaex.exchange.common.progress;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.madaex.exchange.common.base.BaseApplication;

import java.io.File;

/**
 * 项目：  frame
 * 类名：  UpgradeUtil.java
 * 时间：  2018/7/10 17:11
 * 描述：  ${TODO}
 */
public class UpgradeUtil {
    /**
     * apk 文件是否已经下载过，如果已经下载过就直接安装
     *
     * @param version 新 apk 文件版本号
     * @return
     */
    public static boolean isApkFileDownloaded(String version) {
        File apkFile = StorageUtil.getApkFile(version);
        if (apkFile.exists()) {
            installApk(apkFile);
            return true;
        }
        return false;
    }


    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    public static void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(BaseApplication.getInstance(), "com.madaex.exchange.fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        BaseApplication.getInstance().startActivity(intent);
    }

    /**
     * 删除之前升级时下载的老的 apk 文件
     */

    public static void deleteOldApk() {
        File apkDir = StorageUtil.getApkFileDir();
        if (apkDir == null || apkDir.listFiles() == null || apkDir.listFiles().length == 0) {
            return;
        }
        // 删除文件
        StorageUtil.deleteFile(apkDir);
    }

}
