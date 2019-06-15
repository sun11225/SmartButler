package com.sun.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.adapter
 * 文件名:   ChatLiatAdapter
 * 创建者:   sun
 * 创建时间: 2019/6/14 0014 8:51
 * 描述:    机器数据适配器
 */
public class ChatLiatAdapter extends BaseAdapter {

    private Context context;
    private List<ChatListData> listDatas;
    private ChatListData chatListData;
    public static final int LEFT_TYPE = 1;
    public static final int RIGHT_TYPE = 2;


    public ChatLiatAdapter(Context context, List<ChatListData> listDatas) {
        this.context = context;
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolderLeft viewHolderLeft = null;
        ViewHolderRight viewHolderRight = null;

        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case LEFT_TYPE:
                    viewHolderLeft = new ViewHolderLeft();
                    view = LayoutInflater.from(context).inflate(R.layout.left_item, null);
                    viewHolderLeft.textView = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeft);
                    break;
                case RIGHT_TYPE:
                    viewHolderRight = new ViewHolderRight();
                    view = LayoutInflater.from(context).inflate(R.layout.right_item, null);
                    viewHolderRight.textView = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRight);
                    break;
            }
        } else {
            view = convertView;
            switch (type) {
                case LEFT_TYPE:
                    viewHolderLeft = (ViewHolderLeft) view.getTag();
                    break;
                case RIGHT_TYPE:
                    viewHolderRight = (ViewHolderRight) view.getTag();
                    break;
            }
        }


        //赋值
        ChatListData chatListData = listDatas.get(position);
        switch (type) {
            case LEFT_TYPE:
                viewHolderLeft.textView.setText(chatListData.getText());
                break;
            case RIGHT_TYPE:
                viewHolderRight.textView.setText(chatListData.getText());
                break;
        }
        return view;
    }

    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        chatListData = listDatas.get(position);
        int type = chatListData.getType();
        return type;
    }

    //需要创建的视图数量
    @Override
    public int getViewTypeCount() {
        return 3;//一般是list.size()+1
    }

    //左边的文本
    private class ViewHolderLeft {
        TextView textView;
    }

    //右边的文本
    private class ViewHolderRight {
        TextView textView;
    }
}
