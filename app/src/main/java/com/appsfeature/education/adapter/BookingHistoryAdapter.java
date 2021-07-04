package com.appsfeature.education.adapter;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.R;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.listeners.AppointmentStatus;
import com.appsfeature.education.listeners.ItemType;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.education.util.SupportUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final AppCallback.OnAppointmentClickListener<AppointmentModel> callback;
    private List<AppointmentModel> mList;
    private long currentTimeStamp;

    public BookingHistoryAdapter(List<AppointmentModel> mList, AppCallback.OnAppointmentClickListener<AppointmentModel> callback) {
        this.mList = mList;
        this.callback = callback;
        try {
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(DateTimeUtil.getDateStampFormatted());
            if (currentDate != null) {
                currentTimeStamp = currentDate.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case ItemType.TYPE_APPOINTMENT:
                return new ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_booking_history, viewGroup, false));
            case ItemType.TYPE_TITLE:
            default:
                return new TitleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slot_skill_title, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        AppointmentModel item = mList.get(position);
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder viewHolder = (TitleViewHolder) holder;
            viewHolder.tvTitle.setText(DateTimeUtil.getDateInMobileViewFormatFromServer(item.getAppointmentDate()));
        } else if (holder instanceof ListViewHolder) {
            ListViewHolder viewHolder = (ListViewHolder) holder;
            try {
                String[] time = item.getAppointmentSlot().split("-");
                viewHolder.start_time.setText(time[0].trim());
                viewHolder.end_time.setText(time[1].trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolder.tv_title.setText(item.getPatientName());
            viewHolder.tv_doctor_detail.setText(SupportUtil.generateBoldSubTitle("Appointment Booked with ", getDoctorName(item.getDoctorDetails())));
            viewHolder.tv_amount.setText("Fees Rs." + item.getDoctorFee());

            AppointmentStatus status = getAppointmentStatus(item);
            viewHolder.status = status;
            setCircleStatus(viewHolder, status);
            updateButtonColor(viewHolder, status);
        }
    }

    private void updateButtonColor(ListViewHolder viewHolder, AppointmentStatus appointmentStatus) {
        if (appointmentStatus == AppointmentStatus.Cancel) {
            viewHolder.btn_action.setBackgroundResource(R.drawable.bg_button_cancel);
            viewHolder.btn_action.setText("Cancel");
            viewHolder.btn_action.setVisibility(View.VISIBLE);
        } else if (appointmentStatus == AppointmentStatus.ReBooking) {
            viewHolder.btn_action.setBackgroundResource(R.drawable.bg_button_rebook);
            viewHolder.btn_action.setText("ReBook");
            viewHolder.btn_action.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btn_action.setVisibility(View.GONE);
        }
    }

    private void setCircleStatus(ListViewHolder viewHolder, AppointmentStatus status) {
        if (status == AppointmentStatus.Canceled) {
            viewHolder.slot.setBackgroundResource(R.drawable.bg_circle_red);
        } else {
            viewHolder.slot.setBackgroundResource(R.drawable.bg_circle_green);
        }

    }

    private AppointmentStatus getAppointmentStatus(AppointmentModel item) {
        AppointmentStatus appointmentStatus;
        if (item.getStatus().equalsIgnoreCase("0")) {
            appointmentStatus = AppointmentStatus.Canceled;
        } else {
            if (item.getAppointmentStatus().equalsIgnoreCase("2")) {
                if (!TextUtils.isEmpty(item.getBookingType()) && !item.getBookingType().equalsIgnoreCase("old")
                        && item.getReBooking().equalsIgnoreCase("available")) {
                    appointmentStatus = AppointmentStatus.ReBooking;
                } else {
                    appointmentStatus = AppointmentStatus.Booked;
                }
            } else if (item.getAppointmentStatus().equalsIgnoreCase("0")
                    && isValidAppointmentByDate(item.getAppointmentDate())) {
                appointmentStatus = AppointmentStatus.Cancel;
            } else {
                appointmentStatus = AppointmentStatus.Booked;
            }
        }
        return appointmentStatus;
    }

    private boolean isValidAppointmentByDate(String appointmentDate) {
        try {
            Date appDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(appointmentDate);
            return appDate != null && appDate.getTime() >= currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private String getDoctorName(List<DoctorModel> doctorDetails) {
        if (doctorDetails != null && doctorDetails.size() > 0) {
            return doctorDetails.get(0).getName();
        }
        return "NA";
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        TitleViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView start_time;
        private final TextView end_time;
        private final TextView tv_title;
        private final TextView tv_doctor_detail;
        private final TextView tv_amount;
        private final View slot;
        private final TextView btn_action;
        public AppointmentStatus status;

        ListViewHolder(View view) {
            super(view);
            slot = view.findViewById(R.id.slot);
            start_time = view.findViewById(R.id.start_time);
            end_time = view.findViewById(R.id.end_time);
            tv_title = view.findViewById(R.id.tv_title);
            tv_doctor_detail = view.findViewById(R.id.tv_doctor_detail);
            tv_amount = view.findViewById(R.id.tv_amount);
            btn_action = view.findViewById(R.id.btn_action);

            btn_action.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mList.size() > getAdapterPosition() && getAdapterPosition() >= 0) {
                if (v.getId() == R.id.btn_action) {
                    if (status == AppointmentStatus.Cancel) {
                        callback.onItemCancelClicked(v, mList.get(getAdapterPosition()));
                    } else if (status == AppointmentStatus.ReBooking) {
                        callback.onItemReBookingClicked(v, mList.get(getAdapterPosition()));
                    }
                } else {
                    callback.onItemClicked(v, mList.get(getAdapterPosition()));
                }
            }
        }
    }
}
