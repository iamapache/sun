package com.madaex.exchange.common.progress;

import android.os.Environment;

import com.madaex.exchange.common.base.BaseApplication;
import com.orhanobut.logger.Logger;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 项目：  frame
 * 类名：  StorageUtil.java
 * 时间：  2018/7/10 16:26
 * 描述：  ${TODO}
 */
public class StorageUtil {
    private static final String DIR_NAME_APK = "upgrade_apk";

    private StorageUtil() {
    }

    /**
     * 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录
     */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath());
//            sb.append(getCachePath());
        } else {
            sb.append(getCachePath());
        }

        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        Logger.i("==" + path);
        ///data/data/com.app.frames/cache/upgrade_apk/
        ///storage/emulated/0/Download/upgrade_apk/
        ///storage/emulated/0/Android/data/com.app.frames/cache/upgrade_apk/
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 获取SD下的应用目录
     */
    public static String getExternalStoragePath() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsolutePath());
        sb.append(File.separator);
        Logger.i("==" + sb.toString());
        return sb.toString();
    }

    /**
     * 获取应用的cache目录
     */
    public static String getCachePath() {
        File f = BaseApplication.getInstance().getExternalCacheDir();
        if (null == f) {
            return null;
        } else {
//            chmod("777", f.getAbsolutePath()); //更改文件权限
            return f.getAbsolutePath() + "/";
        }
    }

    /**
     * 获取权限
     *
     * @param permission 权限
     * @param path       路径
     */
    public void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取 apk 文件夹
     *
     * @return
     */
    static File getApkFileDir() {
        File apkFile =
                new File(getDir(DIR_NAME_APK));
        return apkFile;
    }

    /**
     * 获取 apk 文件
     *
     * @param version
     * @return
     */
    static File getApkFile(String version) {
        String appName;
        try {
            appName = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).applicationInfo.loadLabel(BaseApplication.getInstance().getPackageManager()).toString();
        } catch (Exception e) {
            // 利用系统api getPackageName()得到的包名，这个异常根本不可能发生
            appName = "";
        }
        return new File(getApkFileDir(), appName + "_v" + version + ".apk");
    }

    /**
     * 保存 apk 文件
     *
     * @param is
     * @param version
     * @return
     */
    public static File saveApk(ProgressCallBack progressCallBack, InputStream is, String version) {
        File file = getApkFile(version);

        if (writeFile(progressCallBack, file, is)) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 根据输入流，保存文件
     *
     * @param file
     * @param is
     * @return
     */
    static boolean writeFile(ProgressCallBack progressCallBack, File file, InputStream is) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
            progressCallBack.unsubscribe();
            return true;
        } catch (Exception e) {
            if (file != null && file.exists()) {
                file.deleteOnExit();
            }
            e.printStackTrace();
        } finally {
            closeStream(os);
            closeStream(is);
        }
        return false;
    }


    /**
     * 删除文件或文件夹
     *
     * @param file
     */
    static void deleteFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                deleteFile(f);
                            } else {
                                f.delete();
                            }
                        }
                    }
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭流
     *
     * @param closeable
     */
    static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
