package com.pc.mimi.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.pc.mimi.http.API;

import me.goldze.mvvmhabit.utils.KLog;

public class ImageLoaderUtils {
    private ImageLoaderUtils() {
        super();
    }

    /**
     * 加载本项目中图片
     *
     * @param imageView
     * @param url
     */
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView).load(API.COMMON_PIC_URL + url).into(imageView);
    }

    /**
     * 加载全路径图片
     *
     * @param imageView
     * @param url
     */
    public static void loadLocalImage(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }

    /**
     * 高斯模糊
     *
     * @param imageView
     * @param url
     */
    public static void loadImageByGaoSi(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(API.COMMON_PIC_URL + url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(imageView.getContext(),23, 8)))// “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView);
    }

    /**
     * 加载进度监听
     *
     * @param imageView
     * @param url
     */
    public static void loadImageListener(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                KLog.d("资源加载异常");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                KLog.d("图片加载完成");
                return false;
            }
        }).into(imageView);
    }

}
