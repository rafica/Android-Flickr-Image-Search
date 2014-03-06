package com.example.imagesearch;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.example.imagesearch.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import com.example.imagesearch.FlickrParser.Entry;
import com.example.imagesearch.LazyAdapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ResultActivity extends Activity implements OnItemClickListener{

	String FlickrQuery_url = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
	String FlickrQuery_tag = "&tags=";
	String FlickrQuery_key = "&api_key=";
	String FlickrApiKey = "062a6c0c49e4de1d78497d13a7dbb360";
	
	//public List<Entry> returnedResult;
	
	// Apply your Flickr API:
	// www.flickr.com/services/apps/create/apply/?


	 FlickrParser xmlParser = new FlickrParser();
     List<com.example.imagesearch.FlickrParser.Entry> imageList = null;
	

     ListView list;
	 LazyAdapter adapter;
      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Bundle extras = getIntent().getExtras();
		list=(ListView)findViewById(R.id.list);
		list.setOnItemClickListener(this);
        
        
		if (extras != null) {
			String value = extras.getString("QUERY");
			DownloadWebPageTask task = new DownloadWebPageTask();

			task.execute(new String[] { value });

		}

	}
	

	public class DownloadWebPageTask extends AsyncTask<String, Void, List<Entry>> {

	    
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
		protected void onPostExecute(final List<Entry> entries) {

			Log.d("onpostexecute","in on post execute");
			if(entries!=null){
				
				
				imageList = entries;
				adapter=new LazyAdapter(ResultActivity.this, entries);
				list.setAdapter(adapter);
			}else{
				Log.d("onpostexecute", "imagelist is null");
			}
	        
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(imageList.get(pos).getUrl().replace("_m.jpg", "_c.jpg")));
		startActivity(intent);
	}

}



