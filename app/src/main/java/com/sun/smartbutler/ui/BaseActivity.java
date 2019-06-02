package com.sun.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.ui
 * 文件名:   BaseActivity
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 9:16
 * 描述:    activity基类
 */


/**
 * 统一的属性
 * 统一的方法
 * 统一的接口
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

}

    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
