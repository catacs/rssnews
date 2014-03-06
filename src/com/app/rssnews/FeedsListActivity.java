package com.app.rssnews;

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
