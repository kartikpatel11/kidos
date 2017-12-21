package com.example.kidos.utils;

import android.content.Context;

public class KidosApplication extends android.app.Application {

    private static KidosApplication instance;

    @Override
    public void onCreate()
    {
    	super.onCreate();
    	System.out.println("Overriding default fonts");
    	FontsOverride.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Roboto-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "Roboto-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "Roboto-Regular.ttf");
    }
    
    public KidosApplication() {
    	instance = this;
    }

    public static Context getContext() {
    	return instance;
    }

}
