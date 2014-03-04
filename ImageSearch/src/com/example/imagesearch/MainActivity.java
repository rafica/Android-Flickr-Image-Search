package com.example.imagesearch;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
//062a6c0c49e4de1d78497d13a7dbb360
	EditText etSearch;
	Button btnSearch;
	private static final int CONTACT_PICKER_RESULT = 1001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etSearch = (EditText) findViewById(R.id.etSearch);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		//btnSearch.setOnClickListener(this);
		

        btnSearch.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    	Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                    	intent.putExtra("QUERY", etSearch.getText().toString());
                    	startActivity(intent);
                    }
                });

 }
		
	
	
	public void doLaunchContactPicker(View view) {
	    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
	            Contacts.CONTENT_URI);
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	        switch (requestCode) {
	        case CONTACT_PICKER_RESULT:
	            Cursor cursor = null;

	            try {
	                Uri result = data.getData();
	                String id = result.getLastPathSegment();

	                //Get Name
	                cursor = getContentResolver().query(result, null, null, null, null);
	                if (cursor.moveToFirst()) {
	                    etSearch.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
	                }
	            }
	             catch (Exception e) { }
	        
	    }
	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
