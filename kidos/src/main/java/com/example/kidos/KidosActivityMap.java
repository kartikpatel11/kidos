package com.example.kidos;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class KidosActivityMap extends AppCompatActivity implements OnMapReadyCallback {

	  private GoogleMap map;
	  LatLng activityCoordinates;
	  String activityName ="";
	  MarkerOptions markerOptions;
	  Marker marker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_activity_map);
	
		Bundle bundle = getIntent().getExtras();
        double latitude = bundle.getDouble("latitude");
        double longitude = bundle.getDouble("longitude");
        activityName=bundle.getString("activityName");
		
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(activityName);
        actionBar.show();
        
		activityCoordinates=new LatLng(latitude,longitude);

		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (map == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			mapFrag.getMapAsync(this);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;
		setMarker(activityCoordinates);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_activity_map, menu);
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

	protected void setMarker(LatLng coordinates) {


		markerOptions = new MarkerOptions();
		markerOptions.draggable(true);
		markerOptions.position(coordinates);

		marker=map.addMarker(markerOptions);

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 18.0f));

	}
}
