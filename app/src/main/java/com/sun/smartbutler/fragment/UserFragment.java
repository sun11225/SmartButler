package com.sun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.smartbutler.R;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.fragment
 * 文件名:   UserFragment
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 10:12
 * 描述:    TODO
 */
public class UserFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,null);
        return view;
    }
}
