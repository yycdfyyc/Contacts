package com.yuyunchao.asus.contacts.adapter;

import com.yuyunchao.asus.contacts.R;
import com.yuyunchao.asus.contacts.entity.PhoneType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TypeLVAdapter extends BaseAdapter{
	LayoutInflater inflater;
	ArrayList<PhoneType> mlist;
	public TypeLVAdapter(Context context, ArrayList<PhoneType> list){
		inflater = LayoutInflater.from(context);
		mlist = list;
	}
	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.onelistview_phonetype, null);
			holder = new ViewHolder();
			holder.tv = (TextView)convertView.findViewById(R.id.lv_main_type);
			holder.iv = (ImageView) convertView.findViewById(R.id.lv_main_arrows);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tv.setText(mlist.get(position).getPhoneType());
		return convertView;
	}
	public static class ViewHolder{
		public TextView tv;
		public ImageView iv;
	}
}
