package com.sun.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sun.smartbutler.R;
import com.sun.smartbutler.entity.GrilData;
import com.sun.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * 项目名:   SmartButler
 * 包名:     com.sun.smartbutler.adapter
 * 文件名:   GirlAdapter
 * 创建者:   sun
 * 创建时间: 2019/6/14 0014 16:05
 * 描述:    妹子适配器
 */
public class GirlAdapter extends BaseAdapter {

    private Context context;
    private List<GrilData> list;
    private GrilData grilData;

    private WindowManager wm;
    //屏幕宽
    private int width;

    public GirlAdapter(Context context,List<GrilData> list) {
        this.context=context;
        this.list=list;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        ViewHolder viewHolder=null;
        if (convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.image_girl,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) view.findViewById(R.id.girl_image);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        grilData =list.get(position);
        //解析图片
        String url = grilData.getPicUrl();
//        PicassoUtils.loadImageView(context,url,viewHolder.imageView);
        PicassoUtils.loadImageViewSize(context,url,width/2,500,viewHolder.imageView);

        return view;
    }

    private class ViewHolder{
        ImageView imageView;
    }
}
