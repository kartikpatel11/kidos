package com.example.kidos;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.kidos.adapter.KidosActivityDetailsAdapter;
import com.example.kidos.beans.KidosActivityDetailsBean;
import com.example.kidos.interfaces.IKidosRestClientWrapper;
import com.example.kidos.utils.KidosConstants;
import com.example.kidos.utils.KidosRestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class KidosActivityDetails extends AppCompatActivity implements IKidosRestClientWrapper{

	private List<KidosActivityDetailsBean> data=new ArrayList<KidosActivityDetailsBean>();
	private KidosActivityDetailsAdapter adapter;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		View decorView = getWindow().getDecorView();

		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		super.onCreate(savedInstanceState);
		
		
		getSupportActionBar().hide();

		setContentView(R.layout.activity_kidos_activity_details);
		
		Bundle bundle=this.getIntent().getExtras();
		
		//Get recyclerview
		recyclerView = (RecyclerView)findViewById(R.id.recyclerview_activity_details);
		mLayoutManager = new LinearLayoutManager(KidosActivityDetails.this);
		recyclerView.setLayoutManager(mLayoutManager);

		
		//Populate Data
		String url=KidosConstants.GET_ACTIVITY_DETAILS_URI+bundle.getLong("activityId");
		restRequest(KidosActivityDetails.this, null, KidosConstants.GET, url);

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);

	    View decorView = getWindow().getDecorView();
	    if (hasFocus) {
	        decorView.setSystemUiVisibility(
	            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_activity_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	/**
	 * Class used to switch to map window
	 * @author Kartik
	 *
	 */
	class LocateActivityImageClickListener implements View.OnClickListener
	{
		private double latitude;
		private double longitude;
		private String activityName;
		private Activity activity;
		public LocateActivityImageClickListener(Activity activity_, String activityName_, double latitude_,double longitude_) {
			activityName=activityName_;
			latitude=latitude_;
			longitude=longitude_;
			activity=activity_;
		}
		

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(activity,KidosActivityMap.class);
			Bundle bundle = new Bundle();
        	
			bundle.putDouble("latitude", latitude);
			bundle.putDouble("longitude", longitude);
			bundle.putString("activityName", activityName);
			intent.putExtras(bundle); 
			activity.startActivity(intent);
			
		}
		
	}

	

	@Override
	public void restRequest(IKidosRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		KidosRestClient.sendRequest(context,args,method,url);
			
	}

	@Override
	public void restCallBack(String restOutput) {
		Gson gson=new Gson();
		data=gson.fromJson(restOutput,new TypeToken<List<KidosActivityDetailsBean>>(){}.getType());
		System.out.println("ACTIVITY DETAIL DATA="+data);
		adapter = new KidosActivityDetailsAdapter(this,data);
		recyclerView.setAdapter(adapter);
	
		
	}
}
