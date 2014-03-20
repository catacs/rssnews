package com.app.rssnews.activity;

import com.app.rssnews.R;
import com.app.rssnews.R.layout;
import com.app.rssnews.adapter.FeedAdapter;
import com.app.rssnews.db.Storage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class FeedsListActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feeds_list);
		
		ListView feedsListView = (ListView) findViewById(R.layout.feeds_list);
		feedsListView.setAdapter(new FeedAdapter(this, new Storage(getApplicationContext())));
	}
}
