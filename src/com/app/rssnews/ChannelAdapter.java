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
	private final Vector<String> m_list;
	
	
	public ChannelAdapter(Activity activity, Vector<String> list) {
		this.m_activity = activity;
		this.m_list = list;
	}
	
	@Override
	public int getCount() {
		return m_list.size();
	}

	@Override
	public Object getItem(int position) {
		m_list.elementAt(position);
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
         textView.setText(m_list.elementAt(position));
         ImageView imageView=(ImageView)view.findViewById(R.id.channelIcon);
         imageView.setImageResource(R.drawable.ic_launcher);
         return view;
	}

}
