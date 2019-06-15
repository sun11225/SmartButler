package com.sun.smartbutler.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.sun.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.application
 * 文件名:   BaseApplication
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 9:15
 * 描述:    Application
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUG_APP_ID, false);
        //初始化bmob
        Bmob.initialize(this,StaticClass.BMOB_APP_ID);
        //初始化语音播报
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.VOIVE_KEY);

    }
}
