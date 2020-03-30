package com.madaex.exchange.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.allenliu.versionchecklib.core.http.HttpParams;
import com.google.gson.Gson;
import com.madaex.exchange.R;
import com.madaex.exchange.common.base.BaseApplication;
import com.madaex.exchange.common.languagelib.LanguageType;
import com.madaex.exchange.common.languagelib.MultiLanguageUtil;
import com.madaex.exchange.ui.constant.Constants;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 项目：  madaexchange
 * 类名：  DataUtil.java
 * 时间：  2018/8/24 15:26
 * 描述：  ${TODO}
 */

public class DataUtil {
    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static String is9Length(String value) {
        try {
            if(value.length()>=9){
                return new BigDecimal(value.substring(8)).toString();
            }else {
                return new BigDecimal(value).toString();
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }
    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Map sign(Map map){
        Set<Map.Entry<String, String>> set = map.entrySet();
        StringBuffer stringBuffer = new StringBuffer();

        map.put("version",AppUtils.getVerName(BaseApplication.getInstance())+"");
        map.put("timestamp",System.currentTimeMillis()+"");
        map.put("token",SPUtils.getString(Constants.TOKEN,""));
        map.put("user_id",SPUtils.getString(Constants.USER_ID,""));
        map.put("port_sn","9e304d4e8df1b74cfa009913198428ab");
        int languageType = SPUtils.getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
        if(languageType==2){
            map.put("lan","zh-cn");
        }else if(languageType==1){
            map.put("lan","en-us");
        }else {
            map.put("lan","zh-cn");
        }

//        for (Map.Entry<String, String> me : set) {
//            stringBuffer.append(me.getKey());
//            stringBuffer.append("=");
//            stringBuffer.append(me.getValue());
//            stringBuffer.append("&");
//        }
//        String data="";
//        try {
//            data =  stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
//
////            data =Base64Utils.encode(rsa.encryptPublicKey(str.getBytes(),rsa.PUBLIC_KEY));
//            Logger.i("<==>encryptByPublicKey:" + data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (Map.Entry<String, String> me : set) {
//            stringBuffer.append(me.getKey());
//            stringBuffer.append("=");
//            stringBuffer.append(me.getValue());
//            stringBuffer.append("&");
//        }
//        String data="";
//        data =  stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
        Map newmap = new HashMap();
        Gson gson = new Gson();
        String data = gson.toJson(map);
        newmap.put("data",data);
        Logger.i("<==>:" + data);
        return newmap;
    }

    public static HttpParams signlown(HttpParams map){
        StringBuffer stringBuffer = new StringBuffer();

        map.put("version",AppUtils.getVerName(BaseApplication.getInstance())+"");
        map.put("timestamp",System.currentTimeMillis()+"");
        map.put("token",SPUtils.getString(Constants.TOKEN,""));
        map.put("user_id",SPUtils.getString(Constants.USER_ID,""));
        map.put("port_sn","9e304d4e8df1b74cfa009913198428ab");
        int languageType = SPUtils.getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
        if(languageType==2){
            map.put("lan","zh-cn");
        }else if(languageType==1){
            map.put("lan","en-us");
        }else {
            map.put("lan","zh-cn");
        }

//        for (Map.Entry<String, String> me : set) {
//            stringBuffer.append(me.getKey());
//            stringBuffer.append("=");
//            stringBuffer.append(me.getValue());
//            stringBuffer.append("&");
//        }
//        String data="";
//        try {
//            data =  stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
//
////            data =Base64Utils.encode(rsa.encryptPublicKey(str.getBytes(),rsa.PUBLIC_KEY));
//            Logger.i("<==>encryptByPublicKey:" + data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (Map.Entry<String, String> me : set) {
//            stringBuffer.append(me.getKey());
//            stringBuffer.append("=");
//            stringBuffer.append(me.getValue());
//            stringBuffer.append("&");
//        }
//        String data="";
//        data =  stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
        HttpParams newmap = new HttpParams();
        Gson gson = new Gson();
        String data = gson.toJson(map);
        newmap.put("data",data);
        Logger.i("<==>:" + data);
        return newmap;
    }

    public static Map sign2(Map map){
        Set<Map.Entry<String, String>> set = map.entrySet();
        StringBuffer stringBuffer = new StringBuffer();

        map.put("version",AppUtils.getVerName(BaseApplication.getInstance())+"");
        map.put("timestamp",System.currentTimeMillis()+"");
        map.put("token",SPUtils.getString(Constants.TOKEN,""));
        map.put("port_sn","42d6477e16393b5f47e0ad887169f807");
        int languageType = SPUtils.getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
        if(languageType==2){
            map.put("lan","zh-cn");
        }else if(languageType==1){
            map.put("lan","en-us");
        }else {
            map.put("lan","zh-cn");
        }
                for (Map.Entry<String, String> me : set) {
            stringBuffer.append(me.getKey());
            stringBuffer.append("=");
            stringBuffer.append(me.getValue());
            stringBuffer.append("&");
        }
//        String data="";
//        data =  stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
        Gson gson = new Gson();
        String data = gson.toJson(map);
        Map newmap = new HashMap();
        newmap.put("data",data);
        Logger.i("<==>:" + data);
        return newmap;
    }
    public static String thousand(String map,Context mContext){
        if(!TextUtils.isEmpty(map)){
            if(Double.valueOf(map)>=10000){
                BigDecimal bg = new BigDecimal(Double.valueOf(map)/10000);
                double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                return f1+mContext.getString(R.string.thousand);
            }else {
                return map;
            }
        }
        return map;
    }
}
