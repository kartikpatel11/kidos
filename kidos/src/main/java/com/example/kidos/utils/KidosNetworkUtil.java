package com.example.kidos.utils;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;

import com.example.kidos.interfaces.IKidosFetchLocationWrapper;

public class KidosNetworkUtil {

	private static LocationManager locationManager;
	private static boolean gps_enabled,network_enabled;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; 
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 0; 
	
	
	
	public static void isNetworkAvailable(Activity activity,Context srcActivity, Class successActivity, Class failureActivity, boolean destroySrcActivity, Bundle bundle)
	{
		if(isNetworkAvailable(activity))
		{
		        // This method will be executed once the timer is over
             // Start your app main activity
             Intent i = new Intent(srcActivity, successActivity);
             if(bundle!=null)
             {
            	 i.putExtras(bundle); 
             }
             srcActivity.startActivity(i);
             
		 }
		 else
		 {
			 Intent i = new Intent(srcActivity, failureActivity);
             if(bundle!=null)
             {
            	 i.putExtras(bundle); 
             }
			 srcActivity.startActivity(i);
		     
		 }
		
        if(destroySrcActivity)
       	 activity.finish();
	}
	
	public static  boolean isNetworkAvailable(Activity activity) {
	    ConnectivityManager connectivityManager 
        = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
  return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
	
	public static Location initializeGeoLocation(LocationListener listener)
	{
		
		Location location=null;
		
		
		//Get current location
				locationManager = (LocationManager) KidosApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
			//	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
				
				 location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				 System.out.println("Got Last known location "+location);
				if(location==null)
				{
					location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					System.out.println("Got Last known location "+location);
					
				}
		       
				
				//Get GPS Status
				gps_enabled = locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
						// getting network status
						network_enabled = locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

						if (network_enabled) {
							
							System.out.println("Network Enabled... Using it");
							locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, listener,Looper.getMainLooper());
						}
						else if (gps_enabled) {
							System.out.println("GPS Enabled... Using it");
						locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, listener,Looper.getMainLooper());
						};  
				
				return location;

	}
	
	
	
	public static Address  getAddressFromLongLat(double longitude, double latitude)
	{
		List<Address> addresses;
		Geocoder geocoder=new Geocoder(KidosApplication.getContext(), Locale.getDefault()); 

		try
		{
			addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
			return addresses.get(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static String getAddressFromCurrentLocation(double longitude, double latitude) {
		Address currentAddr=getAddressFromLongLat(longitude,latitude);
		
		
		if(currentAddr!=null)
		{
			System.out.println("Current Address====="+currentAddr);
			if(currentAddr.getLocality()!=null)
			{
				return currentAddr.getLocality();
			}
			else if(currentAddr.getAddressLine(3)!=null)
			{
				if(currentAddr.getAddressLine(3).contains(","))
				{
					return currentAddr.getAddressLine(3).split(",")[1];
				}
				else
				{
					return currentAddr.getAddressLine(3);
				}
			}

		}
		return null;
	}
	
	
	private static class FindLocationAsync extends AsyncTask<Void,Void, Location>
	{
		IKidosFetchLocationWrapper context;
		public FindLocationAsync(IKidosFetchLocationWrapper locationListener)
		{
			context=locationListener;
		}
	
		@Override
		protected Location doInBackground(Void... args) {
			return initializeGeoLocation(context);
			
		}
		
		@Override
		protected void onPostExecute(Location data){
			super.onPostExecute(data);
			// Display based on error existence
			context.fetchLocationBack(data);
		}
		
	}
	
	public static void fetchCurrentLocation(IKidosFetchLocationWrapper context)
	{
		try {
			new FindLocationAsync(context).execute().get();
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
