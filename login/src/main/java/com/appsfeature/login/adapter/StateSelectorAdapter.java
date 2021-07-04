package com.appsfeature.login.adapter;


/**
 * Created by Abhijit on 14-Nov-16.
 */


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsfeature.login.R;
import com.appsfeature.login.model.StateModel;

import java.util.ArrayList;
import java.util.List;

// The standard text view adapter only seems to search from the beginning of whole words
// so we've had to write this whole class to make it possible to search
// for parts of the arbitrary string we want
public class StateSelectorAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<StateModel> originalData = null;
    private List<StateModel> filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public StateSelectorAdapter(Context context, List<StateModel> data) {
        this.context = context;
        this.filteredData = data;
        this.originalData = data;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return filteredData.size();
    }

    public StateModel getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.slot_empty, null);
            View v = convertView;
            holder = new ViewHolder();
            holder.tvState = (TextView) v.findViewById(R.id.tv_message);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (getCount()-1==position){
            holder.tvState.setBackgroundResource(android.R.color.white);
        }else {
            holder.tvState.setBackgroundResource(R.drawable.bg_alert_underline);
        }

        String stateName = filteredData.get(position).getStateName();
        if (!TextUtils.isEmpty(stateName)) {
            holder.tvState.setText(stateName);
        }
        return convertView;
    }


    static class ViewHolder {
        public TextView tvState;
    }


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<StateModel> list = originalData;

            int count = list.size();
            final ArrayList<StateModel> nlist = new ArrayList<>(count);
            String filterableText;

            if (!filterString.equals("")) {
                for (StateModel model : list) {
                    if(!filterString.equals("")) {
                        filterableText = model.getStateName();
                        if (filterableText != null && filterableText.toLowerCase().contains(filterString))  {
                            nlist.add(model);
                        }
                    }else{
                        nlist.add(model);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            } else {
                results.values = originalData;
                results.count = originalData.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<StateModel>) results.values;
            notifyDataSetChanged();
        }

    }

}