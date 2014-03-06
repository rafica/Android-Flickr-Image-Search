package com.example.imagesearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class FlickrParser {
	   //private static final String ns = null;

	    // We don't use namespaces

	   List<Entry> entries = new ArrayList<Entry>();
   		private String text;
   		private String date;
   		private String owner;
   		
   		private String photoId;
   		private String secret;
   		private String farmId;
   		private String serverId;
   		
   		private Entry entry;
	    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
	        try {
	      
	            XmlPullParser parser = Xml.newPullParser();
	            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in, null);
	            parser.nextTag();
	            return readFeed(parser);
	        } finally {
	            in.close();
	        }
	    }
	    

	    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
	        


	    	int eventType = parser.getEventType();
	    	while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("photo")) {
                        // create a new instance of employee
                    	
                    	date = parser.getAttributeValue(null, "datetaken");
                    	owner = parser.getAttributeValue(null, "ownername");
                    	
                   		photoId = parser.getAttributeValue(null, "id");
                   		secret = parser.getAttributeValue(null, "secret");
                   		farmId =  parser.getAttributeValue(null, "farm");
                   		serverId =  parser.getAttributeValue(null, "server");
                   		
                        entry = new Entry();
                        entry.setDate(date);
                        entry.setOwner(owner);
                        entry.setUrl("http://farm"+farmId+".staticflickr.com/"+serverId+"/"+ photoId +"_"+secret+"_m.jpg");

                    	
                    }
                    break;
 
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
 
                case XmlPullParser.END_TAG:
                	
                    if (tagname.equalsIgnoreCase("photo")) {
                        // add employee object to list
                        entries.add(entry);
                    } else if (tagname.equalsIgnoreCase("description")) {
                    	entry.setDescription(text);
                    } 
                    break;
 
                default:
                    break;
                }
                eventType = parser.next();
            }
	        return entries;
	    }

	    // This class represents a single entry (post) in the XML feed.
	    // It includes the data members "title," "link," and "summary."
	    public static class Entry implements Serializable{
	        
	    	private static final long serialVersionUID = 1L;
	        private String description;
	        private String date;
	        private String owner;
	        private String url;
	        
	        public String getDescription(){
	        	return description;
	        }
	        
	        public void setDescription(String description) {
	            this.description = description;
	        }
	        
	        public String getDate(){
	        	return date;
	        }
	        public void setDate(String date){
	        	this.date = date;
	        }
	        
	        public String getOwner(){
	        	return owner;
	        }
	        public void setOwner(String owner){
	        	this.owner = owner;
	        }

	        public String getUrl(){
	        	return url;
	        }
	        public void setUrl(String url){
	        	this.url = url;
	        }
	    }


}

