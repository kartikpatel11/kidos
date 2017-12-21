package com.example.kidos.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kidos.KidosActivityDetails;
import com.example.kidos.R;
import com.example.kidos.beans.KidosMainScreenDAO;
import com.example.kidos.utils.KidosConstants;
import com.squareup.picasso.Picasso;

import java.util.List;




public class KidosMainScreenItemDataRowAdapter extends RecyclerView.Adapter<KidosMainScreenItemDataRowAdapter.KidosMainScreenItemDataHolder> {
	
	private  List<KidosMainScreenDAO> kidosMainScreenItemList;
	private  Activity activity;

	public KidosMainScreenItemDataRowAdapter(Activity activity_, List<KidosMainScreenDAO> nearByActivityList) {

		kidosMainScreenItemList=nearByActivityList;
		activity=activity_;
    }
	
	
	 public  class KidosMainScreenItemDataHolder extends RecyclerView.ViewHolder{
	        
	        
			public TextView txtActivityId;
	        public TextView txtActivityName;
	        public TextView txtActivityArea;
	        public ImageView imgActivityProfile;
	        public Button btnActivityRating;
	        private View itemLayoutView;
	       
	        public KidosMainScreenItemDataHolder(View itemLayoutView_) {
	            super(itemLayoutView_);
	        	this.itemLayoutView=itemLayoutView_;
	        	txtActivityId = (TextView) itemLayoutView.findViewById(R.id.txt_hidden_activity_id);
		        txtActivityName = (TextView) itemLayoutView.findViewById(R.id.txt_activity_header);
		        txtActivityArea = (TextView) itemLayoutView.findViewById(R.id.txt_activity_area);
	            imgActivityProfile=(ImageView)itemLayoutView.findViewById(R.id.img_activity_profilepicture);
	            btnActivityRating = (Button)itemLayoutView.findViewById(R.id.btn_activity_rating);
	           }
	        
	 }
	
		@Override
		public int getItemCount() {
			// TODO Auto-generated method stub
			if(kidosMainScreenItemList!=null)
			{
				return kidosMainScreenItemList.size();
			}
			else
			{
				return 0;
			}
		}
		
		
		
		@Override
		public KidosMainScreenItemDataRowAdapter.KidosMainScreenItemDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			KidosMainScreenItemDataHolder viewHolder =null;
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			
			viewHolder = new KidosMainScreenItemDataHolder(inflater.inflate(R.layout.layout_kidos_main_screen, parent,false));
			
			return viewHolder;
		}


		@Override
		public void onBindViewHolder(KidosMainScreenItemDataHolder viewHolder, int position) {
			viewHolder.txtActivityName.setText(kidosMainScreenItemList.get(position).getName());
			viewHolder.txtActivityId.setText(String.valueOf(kidosMainScreenItemList.get(position).getActivityId()));
			if(kidosMainScreenItemList.get(position).getImages()!=null && kidosMainScreenItemList.get(position).getImages().length>0)
            {
      			Picasso.with(activity).load(kidosMainScreenItemList.get(position).getImages()[0].getImgurl()).into(viewHolder.imgActivityProfile);
            }
            else
			{
				System.out.println("Image data not available so defaulting image.");
				Picasso.with(activity).load(KidosConstants.DEFAULT_ACTIVITY_IMAGE).into(viewHolder.imgActivityProfile);
			}
			
			viewHolder.btnActivityRating.setText(String.valueOf(kidosMainScreenItemList.get(position).getRating()));
			viewHolder.txtActivityArea.setText(kidosMainScreenItemList.get(position).getArea());
			
			viewHolder.imgActivityProfile.setOnClickListener(new KidosOnActivityClickListener(activity,kidosMainScreenItemList.get(position)));

		}
		
		/**
		 * Object of class will be called when user clicks on activityprofile image on main page
		 * @author Kartik
		 *
		 */
		private static class KidosOnActivityClickListener implements OnClickListener{

			private KidosMainScreenDAO dao;
			private Activity activity;
			
			public KidosOnActivityClickListener(Activity activity_, KidosMainScreenDAO dao_) {
				
			dao=dao_;
			activity=activity_;
			}

			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(activity,KidosActivityDetails.class);
				Bundle bundle = new Bundle();
	        	
				bundle.putLong("activityId", dao.getActivityId());
				bundle.putString("activityName", dao.getName());
				
				intent.putExtras(bundle); 
				activity.startActivity(intent);
			
			}
		}
}
