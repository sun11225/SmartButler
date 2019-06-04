package com.sun.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.sun.smartbutler.R;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.view
 * 文件名:   CustomDialog
 * 创建者:   sun
 * 创建时间: 2019/6/3 0003 17:34
 * 描述:    自定义Dialog
 */
public class CustomDialog extends Dialog {

    //定义模板
    public CustomDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }

    //定义它的属性
    public CustomDialog(Context context, int width, int height,int layout, int style, int gravity, int anim) {
        super(context,style);
        //设置属性
//        setContentView(layout);
//        Window window = getWindow();
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width=width;
//        params.height = height;
//        params.gravity=gravity;
//        window.setAttributes(params);
//        window.setWindowAnimations(anim);

        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.AppTheme_anim_style);
    }

}
