package com.example.kidos.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kidos.R;
import com.example.kidos.beans.KidosActivityImagesBean;
import com.squareup.picasso.Picasso;

public class KidosActivityImageSliderAdapter extends PagerAdapter {
    Context mContext;
    KidosActivityImagesBean[] mResources;
    LayoutInflater inflator;

    KidosActivityImageSliderAdapter(Context context, KidosActivityImagesBean[] resources) {
        this.mContext = context;
        this.mResources = resources;
   }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((View) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	System.out.println("called instantiate item of KidosActivityImageSliderAdapter for position="+position+","+mResources[position].getImgurl());
        
    	
    	LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        
        View viewItem = inflater.inflate(R.layout.layout_kidos_activity_image, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.img_kidos_activity_image);
        //imageView.setImageResource(imageId[position]);
        Picasso.with(mContext).load(mResources[position].getImgurl()).into(imageView);
        ((ViewPager)container).addView(viewItem);
         
        return viewItem;
    	
    	
    	/*ImageView mImageView = new ImageView(mContext);
        
        
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext).load(mResources[position].getImgurl()).into(mImageView);
	       
        // mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
        
        
        return mImageView;*/
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((View) obj);
    }
}