package com.sun.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.utils
 * 文件名:   UtilTools
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 9:27
 * 描述:    工具的统一类
 */
public class UtilTools {

    public static void setFont(Context mContext, TextView textView) {
        //设置字体
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
