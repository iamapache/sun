package com.madaex.exchange.common.util;

import android.view.View;
import android.widget.EditText;

import com.madaex.exchange.R;

/**
 * 项目：  madaexchange
 * 类名：  ViewUtil.java
 * 时间：  2018/8/29 11:57
 * 描述：  ${TODO}
 */

public class ViewUtil {
    private static int[] mTabImgs = new int[]{R.mipmap.icon_bank_zx, R.mipmap.icon_bank_gd, R.mipmap.icon_bank_pf,
            R.mipmap.icon_bank_ms, R.mipmap.icon_bank_xye, R.mipmap.icon_bank_pa,
            R.mipmap.icon_bank_zx, R.mipmap.icon_bank_zx, R.mipmap.icon_bank_zx,
            R.mipmap.icon_bank_js, R.mipmap.icon_bank_ny, R.mipmap.icon_bank_zs,
            R.mipmap.icon_bank_zg, R.mipmap.icon_bank_jt, R.mipmap.icon_bank_gdfz};

    public static void setView(EditText editText, final View view) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                view.setBackgroundResource(R.color.title_color);
            } else {
                view.setBackgroundResource(R.color.colorCutOffSide);
            }
        });
    }


    public static int setBankImageView(String str) {
        switch (str) {
            case "中信银行":
                return mTabImgs[0];
            case "光大银行":
                return mTabImgs[1];
            case "浦发银行":
                return mTabImgs[2];
            case "中国民生银行":
                return mTabImgs[3];
            case "兴业银行":
                return mTabImgs[4];
            case "平安银行":
                return mTabImgs[5];
            case "华夏银行":
                return mTabImgs[6];
            case "中国工商银行":
                return mTabImgs[7];
            case "中国邮政银行":
                return mTabImgs[8];
            case "中国建设银行":
                return mTabImgs[9];
            case "中国农业银行":
                return mTabImgs[10];
            case "中国招商银行":
                return mTabImgs[11];
            case "中国银行":
                return mTabImgs[12];
            case "交通银行":
                return mTabImgs[13];
            case "广东发展银行":
                return mTabImgs[14];
            default:
                return mTabImgs[14];
        }
    }
}
