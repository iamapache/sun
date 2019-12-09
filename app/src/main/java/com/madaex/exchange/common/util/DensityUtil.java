package com.madaex.exchange.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 计算公式 pixels = dips * (density / 160)
 *
 * @author
 * @version 1.0.1 2010-12-11
 */
public class DensityUtil {
    private static final String TAG = DensityUtil.class.getSimpleName();
    // 当前屏幕的densityDpi  
    private static float dmDensityDpi = 0.0f;
    private static DisplayMetrics dm;
    private static float scale = 0.0f;

    /**
     * 根据构造函数获得当前手机的屏幕系数
     */
    public DensityUtil(Context context) {
        // 获取当前屏幕  
        dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        // 设置DensityDpi  
        setDmDensityDpi(dm.densityDpi);
        // 密度因子  
        scale = getDmDensityDpi() / 160;
    }

    /**
     * 当前屏幕的density因子
     * @retrun DmDensity Getter
     */
    public static float getDmDensityDpi() {
        return dmDensityDpi;
    }

    /**
     * 当前屏幕的density因子
     * @param dmDensityDpi
     * @retrun DmDensity Setter
     */
    public static void setDmDensityDpi(float dmDensityDpi) {
        DensityUtil.dmDensityDpi = dmDensityDpi;
    }

    /**
     * 密度转换像素
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率将dp的单位转成px(像素)
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取手机宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
    * 获取手机高度
    */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 像素转换密度
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public String toString() {
        return " dmDensityDpi:" + dmDensityDpi;
    }

}  