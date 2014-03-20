package com.app.rssnews.listener;

import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;


public class DrawerItemClickListener implements ListView.OnItemClickListener {
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }
	
	private void selectItem(int position) {
	}

	public void setTitle(CharSequence title) {
	}

}
