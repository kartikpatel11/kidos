package com.example.kidos.interfaces;

import android.location.Location;
import android.location.LocationListener;

public interface IKidosFetchLocationWrapper extends LocationListener{

	public void fetchLocationRequest(IKidosFetchLocationWrapper context);
	public void fetchLocationBack(Location location);

}
