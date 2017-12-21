package com.example.kidos.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class KidosPermissionHandler {

	public static final int PERMS_COARSE_LOCATION = 1;
	public static final int PERMS_FINE_LOCATION = 2;

	
	public static void requestRuntimePermission(Activity thisActivity, String[] permissions, int[] requestId)
	{
		// Here, thisActivity is the current activity
		
		for(int index=0;index<permissions.length;index++)
		{
			if (Build.VERSION.SDK_INT >= 23) {
				
				if (ContextCompat.checkSelfPermission(thisActivity,permissions[index])!= PackageManager.PERMISSION_GRANTED) 
				{
					
				    // Should we show an explanation?
				    if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,permissions[index])) 
				    {
			
				        // Show an expanation to the user *asynchronously* -- don't block
				        // this thread waiting for the user's response! After the user
				        // sees the explanation, try again to request the permission.
				    	 ActivityCompat.requestPermissions(thisActivity,
					                new String[]{permissions[index]},
					                requestId[index]);
			
				    }
				
				    else 
				    {
			
				        // No explanation needed, we can request the permission.
			
				        ActivityCompat.requestPermissions(thisActivity,
				                new String[]{permissions[index]},
				                requestId[index]);
	
				    }
				}
			}
		}
		
	
	}
}
