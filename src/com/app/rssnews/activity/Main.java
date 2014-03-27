package com.app.rssnews.activity;

import com.app.rssnews.R;
import com.app.rssnews.R.array;
import com.app.rssnews.R.drawable;
import com.app.rssnews.R.id;
import com.app.rssnews.R.layout;
import com.app.rssnews.R.menu;
import com.app.rssnews.R.string;
import com.app.rssnews.adapter.ChannelAdapter;
import com.app.rssnews.db.ChannelTable;
import com.app.rssnews.db.Storage;
import com.app.rssnews.listener.DrawerItemClickListener;
import com.app.rssnews.provider.ChannelContentProvider;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main extends ListActivity implements
	LoaderManager.LoaderCallbacks<Cursor> {
	
	private String[] mMenuList;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.getListView().setDividerHeight(2);
	    fillData();
	    registerForContextMenu(getListView());
	    
		mTitle = mDrawerTitle = getTitle();
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
	
		mMenuList = getResources().getStringArray(R.array.menu_array);
		
		// Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        		R.layout.drawer_list_item, mMenuList));
        // Set the list's click listener
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			
            // Called when a drawer has settled in a completely closed state. 
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            // Called when a drawer has settled in a completely open state. 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
   }
	

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { ChannelTable.KEY_NAME };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.channelName};

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.channel_item, null, from,
            to, 0);
        setListAdapter(adapter);
	}


	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_addchannel:
            Intent intent = new Intent(this, AddChannelActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }

    }
    
    public void onAddNewChannel(String channel){
    	
    }
	
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	}
	
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_addchannel).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
	    String[] projection = { ChannelTable.KEY_ID, ChannelTable.KEY_NAME };
	    CursorLoader cursorLoader = new CursorLoader(this,
	        ChannelContentProvider.CONTENT_URI, projection, null, null, null);
	    return cursorLoader;
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
	    adapter.swapCursor(null);
	}

}
