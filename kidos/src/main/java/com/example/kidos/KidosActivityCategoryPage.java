package com.example.kidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.example.kidos.adapter.KidosActivityCategoryGridAdapter;
import com.example.kidos.beans.KidosActivityCategoryGridItemBean;
import com.example.kidos.interfaces.IKidosRestClientWrapper;
import com.example.kidos.utils.KidosConstants;
import com.example.kidos.utils.KidosRestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KidosActivityCategoryPage extends AppCompatActivity implements IKidosRestClientWrapper {

	GridView grid;
	List<KidosActivityCategoryGridItemBean> data=new ArrayList<KidosActivityCategoryGridItemBean>();
	private KidosActivityCategoryGridAdapter adapter ;
	private IKidosRestClientWrapper context;
	
	//private Location currentLoc;
	//private boolean doesLocationSetOnce=false;
	private  View mCustomView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_kidos_activity_category_page);
		
		Bundle bundle=this.getIntent().getExtras();
		//set currentLoc object
		//setCurrentLoc((Location)bundle.getParcelable("currentLoc"));
		
		getSupportActionBar().setTitle("");
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		LayoutInflater mInflater = LayoutInflater.from(this);

	    mCustomView = mInflater.inflate(R.layout.layout_custom_actionbar, null);
		
	    getSupportActionBar().setCustomView(mCustomView);
		
		getSupportActionBar().getCustomView().setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View view) {
		    	Intent intent=new Intent(KidosActivityCategoryPage.this,KidosFindLocation.class);
				startActivity(intent);
				KidosActivityCategoryPage.this.finish();
		    }
		});

		TextView locationTxt=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.txt_custom_actionbar_location);
		//set location
		locationTxt.setText(KidosFindLocation.getAddress());

		
		//Populate Data
		String url=null;
		if(KidosFindLocation.getCurrentLoc()!=null)
		{
			 url=KidosConstants.FIND_NEARBY_ACTIVITIES_TYPE_URI+KidosFindLocation.getCurrentLoc().getLongitude()+"/"+KidosFindLocation.getCurrentLoc().getLatitude();
		}
		else
		{
			 url=KidosConstants.FIND_NEARBY_ACTIVITIES_TYPE_BY_AREA_URI+KidosFindLocation.getAddress();
		}
		restRequest(KidosActivityCategoryPage.this, null, KidosConstants.GET, url);
		  
		
	}
	
	private void populateViewAdapter(final List<KidosActivityCategoryGridItemBean> data)
	{
	    adapter = new KidosActivityCategoryGridAdapter(this,data);
        grid=(GridView)findViewById(R.id.grid_activitycategory);
                grid.setAdapter(adapter);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_activity_category_page, menu);
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

	@Override
	public void restCallBack(String restOutput) {
		System.out.println("In Rest Call back method of KidosActivityCategoryPage");
		Gson gson=new Gson();
		data=gson.fromJson(restOutput,new TypeToken<List<KidosActivityCategoryGridItemBean>>(){}.getType());
		populateViewAdapter(data);
	}
	
	
	/*
	@Override
	public void onLocationChanged(Location location) {
		
		System.out.println("onLocationChanged: longitude="+location.getLongitude()+",latitude="+location.getLatitude());
		currentLoc=location;
		//Call for reverGeo lookup only if location is not set
		if(!doesLocationSetOnce)
		{
			TextView locationTxt=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.txt_custom_actionbar_location);
			
			KidosNetworkUtil.setAddressFromCurrentLocation(currentLoc.getLongitude(), currentLoc.getLatitude(),locationTxt);
			doesLocationSetOnce=true;
		}
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		System.out.println("onStatusChanged:"+status);
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		System.out.println("onProviderEnabled:Enabled");
	}

	@Override
	public void onProviderDisabled(String provider) {
		System.out.println("onProviderDisabled:Disabled");
			
	}
*/
	
	@Override
	public void restRequest(IKidosRestClientWrapper context, Map<String, Object> args,
			String method, String url) {
		
		KidosRestClient.sendRequest(context,args,method,url);
		
	}
	
	

}
