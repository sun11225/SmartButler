package com.sun.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.sun.smartbutler.R;
import com.sun.smartbutler.service.SmsService;
import com.sun.smartbutler.utils.ShareUtils;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报开关
    private Switch sw_speak;

    //短信提醒
    private Switch sw_SMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }
    private void initView() {
        sw_speak=(Switch)findViewById(R.id.switch_1);
        sw_speak.setOnClickListener(this);

        sw_SMS= (Switch) findViewById(R.id.switch_2);
        sw_SMS.setOnClickListener(this);

        boolean save_switch = ShareUtils.getBoolean(this, "save_switch", false);
        sw_speak.setChecked(save_switch);

        boolean saveSms = ShareUtils.getBoolean(this, "save_SMS", false);
        sw_SMS.setChecked(saveSms);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_1:
                //点击按钮,切换状态
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                ShareUtils.putBoolean(SettingActivity.this,"save_switch",sw_speak.isChecked());
                break;
            case R.id.switch_2:
                sw_SMS.setSelected(!sw_SMS.isSelected());
                ShareUtils.putBoolean(SettingActivity.this,"save_SMS",sw_SMS.isChecked());
                //启动服务 关闭服务
                if (sw_SMS.isChecked()){
                    startService(new Intent(this,SmsService.class));
                }else {
                    stopService(new Intent(this,SmsService.class));
                }
                break;
        }
    }
}
