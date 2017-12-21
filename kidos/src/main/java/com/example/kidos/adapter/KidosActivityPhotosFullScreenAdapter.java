package com.example.kidos.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kidos.R;
import com.squareup.picasso.Picasso;



public class KidosActivityPhotosFullScreenAdapter extends PagerAdapter {
	 
		Context mContext;
	    LayoutInflater mLayoutInflater;
	    List<String> mResources;
	 
	    public KidosActivityPhotosFullScreenAdapter(Context context,List<String> imgUri) {
	        mContext = context;
	        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        mResources=imgUri;
	    }
	 
	    @Override
	    public int getCount() {
	        return mResources.size();
	    }
	 
	    @Override
	    public boolean isViewFromObject(View view, Object object) {
	        return view == ((LinearLayout) object);
	    }
	 
	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	        View itemView = mLayoutInflater.inflate(R.layout.layout_kidos_fullscreen_activity_photo_item, container, false);
	 
	        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_fullscreenview);
	        //imageView.setImageURI(Uri.parse(mResources.get(position)));
	        //VotesUpUtil.setImageUsingImageUri(imageView, Uri.parse(mResources.get(position)));
	        Picasso.with(mContext).load(mResources.get(position)).into(imageView);
	        container.addView(itemView);
	 
	        return itemView;
	    }
	 
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        container.removeView((LinearLayout) object);
	    }
	    
	  
	   
	}
