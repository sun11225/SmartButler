package com.sun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sun.smartbutler.R;
import com.sun.smartbutler.adapter.ChatLiatAdapter;
import com.sun.smartbutler.entity.ChatListData;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.fragment
 * 文件名:   ButlerFragment
 * 创建者:   sun
 * 创建时间: 2019/6/2 0002 10:11
 * 描述:    智能机器人小管家
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {

    private List<ChatListData> listDatas=new ArrayList<>();
    private ListView listView;
    private Button leftBtn,rightBtn;
    private ChatLiatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    //初始化
    private void findView(View view) {
        leftBtn= (Button) view.findViewById(R.id.btn_left);
        rightBtn= (Button) view.findViewById(R.id.btn_right);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        listView= (ListView) view.findViewById(R.id.mListView);

        //设置适配器
        adapter = new ChatLiatAdapter(getContext(),listDatas);
        listView.setAdapter(adapter);
        //默认
        addLeftItem("你好,我是小管家!");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                addLeftItem("你好！");
                break;
            case R.id.btn_right:
                addRightItem("你也好！");
                break;
        }
    }

    private void addRightItem(String text) {
        ChatListData chatListData=new ChatListData();
        chatListData.setType(ChatLiatAdapter.RIGHT_TYPE);
        chatListData.setText(text);
        listDatas.add(chatListData);
        //刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }

    private void addLeftItem(String text) {
        ChatListData chatListData=new ChatListData();
        chatListData.setType(ChatLiatAdapter.LEFT_TYPE);
        chatListData.setText(text);
        listDatas.add(chatListData);
        //刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }
}
