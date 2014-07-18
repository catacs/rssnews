package com.app.rssnews.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import data.Channel;
import data.Item;

import android.os.AsyncTask;
import android.provider.Telephony.Mms.Part;
import android.util.Log;

public class FetchService extends AsyncTask<String, Void, Channel> {

	public FetchService() {
	}

	protected void onPreExecute() {
		Log.e("FetchService", "Pre execution");
	}

	@Override
	protected Channel doInBackground(String... params) {
		String urlString = params[0];
		Channel channel = null;
		try {
			 channel = loadXmlFromNetwork(urlString);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return channel;
	}

	private Channel loadXmlFromNetwork(String urlString)
			throws XmlPullParserException, IOException {
		InputStream stream = null;
		Channel channel = null;
		try {
			stream = downloadUrl(urlString);
			channel = parse(stream);
		} finally {
			if (stream != null)
				stream.close();
		}
		return channel;
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		return conn.getInputStream();
	}

	protected Channel parse(InputStream is) {
		XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(is, null);
			return parseXML(parser);

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Channel parseXML(XmlPullParser parser) {
		Channel channel = new Channel();
		try {
		int eventType = parser.getEventType();
		boolean done = false;
		Item item = new Item();
		while (eventType != XmlPullParser.END_DOCUMENT && !done) {
			String name = null;
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				channel = new Channel();
				break;
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if (name.equalsIgnoreCase("item")) {
					item = new Item();
				} else {
					if (parser.getDepth() == 3) {
						if (name.equalsIgnoreCase("link")) {
							channel.setLink(parser.nextText());
						} else if (name.equalsIgnoreCase("description")) {
							channel.setDescription(parser.nextText());
						} else if (name.equalsIgnoreCase("pub_date")) {
							channel.setPubDate(parser.nextText());
						} else if (name.equalsIgnoreCase("title")) {
							channel.setTitle(parser.nextText());
						}
					} else {
						if (name.equalsIgnoreCase("link")) {
							item.setLink(parser.nextText());
						} else if (name.equalsIgnoreCase("description")) {
							item.setDescription(parser.nextText());
						} else if (name.equalsIgnoreCase("pub_date")) {
							item.setPubDate(parser.nextText());
						} else if (name.equalsIgnoreCase("title")) {
							item.setTitle(parser.nextText());
						}
					}
				}
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();
				if (name.equalsIgnoreCase("item")) {
					channel.addItem(item);
				} else if (name.equalsIgnoreCase("channel")) {
					done = true;
				}
				break;
			}
			eventType = parser.next();
		}
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
		Log.d("debug", "End parsing");
		return channel;
	}
	
    @Override
    protected void onPostExecute(Channel result) {
       super.onPostExecute(result);
    }
}
