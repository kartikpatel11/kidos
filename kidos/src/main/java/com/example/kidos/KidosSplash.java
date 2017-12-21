package com.example.kidos;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kidos.utils.KidosNetworkUtil;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class KidosSplash extends AppCompatActivity {
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_splash);
		  FacebookSdk.sdkInitialize(getApplicationContext());
		    AppEventsLogger.activateApp(this);
		TextView myTextView=(TextView)findViewById(R.id.txt_splashscreen);
		Typeface typeFace= Typeface.createFromAsset(getAssets(),"Digital-Serial-BoldItalic.ttf");
		myTextView.setTypeface(typeFace);
		
		
		AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
	        @Override
	        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
	        	System.out.println("oldToken="+oldAccessToken+",newToken="+newAccessToken);
	            updateWithToken(newAccessToken);
	        }
	    };
	    
	    updateWithToken(AccessToken.getCurrentAccessToken());

	
	}


	private void updateWithToken(AccessToken currentAccessToken) {

		System.out.println("currentAccessToken="+currentAccessToken);
	    if (currentAccessToken != null) {
	    	
	        new Handler().postDelayed(new Runnable() {

	            @Override
	            public void run() {
	            	
	            	Bundle bundle = new Bundle();
	            	
	        		bundle.putBoolean("AutoSearch", true);
	        		
	            	KidosNetworkUtil.isNetworkAvailable(KidosSplash.this,KidosSplash.this,KidosFindLocation.class,KidosNetworkUnavailable.class,true,bundle);
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
	    } else {
	        new Handler().postDelayed(new Runnable() {

	            @Override
	            public void run() {
	            	KidosNetworkUtil.isNetworkAvailable(KidosSplash.this,KidosSplash.this,KidosLoginActivity.class,KidosNetworkUnavailable.class,true,null);
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
	    }
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
}
