package com.sun.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.utils
 * 文件名:   ShareUtils
 * 创建者:   sun
 * 创建时间: 2019/6/2 14:14
 * 描述:    SharePreferences的封装 根据需求封装相应的数据类型
 */

/**
 * commit()和apply()的使用 SharedPreference相关修改使用 apply 方法进行提交会先写入内存，然后异步写入磁盘，commit
 *方法是直接写入磁盘。如果频繁操作的话 apply 的性能会优于 commit，apply会将最后修改内容写入磁盘。
 *但是如果希望立刻获取存储操作的结果，并据此做相应的其他操作，应当使用 commit。
 */

public class ShareUtils {

    public static final String NAME="config";

    public static void putString(Context mContext,String key,String values){
        SharedPreferences config = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        config.edit().putString(key,values).apply();
    }

    public static String getString(Context mContext,String key,String defValues){
        SharedPreferences config = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return config.getString(key,defValues);
    }
    public static void putInt(Context mContext,String key,int values){
        SharedPreferences config = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        config.edit().putInt(key,values).apply();
    }

    public static int getInt(Context mContext,String key,int defValues){
        SharedPreferences config = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return config.getInt(key,defValues);
    }
    public static void putBoolean(Context mContext,String key,boolean values){
        SharedPreferences config = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        config.edit().putBoolean(key,values).apply();
    }

    public static boolean getBoolean(Context mContext,String key,boolean defValues){
        SharedPreferences config = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return config.getBoolean(key,defValues);
    }

    //删除 单个
    public static void deleteShare(Context mContext,String key){
        SharedPreferences config=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        config.edit().remove(key).apply();
    }
    //删除全部
    public static void deleteAll(Context mContext){
        SharedPreferences config=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        config.edit().clear().apply();
    }
}
