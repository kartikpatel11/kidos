package com.example.kidos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kidos.R;
import com.example.kidos.beans.KidosIndianCitiesBean;
import com.example.kidos.beans.KidosIndianStatesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kartik on 08/12/17.
 */

public class KidosFindLocationAdapter extends BaseAdapter {

    private Context context;
    private List<KidosIndianStatesBean> indianCities;


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<Item> mData = new ArrayList<Item>();

    private LayoutInflater mInflater;

    public KidosFindLocationAdapter() {
        super();
    }

    public KidosFindLocationAdapter(Context context, List<KidosIndianStatesBean> item) {
        this.context = context;
        this.indianCities = item;
        createInternalList(indianCities);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    private void createInternalList(List<KidosIndianStatesBean> states) {
        for (KidosIndianStatesBean state : states) {

            for (KidosIndianCitiesBean city : state.getCities()) {
                Item item = new Item();
                item.setSection(true);
                item.setData(city.getCity() + " - " + state.getState());
                mData.add(item);
                for (String area : city.getAreas()) {
                    Item areaItem = new Item();
                    areaItem.setSection(false);
                    areaItem.setData(area);
                    mData.add(areaItem);

                }

            }
        }
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).isSection() ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public boolean isEnabled(int position) {
        return !mData.get(position).isSection();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.layout_kidos_activity_find_location_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.tvLocationItemTitle);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.layout_kidos_activity_find_location_section, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.tvLocationSectionTitle);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position).getData());

        return convertView;
    }
    public static class ViewHolder {
        public TextView textView;
    }
}


    class Item {
        String data;
        boolean section;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public boolean isSection() {
            return section;
        }

        public void setSection(boolean section) {
            this.section = section;
        }

        @Override
        public String toString()
        {
            return data;
        }
    }
