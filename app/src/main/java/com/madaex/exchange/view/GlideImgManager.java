package com.madaex.exchange.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.madaex.exchange.R;
import com.madaex.exchange.common.net.Constant;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by JUGG on 2017/1/4.
 */

public class GlideImgManager {
    //crossFade(1000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
    //skipMemoryCache(true)  跳过内存缓存
    //DiskCacheStrategy.SOURCE：缓存原始数据
    //thumbnail(0.1f) 用原图的1/10作为缩略图
    //centerCrop().//中心裁剪,缩放填充至整个ImageView
    //override(80,80).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高


    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(Constant.IMG_BASE_URL+url).crossFade().thumbnail(0.1f).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
    }

    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
    }

    //圆形裁剪
    public static void loadCircleImage2(Context context, String url, ImageView iv) {
        Glide.with(context).load(Constant.IMG_BASE_URL+url).placeholder(R.mipmap.logo).error(R.mipmap.logo).transform(new GlideCircleTransform(context)).into(iv);
    }
    public static void loadCustomCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(Constant.IMG_BASE_URL+url).dontAnimate().placeholder(R.mipmap.logo).error(R.mipmap.logo).into(iv);
    }
    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(Constant.IMG_BASE_URL+url).placeholder(R.mipmap.logo).error(R.mipmap.logo).bitmapTransform(new CropCircleTransformation(context)).into(iv);
    }

    //圆角处理
    public static void loadRoundCornerImage2(Context context, String url, ImageView iv) {
//        Glide.with(context)
//                .load(Constant.IMG_BASE_URL+url)
//                .placeholder(R.mipmap.logo)
//                .error(R.mipmap.logo)
//                //.centerCrop() 千万不要加，加了就没有圆角效果了
//                .transform(new CenterCrop(context), new GlideRoundTransform(context, 12))
//                .into(iv);
        Glide.with(context).load(Constant.IMG_BASE_URL+url).placeholder(R.mipmap.logo).error(R.mipmap.logo).transform(new GlideRoundTransform(context, 10)).into(iv);;

    }

    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(Constant.IMG_BASE_URL+url).placeholder(R.mipmap.logo).error(R.mipmap.logo).bitmapTransform(new RoundedCornersTransformation(context, 20, 0,RoundedCornersTransformation.CornerType.ALL)).centerCrop().into(iv);
    }

    //灰度处理
    public static void loadGrayscaleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.logo).error(R.mipmap.logo).bitmapTransform(new GrayscaleTransformation(context)).into(iv);
    }

    //方块处理
    public static void loadSquareImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.logo).error(R.mipmap.logo).bitmapTransform(new CropSquareTransformation(context)).into(iv);
    }

    //高斯模糊处理
    public static void loadBlurImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.logo).error(R.mipmap.logo).bitmapTransform(new BlurTransformation(context, 25)).into(iv);
    }


    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file).fitCenter()
                .into(imageView);
    }

    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }





    //图片加载监听器
    public abstract interface ImageLoadListener {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        <T, K> void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source 图片地址、File、资源id
         * @param e      异常信息
         */
        <T> void onLoadingError(T source, Exception e);

    }


    public abstract interface ImageLoadDetailListener {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        <T, K> void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source 图片地址、File、资源id
         * @param e      异常信息
         */
        <T> void onLoadingError(T source, Exception e);

        <T> void onLoadingStart(T source);

    }


    /**
     * 根据上下文和 url获取 Glide的DrawableTypeRequest
     *
     * @param context 上下文
     * @param url     图片连接
     * @param <T>     Context类型
     * @param <K>     url类型
     * @return 返回DrawableTypeRequst<K> 类型
     */
    private static <T, K> DrawableTypeRequest<K> getContext(T context, K url) {
        DrawableTypeRequest<K> type = null;
        try {
            if (context instanceof android.support.v4.app.Fragment) {
                type = Glide.with((android.support.v4.app.Fragment) context).load(url);
            } else if (context instanceof android.app.Fragment) {
                type = Glide.with((android.app.Fragment) context).load(url);
            } else if (context instanceof Activity) {    //包括FragmentActivity
                type = Glide.with((Activity) context).load(url);
            } else if (context instanceof Context) {
                type = Glide.with((Context) context).load(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return type;
    }


    /**
     * 图片加载监听类
     *
     * @param <T> 图片链接 的类型
     * @param <K> 图片资源返回类型
     * @param <Z> 返回的图片url
     */
    private static class GlideListener<T, K, Z> implements RequestListener<T, K> {

        ImageLoadListener imageLoadListener = null;
        Z url;
        ImageView imageView = null;

        GlideListener(ImageLoadListener imageLoadListener, Z url, ImageView view) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
            this.imageView = view;
        }

        GlideListener(ImageLoadListener imageLoadListener, Z url) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
        }

        GlideListener(Z url) {
            this.url = url;
        }

        @Override
        public boolean onResourceReady(K resource, T model, Target<K> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (null != imageLoadListener) {
                if (imageView != null) {
                    imageLoadListener.onLoadingComplete(url, imageView, resource);
                } else {
                    imageLoadListener.onLoadingComplete(url, null, resource);
                }
            }

            return false;
        }

        @Override
        public boolean onException(Exception e, T model, Target<K> target, boolean isFirstResource) {
            if (imageLoadListener != null) {
                imageLoadListener.onLoadingError(url, e);
            }
            return false;
        }
    }


    /**
     * 获取存储器上的图片,回调返回GlideDrawable
     *
     * @param context           上下文年
     * @param file              文件File
     * @param imageLoadListener 回调监听器
     */
    public static <T> void loadImage(T context, final File file, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<File> type = getContext(context, file);
        if (type != null) {
            type.listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file));
        }
    }

    /**
     * 获取资源中的图片，回调返回GlideDrawable
     *
     * @param context           上下文
     * @param resourceId        资源id
     * @param imageLoadListener 回调监听器
     */
    public static <T> void loadImage(T context, final int resourceId, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<Integer> type = getContext(context, resourceId);
        if (type != null) {
            type.listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId));
        }

    }

    /**
     * 获取网络图片，回调返回 GlideDrawable
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 回调监听器
     */
    public static <T> void loadImage(T context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        if (type != null) {
            type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url));
        }

    }

    /**
     * 加载存储器上的图片到目标载体
     *
     * @param file      文件File
     * @param imageView 要显示到的图片ImageView
     */
    public static void loadImage(final File file, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(file)
                .listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file, imageView))
                .into(imageView);
    }

    /**
     * 加载资源中的图片到目标载体
     *
     * @param resourceId 资源id
     * @param imageView  图片View
     */
    public static void loadImage(final int resourceId, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId, imageView))
                .into(imageView);
    }





    public static <T> void loadImageDetail(final T context, final String url, final ImageView imageView, final Drawable drawable, final ImageLoadDetailListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        if (type != null) {
            type.into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    if (imageView != null && resource != null) {
                        imageView.setImageDrawable(resource);
                    }
                    if (imageLoadListener != null) {
                        imageLoadListener.onLoadingComplete(url, imageView, resource);
                    }

                }

                @Override
                public void onStart() {
                    super.onStart();
                    if (drawable != null && imageView != null) {
                        imageView.setImageDrawable(drawable);
                    }

                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    if (imageLoadListener != null) {
                        imageLoadListener.onLoadingStart(placeholder);
                    }

                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    if (imageLoadListener != null) {
                        imageLoadListener.onLoadingError(errorDrawable, e);
                    }
                }
            });
        }

    }


    /**
     * 加载bitmap，回调返回 Bitmap
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     * @param <T>               上下文类型
     */
    public static <T> void loadImageBitmap(T context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        if (type != null) {
            type.asBitmap().listener(new GlideListener<String, Bitmap, String>(imageLoadListener, url));
        }
    }


    /**
     * 加载GifDrawable，回调返回 GifDrawable
     *
     * @param context           上下文
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     */
    public static <T> void loadImageGif(T context, final String url, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = getContext(context, url);
        if (type != null) {
            type.asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url));

        }
    }


    /**
     * 加载GifDrawable，回调返回 GifDrawable
     *
     * @param url               图片url
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGifThum(final String url, ImageView imageView, final ImageLoadListener imageLoadListener, Drawable drawable) {
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        type.asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, Bitmap, String>(imageLoadListener, url))
                .into(imageView);
    }


    /**
     * 加载gif图片到指定ImageView
     *
     * @param url               图片Url
     * @param imageView         图片View
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGif(final String url, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        type.asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


    /**
     * 释放内存
     *
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }


    /**
     * 清除所有缓存
     *
     * @param context 上下文
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}
