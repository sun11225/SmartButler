package com.sun.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.WeChatData;
import com.sun.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.adapter
 * 文件名:   WeChatAdapter
 * 创建者:   sun
 * 创建时间: 2019/6/14 0014 10:57
 * 描述:    微信精选数据适配器
 */
public class WeChatAdapter extends BaseAdapter {

    private Context context;
    private List<WeChatData> list;
    private WeChatData weChatData;

    public WeChatAdapter(Context context, List<WeChatData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.wechat_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textTitle = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.textSource = (TextView) view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置显示
        weChatData = list.get(position);
        //  viewHolder.imageView.setImageBitmap();
        viewHolder.textTitle.setText(weChatData.getTitle());
        viewHolder.textSource.setText(weChatData.getSource());

        //加载图片
        // PicassoUtils.loadImageViewHolder(context,weChatData.getImgUrl(),viewHolder.imageView);
        return view;
    }

    private class ViewHolder {
        TextView textTitle;
        TextView textSource;
    }

}
