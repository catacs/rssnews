package com.app.rssnews.activity;

import java.util.concurrent.ExecutionException;

import com.app.rssnews.R;
import com.app.rssnews.db.ChannelTable;
import com.app.rssnews.provider.ChannelContentProvider;
import com.app.rssnews.service.FetchService;

import data.Channel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddChannelActivity extends Activity {
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_channel);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
               
        final Button button = (Button) findViewById(R.id.saveUrlButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	FetchService fs = new FetchService();
            	EditText mEdit   = (EditText)findViewById(R.id.newChannel);
                if(isNetworkAvailable()){
                	fs = new FetchService();
                	fs.execute(mEdit.getText().toString());      
                }else{
                    Toast.makeText(getApplicationContext(), "Network Unavailable!", Toast.LENGTH_LONG).show();
                }

                if( fs != null) {
                	Channel c = null;
                	try {
						c = fs.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	if (c!=null) {
                		ContentValues values = new ContentValues();
                		values.put(ChannelTable.KEY_NAME, c.getTitle());
                		values.put(ChannelTable.KEY_URL, c.getLink());
                		getContentResolver().insert(ChannelContentProvider.CONTENT_URI, values);
                	}
                }
                finish();
            }
        });
    }
	
	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}

		return isAvailable;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		   switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
		    case android.R.id.home:
		        Intent upIntent = NavUtils.getParentActivityIntent(this);
		        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
		            // This activity is NOT part of this app's task, so create a new task
		            // when navigating up, with a synthesized back stack.
		            TaskStackBuilder.create(this)
		                    // Add all of this activity's parents to the back stack
		                    .addNextIntentWithParentStack(upIntent)
		                    // Navigate up to the closest parent
		                    .startActivities();
		        } else {
		            // This activity is part of this app's task, so simply
		            // navigate up to the logical parent activity.
		            NavUtils.navigateUpTo(this, upIntent);
		        }
		        return true;
		    }
		    return super.onOptionsItemSelected(item);
	}
}
