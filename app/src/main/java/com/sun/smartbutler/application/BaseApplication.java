package com.sun.smartbutler.application;

import android.app.Application;

import com.sun.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.application
 * 文件名:   BaseApplication
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 9:15
 * 描述:    TODO
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUG_APP_ID, false);

    }
}
