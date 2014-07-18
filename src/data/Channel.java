package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Channel {
	private ArrayList<Item> items;	
	private String title;
	private String link;
	private String description;
	private String pubDate;
	
	public Channel() {
		items =  new ArrayList<Item>();
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void addItem(Item item) {
		//this.items.add(item);
	}
	
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
}
