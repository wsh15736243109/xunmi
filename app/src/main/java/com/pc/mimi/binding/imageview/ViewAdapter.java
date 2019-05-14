package com.pc.mimi.binding.imageview;


import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pc.mimi.util.ImageLoaderUtils;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"setUrl", "isGs"}, requireAll = true)
    public static void setImageurl(ImageView imageView, String url, boolean isGs) {
        if (!TextUtils.isEmpty(url)) {
            if (isGs)
                //使用Glide框架加载图片
                ImageLoaderUtils.loadImageByGaoSi(imageView, url);
            else
                ImageLoaderUtils.loadImage(imageView, url);
        }
    }
}

