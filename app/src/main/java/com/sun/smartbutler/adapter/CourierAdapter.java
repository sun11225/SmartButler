package com.sun.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.adapter
 * 文件名:   CourierAdapter
 * 创建者:   sun
 * 创建时间: 2019/6/13 0013 15:27
 * 描述:    快递查询适配器
 */
public class CourierAdapter extends BaseAdapter {

    private Context context;
    private List<CourierData> list;
    private CourierData courierData;

    public CourierAdapter(Context context, List<CourierData> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.courier_item, null);
//            view=View.inflate(context, R.layout.courier_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_datetime = (TextView) view.findViewById(R.id.tv_datetime);
            viewHolder.tv_remark = (TextView) view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) view.findViewById(R.id.tv_zone);
            //设置缓存
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置数据
        courierData = list.get(position);

        //显示数据
        viewHolder.tv_datetime.setText(courierData.getDateTime());
        viewHolder.tv_remark.setText(courierData.getRemark());
        viewHolder.tv_zone.setText(courierData.getZone());
        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;

    }
}
