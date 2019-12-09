package com.madaex.exchange.common.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 项目：  frame
 * 类名：  JsonAssetsReaderUtil.java
 * 时间：  2018/6/29 15:49
 * 描述：  ${TODO}
 */
public class JsonAssetsReaderUtil {
    public static String getJsonStrFromAssets(Context context, String jsonFileName) {
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuilder = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(jsonFileName), "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String jsonStr;
            stringBuilder = new StringBuilder();
            while ((jsonStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

}
