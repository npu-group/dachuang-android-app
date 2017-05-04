package com.itau.jingdong.itemList;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itau.jingdong.R;

import java.util.List;

/**
 * Created by CAPTON on 2016/11/25.
 */
// 适配器，等待被JsonThread调用

public class JsonAdapter extends BaseAdapter {

    List<Student> students;
    Context context;
    LayoutInflater inflater;
    Handler handler;

    public JsonAdapter(Context context, Handler handler, List<Student> students) {
        this.handler=handler;
        this.context=context;
        this.students=students;
        inflater= LayoutInflater.from(context);//从MainActivity中上下文对象中获取LayoutInflater；所以说这个context,和handler对象很重要，贯穿整项目
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //重写getView方法，即设置ListView每一项的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.activity_item_list,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);//设置tag
        }else {
            holder= (ViewHolder) convertView.getTag(); //获取tag
        }
        System.out.println(String.valueOf(students.get(position).itemInfo));//测试数据是否正常
        holder.itemInfo.setText(String.valueOf(students.get(position).itemInfo));
        holder.itemTitle.setText(students.get(position).itemTitle);
        System.out.println(students.get(position).itemTitle);
        new ImageThread(students.get(position).url, handler,holder.url).start();//开启新线程下载图片并在新线程中更新UI，所以要传递handler对象
        return convertView;
    }

    //用于暂时保存视图对象
    class ViewHolder{
        public TextView itemTitle;
        public TextView itemInfo;
        public ImageView url;

        public ViewHolder(View view){
            itemTitle= (TextView) view.findViewById(R.id.itemTitle);
            itemInfo= (TextView) view.findViewById(R.id.itemInfo);
            url= (ImageView) view.findViewById(R.id.url);
        }
    }
}