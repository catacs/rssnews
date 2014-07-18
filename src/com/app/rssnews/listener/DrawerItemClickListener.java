package com.app.rssnews.listener;

import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;


public class DrawerItemClickListener implements ListView.OnItemClickListener {
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }
	
	private void selectItem(int position) {
		Log.d("selection", String.valueOf(position));
	}

	public void setTitle(CharSequence title) {
	}

}
