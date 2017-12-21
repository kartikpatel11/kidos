package com.example.kidos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kidos.beans.KidosActivityReviewBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class KidosActivityReviewDetail extends AppCompatActivity  {

	private KidosActivityReviewBean data=new KidosActivityReviewBean();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidos_activity_review_detail);


		String review = null;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			review = extras.getString("review");
		}
		if(review!=null)
		{
			data = new Gson().fromJson(review, KidosActivityReviewBean.class);
			populateData(data);
		}
		
		
		//Populate Data
	//			String url=KidosConstants.GET_ACTIVITY_REVIEW_DETAIL_URI+bundle.getLong("reviewId");
		//		restRequest(KidosActivityReviewDetail.this, null, KidosConstants.GET, url);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kidos_activity_review_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_kidos_activity_review_detail, container,
					false);
			return rootView;
		}
	}

	private void populateData(KidosActivityReviewBean data)
	{
		ImageView reviewOwnerPic = (ImageView)findViewById(R.id.img_review_detail_profile_pic);
		TextView reviewOwner = (TextView)findViewById(R.id.txt_review_detail_owner);
		Button reviewScore = (Button)findViewById(R.id.btn_review_detail_activity_rating);
		TextView reviewTxt = (TextView)findViewById(R.id.txt_review_detail);
		
		if(reviewOwnerPic!=null)
			Picasso.with(this).load(data.getReviewownerimg()).into(reviewOwnerPic);
		reviewScore.setText(data.getUserrating());
		reviewOwner.setText(data.getReviewownername());
		reviewTxt.setText(data.getReviewtxt());
	}

}
