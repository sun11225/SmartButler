package com.sun.smartbutler.utils;

import android.util.Log;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.utils
 * 文件名:   LogUtil
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 11:31
 * 描述:    Log封装类
 */
public class LogUtil {
    private static final boolean DEBUG = true;

    private static final String TAG = "LogUtil:";

    //六个等级 VDIWEF

    public static void v(String text) {
        if (DEBUG) {
            Log.v(TAG, text);
        }
    }

    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
