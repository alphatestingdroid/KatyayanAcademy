package com.appsfeature.education.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.model.AppointmentSlot;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.util.List;

public class DoctorSlotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Response.OnClickListener<AppointmentSlot> clickListener;
    private List<AppointmentSlot> mList;

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_doctor_time_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public DoctorSlotAdapter(List<AppointmentSlot> mList, Response.OnClickListener<AppointmentSlot> clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        AppointmentSlot item = mList.get(i);
        myViewHolder.startDate.setText(item.getStart());
        myViewHolder.endDate.setText(item.getEnd());

        if(item.getBookedStatus().equalsIgnoreCase("available")
                && item.getLapsedStatus().equalsIgnoreCase("available") ) {
            myViewHolder.slot.setBackgroundResource(R.drawable.bg_circle_green);
        } else if(item.getBookedStatus().equalsIgnoreCase("booked")) {
            myViewHolder.slot.setBackgroundResource(R.drawable.bg_circle_red);
        } else {
            myViewHolder.slot.setBackgroundResource(R.drawable.bg_circle_grey);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final View slot;
        private TextView startDate;
        private TextView endDate;

        private ViewHolder(View v) {
            super(v);
            slot = v.findViewById(R.id.slot);
            endDate = v.findViewById(R.id.end_date);
            startDate = v.findViewById(R.id.start_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( mList.get(getAdapterPosition()).getBookedStatus().equalsIgnoreCase("available")
                    &&  mList.get(getAdapterPosition()).getLapsedStatus().equalsIgnoreCase("available") ) {
                clickListener.onItemClicked(v, mList.get(getAdapterPosition()));
                setSelected(slot);
            }else {
                SupportUtil.showToast(v.getContext(), "Time slot not available, Please choose another one.");
            }
        }
    }

    private View previousView;

    private void setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.bg_circle_green);
        }
        previousView = view;
        view.setBackgroundResource(R.drawable.bg_circle_orenge);
    }

}
