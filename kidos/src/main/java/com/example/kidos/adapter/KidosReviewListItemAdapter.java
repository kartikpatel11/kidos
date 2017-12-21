package com.example.kidos.adapter;


import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kidos.KidosActivityReviewDetail;
import com.example.kidos.R;
import com.example.kidos.beans.KidosActivityDetailsBean;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class KidosReviewListItemAdapter implements ListAdapter {

	private View view;
	private Context mContext;
	private KidosActivityDetailsBean data;
	private LayoutInflater layoutInflater;
	
	public KidosReviewListItemAdapter(View v, KidosActivityDetailsBean data_)
	{
		view=v;
		mContext=v.getContext();
		data=data_;
		layoutInflater = ( LayoutInflater )v.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data.getReviews()!=null) {
			return data.getReviews().length;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%Asking for Item at "+position);
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%Asking for ItemID at "+position);
		return position;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ReviewItemHolder reviewItemHolder=new ReviewItemHolder();
		
		System.out.println("%%%%%%called getView with position="+position);
		View reviewItemView=layoutInflater.inflate(R.layout.layout_kidos_activity_review_item, null);
		
		reviewItemHolder.userImg=(ImageView)reviewItemView.findViewById(R.id.img_profile_pic);
		reviewItemHolder.ratingBtn=(Button)reviewItemView.findViewById(R.id.btn_review_activity_rating);
		reviewItemHolder.userName=(TextView)reviewItemView.findViewById(R.id.txt_review_owner);
		reviewItemHolder.reviewtxt=(TextView)reviewItemView.findViewById(R.id.txt_review);
		
		//bind
		if(data.getReviews()[position].getReviewownerimg()!=null)
		{
			Picasso.with(mContext).load(data.getReviews()[position].getReviewownerimg()).into(reviewItemHolder.userImg);
		}
		
		reviewItemHolder.userName.setText(data.getReviews()[position].getReviewownername());
		reviewItemHolder.ratingBtn.setText(data.getReviews()[position].getUserrating());
		if(data.getReviews()[position].getReviewtxt()!=null)
		{
			if(data.getReviews()[position].getReviewtxt().length()>500)
			{
				reviewItemHolder.reviewtxt.setText(data.getReviews()[position].getReviewtxt().substring(0, 500)+"...");
			}
			else
			{
				reviewItemHolder.reviewtxt.setText(data.getReviews()[position].getReviewtxt());
			}
		}
		
		reviewItemView.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, "You Clicked "+data.getReviews()[position].getReviewownername() +"'s review", Toast.LENGTH_LONG).show();
            	Intent intent=new Intent(mContext,KidosActivityReviewDetail.class);
    			
            	intent.putExtra("review", new Gson().toJson(data.getReviews()[position]));
    			mContext.startActivity(intent);
            }
        });   
        return reviewItemView;
	}


	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		if(data.getReviews()==null)
			return 0;
		return data.getReviews().length;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public class ReviewItemHolder {
		ImageView userImg;
		TextView userName;
		Button ratingBtn;
		TextView reviewtxt;
	}

}
