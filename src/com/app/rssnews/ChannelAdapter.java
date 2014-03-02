package com.app.rssnews;

import java.util.Vector;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChannelAdapter extends BaseAdapter {

	private final Activity m_activity;
	private Storage m_storage;
	
	
	public ChannelAdapter(Activity activity, Storage db) {
		this.m_activity = activity;
		this.m_storage =  db;
	}
	
	@Override
	public int getCount() {
		return m_storage.getChannelsList().size();
	}

	@Override
	public Object getItem(int position) {
		m_storage.getChannelsList().elementAt(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = m_activity.getLayoutInflater();
         View view = inflater.inflate(R.layout.channel_view, null, true);
         
         TextView textView =(TextView)view.findViewById(R.id.channelName);
         textView.setText(m_storage.getChannelsList().elementAt(position));
         ImageView imageView=(ImageView)view.findViewById(R.id.channelIcon);
         imageView.setImageResource(R.drawable.ic_launcher);
         return view;
	}
	
	public void addItem(String name) {
		m_storage.addChannel(name);
		notifyDataSetChanged();
		
	}
}
