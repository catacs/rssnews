package com.app.rssnews.adapter;

import java.util.Vector;

import com.app.rssnews.R;
import com.app.rssnews.R.drawable;
import com.app.rssnews.R.id;
import com.app.rssnews.R.layout;
import com.app.rssnews.db.Storage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends BaseAdapter {

	private final Activity m_activity;
	
	public ChannelAdapter(Activity activity, Storage db) {
		this.m_activity = activity;
	}
	
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = m_activity.getLayoutInflater();
         View view = inflater.inflate(R.layout.channel_item, null, true);
         
         TextView textView =(TextView)view.findViewById(R.id.channelName);
         textView.setText("");
         ImageView imageView=(ImageView)view.findViewById(R.id.channelIcon);
         imageView.setImageResource(R.drawable.ic_launcher);
         return view;
	}
	
	public void addItem(String name) {
		notifyDataSetChanged();
		
	}
}
