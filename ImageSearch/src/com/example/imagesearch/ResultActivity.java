package com.example.imagesearch;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import com.example.imagesearch.FlickrParser.Entry;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ResultActivity extends Activity{

	String FlickrQuery_url = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
	String FlickrQuery_tag = "&tags=";
	String FlickrQuery_key = "&api_key=";
	String FlickrApiKey = "062a6c0c49e4de1d78497d13a7dbb360";

	
	// Apply your Flickr API:
	// www.flickr.com/services/apps/create/apply/?


	 FlickrParser xmlParser = new FlickrParser();
     List<com.example.imagesearch.FlickrParser.Entry> entries = null;
	
     TextView view;
      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Log.d("resultactivity","in oncreate");
		
		
		
		 view = (TextView) findViewById(R.id.test);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String value = extras.getString("QUERY");
			view.setText(value);

			//searchQueryResult = (TextView) findViewById(R.id.queryresult);

			DownloadWebPageTask task = new DownloadWebPageTask();

			task.execute(new String[] { value });
			

		}

	}





	public class DownloadWebPageTask extends AsyncTask<String, Void, List<Entry>> {

		//TextView searchQueryResult;

		@Override
		protected List<Entry> doInBackground(String... q) {

			try {
				InputStream result = loadXmlFromNetwork(q[0]);
				return xmlParser.parse(result);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			
		}

		
		@Override
		protected void onPostExecute(List<Entry> entries) {

			Log.d("onpostexecute","in on post execute");

	 
	        // Click event for single list row
	       /* list.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	 
	            }
	        });*/
		/*	try {
				
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	      //  ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this,R.layout.list_item, entries);
	       // listView.setAdapter(adapter);
		}
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}




	// Uploads XML from stackoverflow.com, parses it, and combines it with
    // HTML markup. Returns HTML string.
    private InputStream loadXmlFromNetwork(String query) throws XmlPullParserException, IOException {
    	
    	Log.d("loadxmlfromnetwork", "into loadxmlfromnw");
    
        InputStream output = null;
       

      
        
            output = downloadUrl(query);
 
            return output;
      
                
        // Makes sure that the InputStream is closed after the app is
        // finished using it.
        

        // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
        // Each Entry object represents a single post in the XML feed.
        // This section processes the entries list to combine each entry with HTML markup.
        // Each entry is displayed in the UI as a link that optionally includes
        // a text summary.
     
             
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    
    private InputStream downloadUrl(String query) throws IOException {
    	 InputStream in = null;

 		Log.d("downloadurl", query);
 		String qString = FlickrQuery_url + FlickrQuery_key + FlickrApiKey
 				+  FlickrQuery_tag + query +"&extras=date_taken,owner_name,description";

 		Log.d("downloadurl", qString);
 		HttpClient httpClient = new DefaultHttpClient();
 		HttpGet httpGet = new HttpGet(qString);

 		try {
 			HttpResponse httpResponse = httpClient.execute(httpGet);
 			in = httpResponse.getEntity().getContent();

 		} catch (ClientProtocolException e) {
 			// TODO Auto-generated catch block
 			Log.d("download url", "clientprotocolexception");
 			e.printStackTrace();
 		} catch (IOException e) {
 			Log.d("download url", "ioexception");
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		
 		return in;
    	
    	

    }

}



