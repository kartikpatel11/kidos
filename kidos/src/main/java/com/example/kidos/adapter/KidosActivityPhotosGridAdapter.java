package com.example.kidos.adapter;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.kidos.R;
import com.example.kidos.beans.KidosActivityPhotosGridItemBean;
import com.squareup.picasso.Picasso;

public class KidosActivityPhotosGridAdapter extends ArrayAdapter<KidosActivityPhotosGridItemBean> {

	    private Context mContext;
	    private int layoutResourceId;
	    private ArrayList<KidosActivityPhotosGridItemBean> mGridData = new ArrayList<KidosActivityPhotosGridItemBean>();

	    public KidosActivityPhotosGridAdapter(Context mContext, int layoutResourceId, ArrayList<KidosActivityPhotosGridItemBean> mGridData) {
	        super(mContext, layoutResourceId, mGridData);
	        this.layoutResourceId = layoutResourceId;
	        this.mContext = mContext;
	        this.mGridData = mGridData;
	    }


	    /**
	     * Updates grid data and refresh grid items.
	     * @param mGridData
	     */
	    public void setGridData(ArrayList<KidosActivityPhotosGridItemBean> mGridData) {
	        this.mGridData = mGridData;
	        notifyDataSetChanged();
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ViewHolder holder;

	        if (row == null) {
	            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            holder = new ViewHolder();
	           // holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
	            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
	            row.setTag(holder);
	        } else {
	            holder = (ViewHolder) row.getTag();
	        }

	        KidosActivityPhotosGridItemBean item = mGridData.get(position);
	        //holder.titleTextView.setText(Html.fromHtml(item.getTitle()));

	        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
	        return row;
	    }

	    static class ViewHolder {
	       // TextView titleTextView;
	        ImageView imageView;
	    }

}
	
