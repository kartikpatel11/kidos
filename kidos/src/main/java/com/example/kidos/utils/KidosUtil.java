package com.example.kidos.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class KidosUtil {

	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final String TITLE_ERROR = "Error";
    public static final String TITLE_SUCCESS = "Success";
    public static final String NEUTRAL_DIALOG = "Neutral";
    public static final String YESNO_DIALOG = "YesNo";
    public static final String NONE_DIALOG = "None";

	// Returns the URI path to the Bitmap displayed in specified ImageView
	public static Uri getLocalBitmapUri(ImageView imageView) {
	    // Extract Bitmap from ImageView drawable
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp = null;
	    if (drawable instanceof BitmapDrawable){
	       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else {
	       return null;
	    }
	    // Store image to default external storage directory
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
	            Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
	        file.getParentFile().mkdirs();
	        FileOutputStream out = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}
	
   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
   public static boolean checkPermission(final Context context)
   {
       int currentAPIVersion = Build.VERSION.SDK_INT;
       if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
       {
           if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
               if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                   AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                   alertBuilder.setCancelable(true);
                   alertBuilder.setTitle("Permission necessary");
                   alertBuilder.setMessage("External storage permission is necessary");
                   alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                       @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                       public void onClick(DialogInterface dialog, int which) {
                           ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                       }
                   });
                   AlertDialog alert = alertBuilder.create();
                   alert.show();

               } else {
                   ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
               }
               return false;
           } else {
               return true;
           }
       } else {
           return true;
       }
   }


    public static void createDialog(Activity activity, String titleStr, String msgStr, String dialogBoxType, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(activity);

        //title
        TextView title = new TextView(activity);
        title.setText(titleStr);
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(20, 20, 20, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        popupBuilder.setCustomTitle(title);

        //body
        TextView myMsg = new TextView(activity);
        myMsg.setText(msgStr);
        myMsg.setTextColor(Color.BLACK);
        myMsg.setPadding(20,20,20,20);
        myMsg.setTextSize(15f);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        popupBuilder.setView(myMsg);

        if(NEUTRAL_DIALOG.equals(dialogBoxType)) {
            popupBuilder.setNeutralButton("OK", listener);
        }
        else if(YESNO_DIALOG.equals(dialogBoxType)) {
            popupBuilder.setPositiveButton("Yes",listener).setNegativeButton("No",listener);
        }
        else  {
            popupBuilder.setCancelable(true);
        }

        AlertDialog alert = popupBuilder.create();
        alert.show();

        if(NEUTRAL_DIALOG.equals(dialogBoxType))
        {
            final Button neutralButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
            LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams) neutralButton.getLayoutParams();
            neutralButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
            neutralButton.setLayoutParams(neutralButtonLL);

        }

    }
		
}
