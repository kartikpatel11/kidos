package com.example.kidos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kidos.adapter.KidosFindLocationAdapter;
import com.example.kidos.beans.KidosIndianStatesBean;
import com.example.kidos.interfaces.IKidosFetchLocationWrapper;
import com.example.kidos.interfaces.IKidosRestClientWrapper;
import com.example.kidos.utils.KidosConstants;
import com.example.kidos.utils.KidosNetworkUtil;
import com.example.kidos.utils.KidosPermissionHandler;
import com.example.kidos.utils.KidosRestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;


public class KidosFindLocation extends AppCompatActivity implements IKidosFetchLocationWrapper,IKidosRestClientWrapper{

	 Button searchButton;
	 ListView searchOut;
	 
	 private String[] permissions= new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
	 private int[] requestId= new int[] {KidosPermissionHandler.PERMS_FINE_LOCATION};

	private String listIndianCitiesURI = KidosConstants.LIST_INDIANCITIES_URI;

	 private static Location currentLoc;
	 private static String address= "Nearby Activities";
	 private ProgressDialog dialog;
	 private boolean autoSearchLoc=false;
	 private  View mCustomView;

	private List<KidosIndianStatesBean> indianCities;
	 
	 public static Location getCurrentLoc() {
			return currentLoc;
		}
	 
	public static String getAddress()
	{
			return address;
	}
	
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_find_location);
		KidosPermissionHandler.requestRuntimePermission(this,permissions,requestId);
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null)
			autoSearchLoc=bundle.getBoolean("AutoSearch");
		
		if(autoSearchLoc)
		{
			setProgressDialog();
			if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
			{
				fetchLocationRequest(this);
			}
			else if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
			{
				if(dialog.isShowing())
					dialog.dismiss();
			}
		}
		
		getSupportActionBar().setTitle("");
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		LayoutInflater mInflater = LayoutInflater.from(this);

	    mCustomView = mInflater.inflate(R.layout.layout_custom_kidos_standard_actionbar, null);
	   ((TextView)mCustomView.findViewById(R.id.txt_custom_standard_actionbar_header)).setText(R.string.title_activity_kidos_find_location);
	    
	    getSupportActionBar().setCustomView(mCustomView);
		
	  //Populate Data
	  		restRequest(KidosFindLocation.this, null, KidosConstants.GET, listIndianCitiesURI);
	  		
	//onclick for fetch currentloc button
	  		Button getCurrentLocBtn=(Button)findViewById(R.id.btn_searchloc);
	  		getCurrentLocBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					fetchLocationRequest(KidosFindLocation.this);
					
				}
			});
	  		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_find_location, menu);
		    MenuItem searchItem = menu.findItem(R.id.action_search);
		    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		    return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	 @SuppressLint("NewApi")
		@Override
		    public void onRequestPermissionsResult(int requestCode,
		                                           String permissions[],
		                                           int[] grantResults) 
		 	{
		        // Make sure it's our original READ_CONTACTS request
		        if (requestCode == KidosPermissionHandler.PERMS_COARSE_LOCATION) 
		        {
		            if (grantResults.length == 1 &&
		                    grantResults[0] == PackageManager.PERMISSION_DENIED) 
		            {
		                // showRationale = false if user clicks Never Ask Again, otherwise true
		                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.ACCESS_COARSE_LOCATION);

		                if (showRationale) {
		                   // do something here to handle degraded mode
		                }
		                
		            }
		        } 
		        else  if (requestCode == KidosPermissionHandler.PERMS_FINE_LOCATION) 
		        {
		            if (grantResults.length == 1 &&
		                    grantResults[0] == PackageManager.PERMISSION_DENIED) 
		            {
		                // showRationale = false if user clicks Never Ask Again, otherwise true
		                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.ACCESS_FINE_LOCATION);

		                if (showRationale) {
		                   // do something here to handle degraded mode
		                }
		                
		            }
		            else if (grantResults.length == 1 &&
		                    grantResults[0] == PackageManager.PERMISSION_GRANTED) 
		            {
		            	//Initialize location
		            	//Initialize Geo Location and set current address
		            	setProgressDialog();
		        		fetchLocationRequest(this);
		            }
		        } 
		        else 
		        {
		            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		        }
		    }


	private void openActivityCategoryPage(Location currentLoc, String address, boolean autoDetected) {
		
		Intent intent=new Intent(this,KidosActivityCategoryPage.class);
		this.startActivity(intent);
		if(autoDetected)
			this.finish();
		
	}
	
	private void setProgressDialog()
	{
		//ProgressDialog progressDialog;
		dialog = new ProgressDialog(KidosFindLocation.this);
		dialog.setCancelable(false);//can’t be cancelled on back press
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(true);
		dialog.setMessage("Please Wait..Fetching current location..");
		dialog.show();
       
	}

	@Override
	public void fetchLocationRequest(IKidosFetchLocationWrapper context) {
		KidosNetworkUtil.fetchCurrentLocation(context);
		
	}

	@Override
	public void fetchLocationBack(Location location) {
		currentLoc=location;
		
		/*if(currentLoc!=null)
		{
			address=KidosNetworkUtil.getAddressFromCurrentLocation(currentLoc.getLongitude(),currentLoc.getLatitude());
			
		}*/
		if(dialog!=null && dialog.isShowing())
		{
			dialog.dismiss();
		}
		
		if(currentLoc!=null && address!=null)
		{
			openActivityCategoryPage(currentLoc,address,true);
		}
		
	}

	@Override
	public void restRequest(IKidosRestClientWrapper context,
			Map<String, Object> args, String method, String url) {
		
		KidosRestClient.sendRequest(context,args,method,url);
		
	}

	@Override
	public void restCallBack(String restOutput) {
		System.out.println("In Rest Call back method of KidosFindLocation");
		Gson gson=new Gson();

		System.out.println("---> Indiancities"+restOutput);

		indianCities = gson.fromJson(restOutput, new TypeToken<List<KidosIndianStatesBean>>() {}.getType());


		final ListView activityAreasListView = (ListView) findViewById(R.id.lst_activityareas);

		KidosFindLocationAdapter locationAdapter = new KidosFindLocationAdapter(this, indianCities );

		activityAreasListView.setAdapter(locationAdapter);


		activityAreasListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?>  l, View v, int position, long id) {
                address= (activityAreasListView.getItemAtPosition(position).toString());
				currentLoc=null;
            	openActivityCategoryPage(null,address,false);
            }

        });

	}

	
}
