package com.example.kidos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.kidos.adapter.KidosMainScreenItemDataRowAdapter;
import com.example.kidos.beans.KidosMainScreenDAO;
import com.example.kidos.interfaces.IKidosRestClientWrapper;
import com.example.kidos.utils.KidosConstants;
import com.example.kidos.utils.KidosRestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This page refers to page that has nearby activities for a category
 * Bundle Params
 * 	longitude
 * 	latitude
 * 	categoryId: Object Id of category
 * 	categoryName
 * @author Kartik
 *
 */
@SuppressLint("NewApi")
public class KidosMain extends AppCompatActivity implements OnQueryTextListener,IKidosRestClientWrapper{

	private static RecyclerView kidosMainRecyclerView;
	List<KidosMainScreenDAO> data = new ArrayList<KidosMainScreenDAO>();
	private Context context;
	private  View mCustomView;
	private KidosMainScreenItemDataRowAdapter adapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_main);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		LayoutInflater mInflater = LayoutInflater.from(this);
		
		 mCustomView = mInflater.inflate(R.layout.layout_custom_kidosmain_actionbar, null);
			
		    getSupportActionBar().setCustomView(mCustomView);
			
			
			Bundle bundle=this.getIntent().getExtras();
			
			TextView hiddenCatId=(TextView)findViewById(R.id.txt_kidosmain_hidden_catid);
			hiddenCatId.setText(bundle.getString("categoryId"));
			
			InitializeCustomActionbar(bundle);

		//context=getApplicationContext();
			String url=null;
			if(KidosFindLocation.getCurrentLoc()!=null)
			{

				url=KidosConstants.FIND_NEARBY_ACTIVITIES_BY_TYPE_URI+
						KidosFindLocation.getCurrentLoc().getLongitude()+"/"+
						KidosFindLocation.getCurrentLoc().getLatitude()+"/"+
						bundle.getString("categoryId");
			}
			else
			{
				url=KidosConstants.FIND_NEARBY_ACTIVITIES_BY_AREA_AND_TYPE_URI+
						KidosFindLocation.getAddress()+"/"+
						bundle.getString("categoryId");
			}
		kidosMainRecyclerView = (RecyclerView) findViewById(R.id.kidosrecyclerView);
		kidosMainRecyclerView.setLayoutManager(new LinearLayoutManager(context));
		kidosMainRecyclerView.setItemAnimator(new DefaultItemAnimator());
		
		
		//populateData
		restRequest(KidosMain.this, null, KidosConstants.GET, url);
		
	}
	
	private void InitializeCustomActionbar(Bundle bundle)
	{
		TextView locationTxt=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.txt_custom_kidosmain_actionbar_location);
		TextView activityTxt=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.txt_custom_kidosmain_actionbar_categoryname);
		
		activityTxt.setText(bundle.getString("categoryName"));
		locationTxt.setText(KidosFindLocation.getAddress());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_main, menu);
		
		 final MenuItem item = menu.findItem(R.id.action_search);
		 final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
		   searchView.setOnQueryTextListener(this);
		   searchView.setOnSearchClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("Disabling customview");
            	getSupportActionBar().setDisplayShowCustomEnabled(false);
            
			}
		});
		   
		   searchView.setOnCloseListener(new SearchView.OnCloseListener() {
			
			@Override
			public boolean onClose() {
				System.out.println("Enabling customview");
            	getSupportActionBar().setDisplayShowCustomEnabled(true);
            return false;
			}
		});
		   		    
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public boolean onQueryTextChange(String query) {
	    // Here is where we are going to implement our filter logic
		System.out.println("onQueryTextChange");
	    return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
	    return true;
	}

	@Override
	public void restCallBack(String restOutput) {
		
		System.out.println("In Rest Call back method of KidosMain");
		Gson gson=new Gson();
		data=gson.fromJson(restOutput,new TypeToken<List<KidosMainScreenDAO>>(){}.getType());
	
		populateViewAdapter(data);
		
	}
	
	private void populateViewAdapter(final List<KidosMainScreenDAO> data)
	{
		adapter = new KidosMainScreenItemDataRowAdapter(this,data);
		kidosMainRecyclerView.setAdapter(adapter);
	}

	@Override
	public void restRequest(IKidosRestClientWrapper context, Map<String, Object> args,
			String method, String url) {
		KidosRestClient.sendRequest(context,args,method,url);
	}


}
