package com.madaex.exchange.common.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.madaex.exchange.ui.finance.bean.QrCode;

/**
 * 项目：  wallet-android
 * 类名：  QrcodeUtil.java
 * 时间：  2018/6/20 19:45
 * 描述：  ${TODO}
 * @author Administrator
 */
public class QrcodeUtil {

    public static Bitmap createImage(Context context, QrCode qrCode, int density){
        try {
            Bitmap bitmap;
            BitMatrix matrix;
            Gson gson = new Gson();
            String text = gson.toJson(qrCode);
            MultiFormatWriter writer = new MultiFormatWriter();
            matrix = writer.encode(text, BarcodeFormat.QR_CODE, DensityUtil.dip2px(context,density), DensityUtil.dip2px(context,density));
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap createImage(Context context,String text,int density){
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, DensityUtil.dip2px(context,density), DensityUtil.dip2px(context,density));
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
