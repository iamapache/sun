package com.madaex.exchange.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.madaex.exchange.ui.mine.bean.BannerData;
import com.youth.banner.loader.ImageLoader;

/**
 * 项目：  sun
 * 类名：  GlideImageLoader.java
 * 时间：  2020/1/6 16:16
 * 描述：
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        BannerData.DataBean bannerData = (BannerData.DataBean) path;
        //Glide 加载图片简单用法
        Glide.with(context).load(bannerData.getImg()).into(imageView);
    }

}