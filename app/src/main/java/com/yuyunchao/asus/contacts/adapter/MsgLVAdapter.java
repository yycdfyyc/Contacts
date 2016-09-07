package com.yuyunchao.asus.contacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyunchao.asus.contacts.R;
import com.yuyunchao.asus.contacts.entity.PhoneMsg;

import java.util.ArrayList;

/**
 * Created by asus on 2016/8/27.
 */
public class MsgLVAdapter extends BaseAdapter{
    private ArrayList<PhoneMsg> list=new ArrayList<>();
    private LayoutInflater inflater;
    public MsgLVAdapter(Context context, ArrayList<PhoneMsg> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.onelistview_phonemsg, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_child_name);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_child_number);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_child_arrows);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_number.setText(list.get(position).getNumber());
        return convertView;


    }
    public static class ViewHolder{
        public TextView tv_name;
        public TextView tv_number;
        public ImageView iv;
    }
}
