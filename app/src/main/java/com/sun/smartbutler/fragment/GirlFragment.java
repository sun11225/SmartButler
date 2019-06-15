package com.sun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.sun.smartbutler.R;
import com.sun.smartbutler.adapter.GirlAdapter;
import com.sun.smartbutler.entity.GrilData;
import com.sun.smartbutler.utils.LogUtil;
import com.sun.smartbutler.utils.PicassoUtils;
import com.sun.smartbutler.utils.StaticClass;
import com.sun.smartbutler.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.fragment
 * 文件名:   GirlFragment
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 10:12
 * 描述:    美女社区
 */
public class GirlFragment extends Fragment {

    private GridView gridView;
    //数据
    private List<GrilData> mList = new ArrayList<>();
    //适配器
    private GirlAdapter mAdapter;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //自定义的提示框
    private CustomDialog dialog;
    //支持缩放的图片
    private PhotoView photoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        findView(view);
        return view;
    }


    private void findView(View view) {

        gridView = (GridView) view.findViewById(R.id.mGridView);
//        http://bbs.voc.com.cn/topic-8917093-1-1.html
        String url = "http://api.tianapi.com/meinv/?&key=" + StaticClass.TIANXING_KEY + "&num=20";
        dialog = new CustomDialog(getContext(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.item_girl_o, R.style.Theme_dialog,
                Gravity.CENTER);

        photoView = (PhotoView) dialog.findViewById(R.id.iv_image);


        //解析
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                LogUtil.d(t);
//                Toast.makeText(getContext(),t,Toast.LENGTH_SHORT).show();
                parseJsonImage(t);
            }
        });


        //监听点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImageView(getContext(), mListUrl.get(position), photoView);
                dialog.show();

            }
        });

    }

    //解析json 获取图片
    private void parseJsonImage(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String url = json.getString("picUrl");
                mListUrl.add(url);

                String url1 = "http://api.tianapi.com/txapi/htmlpic/?&key=" + StaticClass.TIANXING_KEY + "&url=" + url + "&format=jpg";
                RxVolley.get(url1, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        parseJsonImage(t);
                    }
                });

                GrilData data = new GrilData();
                data.setPicUrl(url);
                mList.add(data);
            }
            mAdapter = new GirlAdapter(getContext(), mList);
            //设置适配器
            gridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void parseJsonImage1(String t){
//        try {
//            JSONObject jsonObject = new JSONObject(t);
//            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject json = jsonArray.getJSONObject(i);
//                String url = json.getString("picUrl");
//                mListUrl.add(url);
//
//                GrilData data = new GrilData();
//                data.setPicUrl(url);
//                mList.add(data);
//            }
//            mAdapter = new GirlAdapter(getContext(), mList);
//            //设置适配器
//            gridView.setAdapter(mAdapter);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
