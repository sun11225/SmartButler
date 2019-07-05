package com.sun.smartbutler.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.view
 * 文件名:   DispatchLinearLayout
 * 创建者:   sun
 * 创建时间: 2019/7/5 0005 9:02
 * 描述:    事件分发
 */
public class DispatchLinearLayout extends LinearLayout {

    private DispatchEventListener dispatchEventListener;

    public DispatchEventListener getDispatchEventListener() {
        return dispatchEventListener;
    }

    public void setDispatchEventListener(DispatchEventListener dispatchEventListener) {
        this.dispatchEventListener = dispatchEventListener;
    }

    public DispatchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //接口
    public static interface DispatchEventListener {
        boolean dispatchEvent(KeyEvent event);
    }


    //为何重写？
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        //如果不为空 获取事件
        if (dispatchEventListener != null) {
            return dispatchEventListener.dispatchEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
