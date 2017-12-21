package com.example.kidos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kidos.KidosMain;
import com.example.kidos.R;
import com.example.kidos.beans.KidosActivityCategoryGridItemBean;
import com.example.kidos.utils.KidosApplication;
import com.example.kidos.utils.KidosConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KidosActivityCategoryGridAdapter extends BaseAdapter{

	      	private final List<KidosActivityCategoryGridItemBean> data;
	      	private final Activity activity;
	   	
	        public KidosActivityCategoryGridAdapter(Activity activity_,List<KidosActivityCategoryGridItemBean> data_ ) {
	         
	        	activity=activity_;
	        	data=data_;

	        }
	 
	        @Override
	        public int getCount() {
	            // TODO Auto-generated method stub
	            return data.size();
	        }
	 
	        @Override
	        public KidosActivityCategoryGridItemBean getItem(int position) {
	            // TODO Auto-generated method stub
	            return data.get(position);
	        }
	 
	        @Override
	        public long getItemId(int position) {
	            // TODO Auto-generated method stub
	            return position;
	        }
	        
	        private View getWorkingView(final View convertView) {
	    		// The workingView is basically just the convertView re-used if possible
	    		// or inflated new if not possible
	    		View workingView = null;
	    		
	    		if(null == convertView) {
	    			final LayoutInflater inflater = (LayoutInflater)KidosApplication.getContext().getSystemService
	    		      (Context.LAYOUT_INFLATER_SERVICE);
	    			workingView = inflater.inflate(R.layout.layout_kidos_activity_category_grid_item, null);
		               
	    		} else {
	    			workingView = convertView;
	    		}
	    		
	    		return workingView;
	    	}
	        
	    	private ViewHolder getViewHolder(final View workingView) {
	    		// The viewHolder allows us to avoid re-looking up view references
	    		// Since views are recycled, these references will never change
	    		final Object tag = workingView.getTag();
	    		ViewHolder viewHolder = null;
	    		
	    		
	    		if(null == tag || !(tag instanceof ViewHolder)) {
	    			viewHolder = new ViewHolder();
	    			
	    			viewHolder.txtGridCategory = (TextView) workingView.findViewById(R.id.txt_grid_category);
	    			viewHolder.txtGridTotal= (TextView)workingView.findViewById(R.id.txt_grid_total);
	    			viewHolder.imgGridCat = (ImageView)workingView.findViewById(R.id.img_grid_category);
	            	
	    			workingView.setTag(viewHolder);
	    			
	    		} else {
	    			viewHolder = (ViewHolder) tag;
	    		}
	    		
	    		return viewHolder;
	    	}

	 
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            // TODO Auto-generated method stub
	         
	        	final View view = getWorkingView(convertView);
	    		final ViewHolder viewHolder = getViewHolder(view);
	    		final KidosActivityCategoryGridItemBean entry = getItem(position);
	    		
	    		// Setting the title view is straightforward
	    		viewHolder.txtGridCategory.setText(entry.getCatName());
	    		if(entry.getTotal()>0)
	    		{
	    			viewHolder.txtGridTotal.setText("("+entry.getTotal()+")");
	    			view.setOnClickListener(new KidosOnActivityTypeGridItemClickListener(activity, entry.get_id(),entry.getCatName()));
	 	    		
	    		}
	    		else
	    		{
	    			viewHolder.txtGridCategory.setTextColor(activity.getResources().getColor(R.color.disabledText));
	    		}
	    	
	    		if(entry.getCatImg()!=null)
                {
                	Picasso.with(activity).load(KidosConstants.NETWORK_URL+ KidosConstants.IMAGE_DOWNLOAD_URI+entry.getCatImg()).resize(50, 50).into(viewHolder.imgGridCat);
                }
             
	    	   return view;
	        }	
	        	

            
            private static class ViewHolder {
	    		public TextView txtGridCategory;
	    		public TextView txtGridTotal;
	    		public ImageView imgGridCat;
            }
             
	 
	            
	        
	        
	        private static class KidosOnActivityTypeGridItemClickListener implements View.OnClickListener{

				private String categoryId;
				private Activity activity;
				private String categoryName;
				
				public KidosOnActivityTypeGridItemClickListener(Activity activity_, String categoryId_, String categoryName_) {
					
				categoryId=categoryId_;
				categoryName=categoryName_;
				activity=activity_;
				}

				@Override
				public void onClick(View v)
				{
					Intent intent=new Intent(activity,KidosMain.class);
					Bundle bundle = new Bundle();
		        	
					//bundle.putDouble("longitude", KidosActivityCategoryPage.getCurrentLoc().getLongitude());
					//bundle.putDouble("latitude", KidosActivityCategoryPage.getCurrentLoc().getLatitude());
					bundle.putString("categoryId", categoryId);
					bundle.putString("categoryName", categoryName);
					
					intent.putExtras(bundle); 
					activity.startActivity(intent);
				
				}
			}
	        
	    		        
	        
	    	
	    	
	
}
