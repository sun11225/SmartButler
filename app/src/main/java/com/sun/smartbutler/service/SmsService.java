package com.sun.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sun.smartbutler.R;
import com.sun.smartbutler.utils.LogUtil;
import com.sun.smartbutler.utils.StaticClass;
import com.sun.smartbutler.view.DispatchLinearLayout;


public class SmsService extends Service implements View.OnClickListener {

    private SMSReceiver smsReceiver;
    public static final String SYSTEM_DIALOGS_RESON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    private HomeWatchReceiver mHomeWatchReceiver;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private DispatchLinearLayout mView;

    //窗口里初始化
    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;
    private String phone;
    private String messageBody;

    public SmsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("开启服务");
        init();
    }

    private void init() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StaticClass.SMS_INTENT);
        intentFilter.setPriority(Integer.MAX_VALUE);
        smsReceiver = new SMSReceiver();

        //动态注册
        registerReceiver(smsReceiver, intentFilter);

        mHomeWatchReceiver = new HomeWatchReceiver();
        IntentFilter intentFilter1 = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatchReceiver, intentFilter1);
    }

    //监听Home键的广播
    class HomeWatchReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOGS_RESON_KEY);
                if (SYSTEM_DIALOGS_HOME_KEY.equals(reason)) {
                    if (mView.getParent() != null) {
                        windowManager.removeView(mView);
                    }
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("停止服务");
        //解除注册
        unregisterReceiver(smsReceiver);
        unregisterReceiver(mHomeWatchReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                //回复短信
                sendSMS();
                //关闭窗口
                if (mView.getParent() != null) {
                    windowManager.removeView(mView);
                }
                break;
        }
    }

    //监听短信
    public class SMSReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (StaticClass.SMS_INTENT.equals(action)) {
                LogUtil.d("短信来了！");
                //获取短信的内容,返回的是一个数组
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                //遍历数组得到的数据
                for (Object obj : pdus) {
                    //把数组元素转换成短信对象
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);
                    //发件人
                    phone = message.getOriginatingAddress();
                    //短信内容
                    messageBody = message.getMessageBody();

                    LogUtil.d("短信内容：" + phone + ": " + messageBody);

                    showWindow();
                }
            }

        }
    }

    private void sendSMS() {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", "");
        startActivity(intent);

    }

    //短信提示窗口
    private void showWindow() {
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义属性
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义类别
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //定义格式 透明
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);
        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);

        //添加VIEW到窗口
        windowManager.addView(mView, layoutParams);

        mView.setDispatchEventListener(dispatchEventListener);
    }

    private DispatchLinearLayout.DispatchEventListener dispatchEventListener=new
            DispatchLinearLayout.DispatchEventListener() {
        @Override
        public boolean dispatchEvent(KeyEvent event) {
            //判断是否按了返回键
            if (event.getKeyCode()==KeyEvent.KEYCODE_BACK){
                if (mView.getParent()!=null){
                    windowManager.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
