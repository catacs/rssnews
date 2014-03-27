package com.app.rssnews.activity;

import com.app.rssnews.R;
import com.app.rssnews.db.ChannelTable;
import com.app.rssnews.provider.ChannelContentProvider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddChannelActivity extends Activity {
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_channel);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
               
        final Button button = (Button) findViewById(R.id.saveUrlButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText mEdit   = (EditText)findViewById(R.id.newChannel);

                ContentValues values = new ContentValues();
                values.put(ChannelTable.KEY_NAME, mEdit.getText().toString());
                values.put(ChannelTable.KEY_URL, mEdit.getText().toString());
                getContentResolver().insert(ChannelContentProvider.CONTENT_URI, values);
                finish();
            }
        });
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
