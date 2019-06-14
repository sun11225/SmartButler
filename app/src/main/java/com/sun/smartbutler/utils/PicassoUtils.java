package com.sun.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.sun.smartbutler.R;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.utils
 * 文件名:   PicassoUtils
 * 创建者:   sun
 * 创建时间: 2019/6/14 0014 15:10
 * 描述:    picasso封装
 */
public class PicassoUtils {


    //默认的加载图片形式
    public static void loadImageView(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    //指定大小的加载形式
    public static void loadImageViewSize(Context context, String url, int width, int height, ImageView imageView) {

        Picasso.with(context)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    //有默认图片的加载形式
    public static void loadImageViewHolder(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }


    //按比例裁剪图片
    public static void loadImageViewGrop(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }

    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }
}
