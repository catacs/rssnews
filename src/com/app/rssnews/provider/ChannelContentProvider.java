package com.app.rssnews.provider;

import java.util.Arrays;
import java.util.HashSet;

import com.app.rssnews.db.ChannelTable;
import com.app.rssnews.db.Storage;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ChannelContentProvider extends ContentProvider {
    private Storage database;
    
 // used for the UriMacher
    private static final int CHANNELS = 10;
    private static final int CHANNEL_ID = 20;
    
    private static final String AUTHORITY = "com.app.rssnews.channel.contentprovider";
    private static final String BASE_PATH = "channels";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
    		+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/channels";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/channel";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	  static {
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH, CHANNELS);
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CHANNEL_ID);
	}

    @Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
        case CHANNELS:
          rowsDeleted = sqlDB.delete(ChannelTable.TABLE_NAME, selection,
              selectionArgs);
          break;
        case CHANNEL_ID:
          String id = uri.getLastPathSegment();
          if (TextUtils.isEmpty(selection)) {
            rowsDeleted = sqlDB.delete(ChannelTable.TABLE_NAME,
            		ChannelTable.KEY_ID + "=" + id, 
            		null);
          } else {
            rowsDeleted = sqlDB.delete(ChannelTable.TABLE_NAME,
            		ChannelTable.KEY_ID + "=" + id 
            		+ " and " + selection,
            		selectionArgs);
          }
          break;
        default:
          throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    long id = 0;
	    switch (uriType) {
	    case CHANNELS:
	      id = sqlDB.insert(ChannelTable.TABLE_NAME, null, values);
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public boolean onCreate() {
		database = new Storage(getContext());
	    return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

	    // Uisng SQLiteQueryBuilder instead of query() method
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

	    // check if the caller has requested a column which does not exists
	    checkColumns(projection);

	    // Set the table
	    queryBuilder.setTables(ChannelTable.TABLE_NAME);

	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case CHANNELS:
	      break;
	    case CHANNEL_ID:
	      // adding the ID to the original query
	      queryBuilder.appendWhere(ChannelTable.KEY_ID + "="
	          + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    SQLiteDatabase db = database.getWritableDatabase();
	    Cursor cursor = queryBuilder.query(db, projection, selection,
	        selectionArgs, null, null, sortOrder);
	    // make sure that potential listeners are getting notified
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);

	    return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		 int uriType = sURIMatcher.match(uri);
		    SQLiteDatabase sqlDB = database.getWritableDatabase();
		    int rowsUpdated = 0;
		    switch (uriType) {
		    case CHANNELS:
		      rowsUpdated = sqlDB.update(ChannelTable.TABLE_NAME, 
		          values, 
		          selection,
		          selectionArgs);
		      break;
		    case CHANNEL_ID:
		      String id = uri.getLastPathSegment();
		      if (TextUtils.isEmpty(selection)) {
		        rowsUpdated = sqlDB.update(ChannelTable.TABLE_NAME, 
		            values,
		            ChannelTable.KEY_ID + "=" + id, 
		            null);
		      } else {
		        rowsUpdated = sqlDB.update(ChannelTable.TABLE_NAME, 
		            values,
		            ChannelTable.KEY_ID + "=" + id 
		            + " and " 
		            + selection,
		            selectionArgs);
		      }
		      break;
		    default:
		      throw new IllegalArgumentException("Unknown URI: " + uri);
		    }
		    getContext().getContentResolver().notifyChange(uri, null);
		    return rowsUpdated;
	}
	
	private void checkColumns(String[] projection) {
	    String[] available = { ChannelTable.KEY_NAME,
	    		 			   ChannelTable.KEY_URL,
	    		 			   ChannelTable.KEY_ID,};
	    if (projection != null) {
	      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
	      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
	      // check if all columns which are requested are available
	      if (!availableColumns.containsAll(requestedColumns)) {
	        throw new IllegalArgumentException("Unknown columns in projection");
	      }
	    }
	  }

}
