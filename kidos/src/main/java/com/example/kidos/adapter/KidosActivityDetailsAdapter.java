package com.example.kidos.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kidos.KidosActivityMap;
import com.example.kidos.KidosAddActivityPhoto;
import com.example.kidos.KidosContact;
import com.example.kidos.KidosWriteActivityReview;
import com.example.kidos.R;
import com.example.kidos.beans.KidosActivityBatchesBean;
import com.example.kidos.beans.KidosActivityContactsBean;
import com.example.kidos.beans.KidosActivityDetailsBean;
import com.example.kidos.beans.KidosActivityImagesBean;
import com.example.kidos.utils.KidosApplication;
import com.example.kidos.utils.KidosConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KidosActivityDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
	
    public static final int ACTIVITY_HEADER = 0;
    public static final int ACTIVITY_ADDRESS = 1;
    public static final int ACTIVITY_SCHEDULE = 2;
    public static final int ACTIVITY_DESCRIPTION = 3;
    public static final int ACTIVITY_IMAGELIST = 4;
    public static final int ACTIVITY_REVIEW = 5;
    public static final int INDX_OF_RECORD= 0;
    public static final int NO_OF_CARDS= 6;

	public static final int MY_PERMISSIONS_CALL_PHONE = 111;

    private PackageManager pm;
    private List<KidosActivityDetailsBean> data;
    private Activity activity;
	
    public KidosActivityDetailsAdapter(Activity activity_, List<KidosActivityDetailsBean> data_)
    {
    	this.activity=activity_;
    	this.data=data_;
    	this.pm=this.activity.getPackageManager();
    }
    
	@Override
	public int getItemCount() {
		//System.out.println("getItemCount="+data.size());
		return NO_OF_CARDS;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {
		if (viewHolder.getItemViewType() == ACTIVITY_HEADER) {	
			((ActivityHeaderViewHolder)viewHolder).bindViewHolder(INDX_OF_RECORD);
		}
		else if(viewHolder.getItemViewType() == ACTIVITY_ADDRESS) {
			((ActivityAddressViewHolder)viewHolder).bindViewHolder(INDX_OF_RECORD);
		}
		else if (viewHolder.getItemViewType() == ACTIVITY_SCHEDULE) {	
			((ActivityScheduleViewHolder)viewHolder).bindViewHolder(INDX_OF_RECORD);
		}
		else if (viewHolder.getItemViewType() == ACTIVITY_DESCRIPTION) {	
			((ActivityDescriptionViewHolder)viewHolder).bindViewHolder(INDX_OF_RECORD);
		}
		else if (viewHolder.getItemViewType() == ACTIVITY_IMAGELIST) {
			((ActivityImageListViewHolder)viewHolder).bindViewHolder(INDX_OF_RECORD);
		}
		else if(viewHolder.getItemViewType() == ACTIVITY_REVIEW) {
			((ActivityReviewViewHolder)viewHolder).bindViewHolder(INDX_OF_RECORD);
		}
	}


	 @Override
	 public int getItemViewType(int position) {
	      return position;
	 }
	   
	@Override
	 public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
	    View v;
        if (viewType == ACTIVITY_HEADER) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_kidos_activity_header_card, viewGroup, false);
            return new ActivityHeaderViewHolder(v);
        }else if(viewType == ACTIVITY_ADDRESS) {
        	 v = LayoutInflater.from(viewGroup.getContext())
                     .inflate(R.layout.layout_kidos_activity_address_card, viewGroup, false);
             return new ActivityAddressViewHolder(v);
         } else if (viewType == ACTIVITY_SCHEDULE) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_kidos_activity_schedule_card, viewGroup, false);
            return new ActivityScheduleViewHolder(v);
        } else if(viewType == ACTIVITY_DESCRIPTION){
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_kidos_activity_description_card, viewGroup, false);
            return new ActivityDescriptionViewHolder(v);
        } else if(viewType == ACTIVITY_IMAGELIST){
        	 v = LayoutInflater.from(viewGroup.getContext())
                     .inflate(R.layout.layout_kidos_activity_images_liststrip_card, viewGroup, false);
             return new ActivityImageListViewHolder(v);
        } else if(viewType == ACTIVITY_REVIEW){
        	 v = LayoutInflater.from(viewGroup.getContext())
                     .inflate(R.layout.layout_kidos_activity_review_card, viewGroup, false);
             return new ActivityReviewViewHolder(v);
        
        }
        return null;
        
    }

	
	private String getFullAddress(int position) {
		StringBuffer address=new StringBuffer();
    	address.append(data.get(position).getAddressline1())
    		   .append(", ")
    		   .append(data.get(position).getLandmark())
    		   .append(", ")
    		   .append(data.get(position).getArea())
    		   .append(", ")
    		   .append(data.get(position).getCity())
    		   .append(" - ")
    		   .append(data.get(position).getPincode());
    	
    	return address.toString();
	}
	
	/**
	 * Create custom viewholder classes
	 * @author Kartik
	 *
	 */
	public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View v) {
            super(v);
        }

		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 0;
		}
    }

	public class ActivityAddressViewHolder extends CustomViewHolder {
		TextView lableAddress;
		TextView txtAddress;
		ImageView imgAddressMapThumbnail;
		View view;
		public ActivityAddressViewHolder(View v) {
			super(v);
			this.view=v;
			
			//TextView myTextView=(TextView)view.findViewById(R.id.txt_activity_address_label);
			//Typeface typeFace= Typeface.createFromAsset(view.getContext().getAssets(),"Digital-Serial-BoldItalic.ttf");
			//myTextView.setTypeface(typeFace);
			this.txtAddress = (TextView) v.findViewById(R.id.txt_activity_address);
			this.imgAddressMapThumbnail = (ImageView)v.findViewById(R.id.img_activity_address_map_thumbnail);
			
		}
		
		public void bindViewHolder(final int position)
	    {
			this.txtAddress.setText(getFullAddress(position));
			
			double latitude=Double.valueOf(data.get(position).getLoc().getCoordinates()[1]);
			double longitude=Double.valueOf(data.get(position).getLoc().getCoordinates()[0]);
			
			
	        String URL = "http://maps.google.com/maps/api/staticmap?markers=color:red|" +latitude + "," + longitude + "&zoom=13&size=80x80&sensor=false";
	        Picasso.with(activity).load(URL).into(this.imgAddressMapThumbnail);
	        imgAddressMapThumbnail.setOnClickListener(new LocateActivityImageClickListener(activity, data.get(position).getName(),latitude,longitude));
	    }
		
	}
	    
	
    public class ActivityHeaderViewHolder extends CustomViewHolder {
      ViewPager mViewPager;
    	TextView txtName;
    	ImageView shareImg;
    	View view;
    	Button reviewBtn;
    	Button addphotoBtn;
    	Button contactBtn;
    	
    	public ActivityHeaderViewHolder(View v) {
            super(v);
            this.view=v;
            this.mViewPager = (ViewPager) v.findViewById(R.id.viewpager_activityimgs);
            this.txtName = (TextView) v.findViewById(R.id.txt_activity_name);
            this.shareImg = (ImageView)v.findViewById(R.id.img_share_activity);
            this.reviewBtn = (Button)v.findViewById(R.id.btn_review);
            this.addphotoBtn = (Button)v.findViewById(R.id.btn_photo);
            this.contactBtn = (Button)v.findViewById(R.id.btn_contact);
            this.shareImg.bringToFront();
          
        }
        
        public void bindViewHolder(final int position)
        {
			KidosActivityImageSliderAdapter imageSliderAdapter;

        	this.txtName.setText(data.get(position).getName());
        	if(data.get(position).getImages()!=null && data.get(position).getImages().length>0)
        	{
        		 imageSliderAdapter = new KidosActivityImageSliderAdapter(view.getContext(),data.get(position).getImages());

        	}
        	else
			{
				KidosActivityImagesBean[] img = new KidosActivityImagesBean[]{new KidosActivityImagesBean(KidosConstants.DEFAULT_ACTIVITY_IMAGE)};
				 imageSliderAdapter = new KidosActivityImageSliderAdapter(view.getContext(),img);
			}

			mViewPager.setAdapter(imageSliderAdapter);
        	//call button
        	this.contactBtn.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View v) {
                	 //showCallListPopup(contactBtn,data.get(position).getContacts());
                	 Intent contactIntent = new Intent(activity,KidosContact.class);
                	 Bundle bundle = new Bundle();
 		        	
 					bundle.putParcelable("contacts", data.get(position).getContacts());
 			
 					contactIntent.putExtras(bundle); 
 					activity.startActivity(contactIntent);
         	
                	 
                 }
             });

        	//addPhotoBtn
        	this.addphotoBtn.setOnClickListener(new OnClickListener(){
        		@Override
				public void onClick(View v) {
					Intent addPhotoIntent = new Intent(activity,KidosAddActivityPhoto.class);
					Bundle bundle = new Bundle();
		        	
					bundle.putString("activityId", data.get(position).getActivityId());
			
					addPhotoIntent.putExtras(bundle); 
					activity.startActivity(addPhotoIntent);
        		}
        	});
        	

        	//share image
        	this.shareImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent sharingIntent = new Intent(Intent.ACTION_SEND);
					String imgUrl;

					System.out.println("*******Clicked share image");

					if(data.get(position).getImages()!=null && data.get(position).getImages().length>0)
					{
						imgUrl =data.get(position).getImages()[0].getImgurl();

					}
					else
					{
						imgUrl = KidosConstants.DEFAULT_ACTIVITY_IMAGE;

					}

					System.out.println("***************imgUrl="+imgUrl);
					System.out.println("uri parse="+Uri.parse(imgUrl));

				    if (imgUrl != null) {
					
					//Add text and then Image URI
				    	
				      	sharingIntent.putExtra(Intent.EXTRA_SUBJECT, data.get(position).getName() +", "+data.get(position).getArea()+","+data.get(position).getCity());
						sharingIntent.putExtra(Intent.EXTRA_TEXT,  data.get(position).getName() +", "+data.get(position).getArea()+","+data.get(position).getCity() +". Checkout this activity for kids on Kidos!! visit http://www.kidos.co.in to get more details about activity and download kidos app from Google app store to find such interesting activities for your kid");
		
				      	sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imgUrl));
						sharingIntent.setType("image/jpeg");
						sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

				    }
				    else
				    {
						//Add text and then Image URI
						sharingIntent.putExtra(Intent.EXTRA_TEXT, "Checkout this activity for kids on Kidos!!"+data.get(position).getName() +", "+data.get(position).getArea()+","+data.get(position).getCity());
						sharingIntent.setType("image/jpeg");
						sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

				    }
					
					
					activity.startActivity(Intent.createChooser(sharingIntent,"Share using"));	
				}
			});
        	
        	//review button
        	reviewBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	Intent intent=new Intent(activity,KidosWriteActivityReview.class);
    				Bundle bundle = new Bundle();
    	        	
    				bundle.putString("activityId", data.get(position).getActivityId());
    				
    				intent.putExtras(bundle); 
    				activity.startActivity(intent);
                }
            });
        	
        }

    }

    
    public class ActivityScheduleViewHolder extends CustomViewHolder {
        TextView txtActivitySchedule;
        TextView txtActivityCriteria;
        TextView txtActivityFees;

        public ActivityScheduleViewHolder(View v) {
            super(v);
            this.txtActivitySchedule = (TextView) v.findViewById(R.id.txt_activity_schedule);
            this.txtActivityCriteria = (TextView) v.findViewById(R.id.txt_activity_criteria);
            this.txtActivityFees = (TextView) v.findViewById(R.id.txt_activity_fees);
        }
        
        public void bindViewHolder(int position)
        {
        	StringBuffer activityBatchBuffer=new StringBuffer();
        	for(KidosActivityBatchesBean bean:data.get(position).getBatches())
        	{
        		activityBatchBuffer.append(android.text.TextUtils.join(", ", bean.getDays())+" "+ bean.getStarttime() +" - "+ bean.getEndtime()+"\n");
        		
        	}
        	this.txtActivitySchedule.setText(activityBatchBuffer.toString());
        	this.txtActivityCriteria.setText(data.get(position).getAge().getFrom() +" - "+data.get(position).getAge().getTo()+" years age group");
        	
        	this.txtActivityFees.setText("Rs. "+ data.get(position).getFees()+" "+data.get(position).getUnit());
        	
        }
    }
    
    
    public class ActivityDescriptionViewHolder extends CustomViewHolder {
        TextView txtActivityDescr;

        public ActivityDescriptionViewHolder(View v) {
            super(v);
            this.txtActivityDescr = (TextView) v.findViewById(R.id.txt_activity_detail);
        }
        
        public void bindViewHolder(int position)
        {
        	this.txtActivityDescr.setText(data.get(position).getDescription());
        }
    }
    
    public class ActivityImageListViewHolder extends CustomViewHolder {
    	
    	LinearLayout layout;
    	View view;
    	
    	public ActivityImageListViewHolder(View v)
    	{
    		super(v);
    		this.view=v;
    		this.layout= (LinearLayout)view.findViewById(R.id.img_activity_linear);
    		
    	}
    	 public void bindViewHolder(int position)
    	 {
    		 for (int i = 0; i < data.get(position).getImages().length; i++) {
    		        ImageView imageView = new ImageView(this.view.getContext());
    		        
    		        imageView.setId(i);
    		        imageView.setPadding(10, 30, 10, 30);
    		        Picasso.with(this.view.getContext()).load(data.get(position).getImages()[i].getImgurl()).into(imageView);
    		        imageView.setScaleType(ScaleType.FIT_XY);
    		        layout.addView(imageView);
    		        android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
    		        layoutParams.width = 250;
    		        layoutParams.height = 250;
    		        imageView.setLayoutParams(layoutParams);
    		       
    		 } 
    	 }
    	
    }
    
    public class ActivityReviewViewHolder extends CustomViewHolder {
       ListView lstActivityReview;
       View view;
    	
        public ActivityReviewViewHolder(View v) {
            super(v);
            this.lstActivityReview = (ListView)v.findViewById(R.id.lst_activity_review);
            this.view=v;
        }
        
        public void bindViewHolder(int position)
        {
        	if(data.get(position).getReviews()!=null) {
				this.lstActivityReview.setAdapter(new KidosReviewListItemAdapter(view, data.get(position)));
				setListViewHeightBasedOnChildren(this.lstActivityReview);
			}
		}
        
        
        public  void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter(); 
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
               // listItem.measure(0, 0);
                float px = 300 * (listView.getResources().getDisplayMetrics().density);
                listItem.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }
    
	private void showCallListPopup(View anchor,KidosActivityContactsBean phoneNumbers)
	{
		 View popupView = LayoutInflater.from(anchor.getContext()).inflate(R.layout.layout_callactivity_popup, null);
		
		 ArrayAdapter<String> popupMenuAdapter = new ArrayAdapter<String>(anchor.getContext(),R.layout.layout_kidos_callactivity_popup_item, phoneNumbers.toList());

		final ListView listOfPhoneNumbers= (ListView)popupView.findViewById(R.id.lst_activitycontacts);
			   
        listOfPhoneNumbers.setAdapter(popupMenuAdapter);
        
        final PopupWindow popupWindow = new PopupWindow(
			      popupView, 600, LayoutParams.WRAP_CONTENT);
        
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        
        
        listOfPhoneNumbers.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {

           String number = (String)listOfPhoneNumbers.getItemAtPosition(position);
           Toast.makeText(KidosApplication.getContext(),"Calling "+number,Toast.LENGTH_SHORT).show();

			if ( ContextCompat.checkSelfPermission( activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
				ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},
						MY_PERMISSIONS_CALL_PHONE);
			}

			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
					activity.startActivity(intent);

           popupWindow.dismiss();
            }
        });
    
			 
        popupWindow.showAsDropDown(anchor, 50, -30);
	}


	
	
	/**
	 * Class used to switch to map window
	 * @author Kartik
	 *
	 */
	class LocateActivityImageClickListener implements View.OnClickListener
	{
		private double latitude;
		private double longitude;
		private String activityName;
		private Activity activity;
		public LocateActivityImageClickListener(Activity activity_, String activityName_, double latitude_,double longitude_) {
			activityName=activityName_;
			latitude=latitude_;
			longitude=longitude_;
			activity=activity_;
		}
		

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(activity,KidosActivityMap.class);
			Bundle bundle = new Bundle();
        	
			bundle.putDouble("latitude", latitude);
			bundle.putDouble("longitude", longitude);
			bundle.putString("activityName", activityName);
			intent.putExtras(bundle); 
			activity.startActivity(intent);
			
		}
		
	}
	
}
