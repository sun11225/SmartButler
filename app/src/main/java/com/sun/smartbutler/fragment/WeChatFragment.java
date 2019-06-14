package com.sun.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.sun.smartbutler.R;
import com.sun.smartbutler.adapter.ChatLiatAdapter;
import com.sun.smartbutler.adapter.WeChatAdapter;
import com.sun.smartbutler.entity.ChatListData;
import com.sun.smartbutler.entity.WeChatData;
import com.sun.smartbutler.ui.WebViewActivity;
import com.sun.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.fragment
 * 文件名:   WeChatFragment
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 10:12
 * 描述:    微信精选
 */
public class WeChatFragment extends Fragment {

    private ListView listView;
    private List<WeChatData> list = new ArrayList<>();
    private List<String> listTitle = new ArrayList<>();
    private List<String> listUrl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;
    }

    //初始化
    private void findView(View view) {

        listView = (ListView) view.findViewById(R.id.weChat_ListView);

        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(getContext(), t, Toast.LENGTH_SHORT).show();
                parseWeChatJson(t);
            }
        });

        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title",listTitle.get(position));
                intent.putExtra("url",listUrl.get(position));
                startActivity(intent);
            }
        });
    }

    private void parseWeChatJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray jsonArray = result.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                WeChatData weChatData = new WeChatData();
                String title = object.getString("title");
                listTitle.add(title);
                String url = object.getString("url");
                listUrl.add(url);

                weChatData.setTitle(object.getString("title"));
                weChatData.setSource(object.getString("source"));
                weChatData.setImgUrl(object.getString("firstImg"));
                list.add(weChatData);
            }
            WeChatAdapter adapter = new WeChatAdapter(getContext(), list);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
