package com.sun.smartbutler.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    //忘记密码
    private EditText forget_pwd;
    private Button btn_forget_pwd;
    //修改密码
    private EditText et_old_pwd;
    private EditText et_new_password;
    private EditText et_new_password_again;
    private Button btn_update_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    private void initView() {
        //忘记密码  邮件修改
        forget_pwd= (EditText) findViewById(R.id.et_email);
        btn_forget_pwd= (Button) findViewById(R.id.btn_forget_password);
        btn_forget_pwd.setOnClickListener(this);

        //修改密码
        et_old_pwd= (EditText) findViewById(R.id.et_old_pwd);
        et_new_password= (EditText) findViewById(R.id.et_new_password);
        et_new_password_again= (EditText) findViewById(R.id.et_new_password_again);
        btn_update_password= (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_password:
                //获取输入的邮箱地址
                final String email = forget_pwd.getText().toString().trim();
                if (!TextUtils.isEmpty(email)){
                    //发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮件发送成功，请到" + email + "邮箱进行密码重置操作",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(ForgetPasswordActivity.this,"邮箱不能为空!",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_update_password:
                //获取输入的值
                String oldPwd = et_old_pwd.getText().toString().trim();
                String newPwd = et_new_password.getText().toString().trim();
                String newPwdAgain = et_new_password_again.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(oldPwd)&&!TextUtils.isEmpty(newPwd)&&!TextUtils.isEmpty(newPwdAgain)){
                    //判断两次输入的密码是否一致
                    if (newPwd.equals(newPwdAgain)){
                        //修改密码
                        MyUser.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }else {
                    Toast.makeText(ForgetPasswordActivity.this,"输入框不能为空!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
