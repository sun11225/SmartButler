package com.sun.smartbutler.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.MyUser;
import com.sun.smartbutler.ui.LoginActivity;
import com.sun.smartbutler.utils.LogUtil;
import com.sun.smartbutler.utils.ShareUtils;
import com.sun.smartbutler.utils.UtilTools;
import com.sun.smartbutler.view.CustomDialog;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.fragment
 * 文件名:   UserFragment
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 10:12
 * 描述:    个人中心
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    public static final int CHOOSE_PHOTO = 2;
    private static final int TAKE_PHOTO = 1;
    private static final int RESULT_REQUEST_CODE = 3;
    private static final int RESULT_REQUEST_CODE_1 = 4;
    private Uri imageUri;

    private Button btn_exit_login;
    private TextView edit_user;

    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    private Button btn_update_ok;

    private CircleImageView profile_image;
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);

        //初始化view
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_exit_login = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_login.setOnClickListener(this);

        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);

        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        //圆形头像
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);


        //初始化一个dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.AppTheme_anim_style, Gravity.BOTTOM, 0);
//        dialog.setCancelable(false);

        //选择头像
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_camera.setOnClickListener(this);


        //输入框不可点击
        setEnabled(false);
        //设置具体的值
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        et_username.setText(user.getUsername());
        et_age.setText(String.valueOf(user.getAge()));
        et_desc.setText(user.getDesc());
        et_sex.setText(user.isSex() ? "男" : "女");


        //读取图片
        UtilTools.getImageToShare(getContext(), profile_image);
    }

    private void setEnabled(boolean is) {
        et_age.setEnabled(is);
        et_sex.setEnabled(is);
        et_username.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                MyUser.logOut();
                BmobUser user = MyUser.getCurrentUser();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                //设置输入框可以点击
                setEnabled(true);
                //设置确认按钮可见
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //获取输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                //判断是否为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)) {
                    //更新信息
                    MyUser user1 = BmobUser.getCurrentUser(MyUser.class);
                    user1.setAge(Integer.parseInt(age));
                    user1.setUsername(username);
                    //性别
                    if (sex.equals("男")) {
                        user1.setSex(true);
                    } else {
                        user1.setSex(false);
                    }
                    //简介
                    if (!TextUtils.isEmpty(desc)) {
                        user1.setDesc(desc);
                    } else {
                        user1.setDesc(getString(R.string.text_desc));
                    }
                    user1.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "修改失败！" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                //相机
                toCamera();
                break;
            case R.id.btn_picture:
//                //相册
//                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                } else {
//                   // toPicture();
//                }
                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    toPicture();
//                } else {
//                    Toast.makeText(getContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//        }
//    }

//    private void toPicture() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        //全部图片
//        intent.setType("image/*");
//        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
//        dialog.dismiss();
//
//    }

    private void toCamera() {

        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getContext().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(getContext(), "com.sun.smartbutler.fragment.FileProvider", outputImage);
        }

        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
        dialog.dismiss();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 0) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    setImageToView(imageUri);
                    break;
            }
        }
    }

    //设置图片
    private void setImageToView(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
            profile_image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilTools.putImageToShare(getContext(), profile_image);

    }
}
