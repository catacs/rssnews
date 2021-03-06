package com.app.rssnews.adapter;

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

public class FeedAdapter extends BaseAdapter {

	private final Activity m_activity;
	private Storage m_storage;
	
	
	public FeedAdapter(Activity activity, Storage db) {
		this.m_activity = activity;
		this.m_storage =  db;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = m_activity.getLayoutInflater();
         View view = inflater.inflate(R.layout.feed_item, null, true);
         
         TextView textView =(TextView)view.findViewById(R.id.channelName);
		 textView.setText("");
         ImageView imageView=(ImageView)view.findViewById(R.id.channelIcon);
         imageView.setImageResource(R.drawable.ic_launcher);
         return view;
	}

}
