package com.example.kidos;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kidos.adapter.KidosActivityPhotosFullScreenAdapter;

public class KidosActivityFullScreenPhotoView extends Activity {

	ViewPager mPager;
	KidosActivityPhotosFullScreenAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_activity_full_screen_photo_view);
		
		Bundle b=this.getIntent().getExtras();
		
		ArrayList<String>  kidosFullScreenImgViewAdapterData=b.getStringArrayList("images");
		
		mAdapter = new KidosActivityPhotosFullScreenAdapter(this,kidosFullScreenImgViewAdapterData);

        mPager = (ViewPager) findViewById(R.id.imgviewpager);
        
        mPager.setAdapter(mAdapter);
        
     // get the item that we should be showing from the intent
        int mCurrentPage = b.getInt("currentImgIndex");

        // show the item the user picked
        mPager.setCurrentItem(mCurrentPage);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_activity_full_screen_photo_view,
				menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
