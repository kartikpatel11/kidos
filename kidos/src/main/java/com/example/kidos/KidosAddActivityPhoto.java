package com.example.kidos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.kidos.utils.KidosUtil;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class KidosAddActivityPhoto extends AppCompatActivity {

	private static final int REQUEST_CAMERA = 0;
	private static final int SELECT_FILE = 1;
    private String userChoosenTask;
    private ImageView activityImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_add_activity_photo);
		activityImg=(ImageView)findViewById(R.id.img_kidos_add_image);
		selectImage();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
	    
		getMenuInflater().inflate(R.menu.kidos_add_activity_photo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		 switch (item.getItemId()) {
	        case R.id.action_camera:
	            // User chose the "Settings" item, show the app settings UI...
	            return true;
	        case R.id.action_gallery:
	        	return true;
		 	}
	            return super.onOptionsItemSelected(item);
				
		 }


	
	private void selectImage() {
		  final CharSequence[] items = { "Take Photo", "Choose from Library",
		        "Cancel" };

		  AlertDialog.Builder builder = new AlertDialog.Builder(KidosAddActivityPhoto.this);
		  builder.setTitle("Add Photo!");
		  builder.setItems(items, new DialogInterface.OnClickListener() {


			@Override
		     public void onClick(DialogInterface dialog, int item) {
		        boolean result=KidosUtil.checkPermission(KidosAddActivityPhoto.this);

		        if (items[item].equals("Take Photo")) {
		           userChoosenTask="Take Photo";
		           if(result)
		              cameraIntent();

		        } else if (items[item].equals("Choose from Library")) {
		           userChoosenTask="Choose from Library";
		           if(result)
		              galleryIntent();

		        } else if (items[item].equals("Cancel")) {
					userChoosenTask="Cancel";
		           dialog.dismiss();
		        }
		     }
		  });
		  builder.show();
		}
	
	private void cameraIntent()
	{
	  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	  startActivityForResult(intent, REQUEST_CAMERA);
	}
	
	private void galleryIntent()
	{
	  Intent intent = new Intent();
	  intent.setType("image/*");
	  intent.setAction(Intent.ACTION_GET_CONTENT);//
	  startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	  switch (requestCode) {
	     case KidosUtil.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
	        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	           if(userChoosenTask.equals("Take Photo"))
	              cameraIntent();
	           else if(userChoosenTask.equals("Choose from Library"))
	              galleryIntent();
	        } else {
	           //code for deny
	        }
	        break;
	  }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  if (resultCode == Activity.RESULT_OK) {
	     if (requestCode == SELECT_FILE)
	        onSelectFromGalleryResult(data);
	     else if (requestCode == REQUEST_CAMERA)
	        onCaptureImageResult(data);
	  }
	}
	
	@SuppressWarnings("deprecation")
	private void onSelectFromGalleryResult(Intent data) {
	  Bitmap bm=null;
	  if (data != null) {
	        Picasso.with(this).load(data.getData()).fit().into(activityImg);
	  }
	}
	
	private void onCaptureImageResult(Intent data) {
		  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
		  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		  thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
		  File destination = new File(Environment.getExternalStorageDirectory(),
		        System.currentTimeMillis() + ".jpg");
		  FileOutputStream fo;
		  try {
		     destination.createNewFile();
		     fo = new FileOutputStream(destination);
		     fo.write(bytes.toByteArray());
		     fo.close();
		  } catch (FileNotFoundException e) {
		     e.printStackTrace();
		  } catch (IOException e) {
		     e.printStackTrace();
		  }
	       Picasso.with(this).load(destination).fit().into(activityImg);
		}


}
