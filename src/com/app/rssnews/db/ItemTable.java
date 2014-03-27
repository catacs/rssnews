package com.app.rssnews.db;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ItemTable {
    public static final String TABLE_NAME="feed";
    public static final String KEY_TITLE="name";
    public static final String KEY_ID="_id";
    
    // Database creation SQL statement
	private static final String TABLE_CREATE = "create table "
	      + TABLE_NAME
	      + "("
	      + KEY_ID + " integer primary key autoincrement, "
	      + KEY_TITLE + " text not null" 
	      + ");";

	public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(ChannelTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    onCreate(database);
	}
}
