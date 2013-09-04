package com.connectedio.advocateio;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

import java.net.URL;
import java.net.MalformedURLException;

import com.connectedio.advocateio.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    protected void onStart() {
		//processLink();
        super.onStart();
        // The activity is about to become visible.
    }
	
    @Override
    protected void onResume() {
		processLink();
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    
    protected void processLink() {
    	String baseURL = "http://advocate.io/bookmarklet";
    	Intent receivedIntent = getIntent();
    	String subject = receivedIntent.getStringExtra(Intent.EXTRA_SUBJECT);
    	String body = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
    	
    	String [] parts = body.split("\\s");
    	
    	String to_url_string = "http://foo.bar";
    	
    	// Attempt to convert each item into an URL.   
        for( String item : parts ) try {
            URL to_url = new URL(item);
            to_url_string = to_url.toString();
        } catch (MalformedURLException e) {
            // If there was an URL that was not it!...
            System.out.print( item + " " );
        }
        
        Uri.Builder b = Uri.parse(baseURL).buildUpon();
        b.appendQueryParameter("url", to_url_string);
        b.appendQueryParameter("title", subject);
        b.appendQueryParameter("mobile", "1");
        
        String url_string = b.build().toString(); 
    	
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_string));
		startActivity(browserIntent);
    }
    
    @Override
    protected void onPause() {
    	finish();
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    
    @Override
    protected void onStop() {
    	finish();
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

}
