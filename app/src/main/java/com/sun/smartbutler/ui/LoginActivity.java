package com.sun.smartbutler.ui;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.smartbutler.MainActivity;
import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.MyUser;
import com.sun.smartbutler.utils.ShareUtils;
import com.sun.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private EditText ed_user;
    private EditText ed_password;
    private CheckBox remember_pwd;
    private Button bt_login;
    private Button bt_register;
    private TextView forget_pwd;

    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        mImageView = (ImageView) findViewById(R.id.mImageView);
        ed_user = (EditText) findViewById(R.id.ed_user);
        ed_password = (EditText) findViewById(R.id.ed_pwd);
        remember_pwd = (CheckBox) findViewById(R.id.checkbox_1);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);

        dialog=new CustomDialog(this,100,100,R.layout.dailog_loading,R.style.Theme_dialog, Gravity.CENTER);
        //屏幕点击无效
        dialog.setCancelable(false);

        //注册
        bt_register.setOnClickListener(this);
        //登录
        bt_login.setOnClickListener(this);
        //忘记密码
        forget_pwd.setOnClickListener(this);
        //头像
        mImageView.setImageResource(R.mipmap.ic_launcher);

        //默认没有点击保存
        boolean rememberPwd = ShareUtils.getBoolean(this, "rememberPwd", false);

        remember_pwd.setChecked(rememberPwd);

        //判断是否点击保存
        if (rememberPwd) {
            //读取用户密码
            String user = ShareUtils.getString(this, "user", "");
            String password = ShareUtils.getString(this, "password", "");
            ed_user.setText(user);
            ed_password.setText(password);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.bt_login:
                //获取输入框的值
                String name = ed_user.getText().toString().trim();
                String password = ed_password.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存isChecked的状态
        ShareUtils.putBoolean(this, "rememberPwd", remember_pwd.isChecked());

        //判断是否点击了保存
        if (remember_pwd.isChecked()) {
            //储存密码
            ShareUtils.putString(this, "user", ed_user.getText().toString().trim());
            ShareUtils.putString(this, "password", ed_password.getText().toString().trim());
        } else {
            //删除储存的信息
            ShareUtils.deleteShare(this, "user");
            ShareUtils.deleteShare(this, "password");
        }
    }
}
