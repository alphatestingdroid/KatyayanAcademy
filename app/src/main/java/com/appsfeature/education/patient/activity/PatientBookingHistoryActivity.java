package com.appsfeature.education.patient.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.adapter.BookingHistoryAdapter;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.listeners.AppCallback;
import com.appsfeature.education.listeners.ItemType;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.patient.fragment.RatingAndReviewPopup;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PatientBookingHistoryActivity extends BaseActivity {

    private View llNoData;
    private BookingHistoryAdapter adapter;
    private List<AppointmentModel> mList = new ArrayList<>();
    private AppointmentModel ratingAppointmentModel;
    private String memberName;
    private boolean isDashboard = false;
    private long currentTimeStamp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        memberName = getExtraProperty().getMemberName();
        isDashboard = getExtraProperty().isDashboard();
        onInitializeUI();
        setUpToolBar(isDashboard ? "Dashboard" : "My Appointment");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getExtraProperty() != null) {
            appPresenter.getPatientBookingHistory(AppApplication.getInstance().getLoginSDK().getUserId());
        }
    }

    public void onInitializeUI() {
        llNoData = findViewById(R.id.ll_no_data);
        RecyclerView rvList = findViewById(R.id.recycler_view);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new BookingHistoryAdapter(mList, new AppCallback.OnAppointmentClickListener<AppointmentModel>() {
            @Override
            public void onItemClicked(View view, AppointmentModel item) {
                ClassUtil.openPatientBookingDetailActivity(PatientBookingHistoryActivity.this, item);
            }

            @Override
            public void onItemCancelClicked(View view, AppointmentModel item) {
                cancelAppointmentDialog(item);
            }

            @Override
            public void onItemReBookingClicked(View view, AppointmentModel item) {
                if (item.getDoctorDetails().size() > 0) {
                    DoctorModel doctorModel = item.getDoctorDetails().get(0);
                    DoctorModel mDoctorModel = doctorModel.getClone();
                    mDoctorModel.setClinicFee(doctorModel.getOldClinicFee());
                    ClassUtil.openReBookingActivity(PatientBookingHistoryActivity.this, mDoctorModel, item.getPatientName());
                }
            }
        });

        rvList.setAdapter(adapter);
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
    public void onUpdateUI(PresenterModel response) {
        SupportUtil.showNoData(llNoData, View.GONE);
        llNoData.setVisibility(View.GONE);
        mList.clear();
        if (response.getAppointmentModel() != null && response.getAppointmentModel().size() > 0) {
            String lastDate = "";
            ArrayList<AppointmentModel> appointmentList = new ArrayList<>();
            for (AppointmentModel item : response.getAppointmentModel()) {
                if (TextUtils.isEmpty(memberName) || item.getPatientName().contains(memberName)) {
                    if (!isDashboard || isValidAppointmentForDashboard(item)) {
                        if (!item.getAppointmentDate().equalsIgnoreCase(lastDate)) {
                            lastDate = item.getAppointmentDate();
                            appointmentList.add(new AppointmentModel(lastDate, ItemType.TYPE_TITLE));
                        }
                        if (ratingAppointmentModel == null && item.getAppointmentStatus().equalsIgnoreCase("2") && TextUtils.isEmpty(item.getRatingStatus())) {
                            ratingAppointmentModel = item;
                        }
                        item.setItemType(ItemType.TYPE_APPOINTMENT);
                        appointmentList.add(item);
                    }
                }
            }
            mList.addAll(appointmentList);
        } else {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        SupportUtil.showNoData(llNoData, mList.size() > 0 ? View.GONE : View.VISIBLE);
        showRatingAndReviewPopup();
    }

    private boolean isValidAppointmentForDashboard(AppointmentModel item) {
        if(!item.getStatus().equalsIgnoreCase("0")){
            if(item.getAppointmentStatus().equalsIgnoreCase("2")
                    && (!TextUtils.isEmpty(item.getBookingType()) && !item.getBookingType().equalsIgnoreCase("old"))
                    && item.getReBooking().equalsIgnoreCase("available")) {
                return true;
            }else return isValidAppointmentByDate(item.getAppointmentDate());
        }else {
            return false;
        }
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


    private Handler handler = new Handler();

    private void showRatingAndReviewPopup() {
        if (ratingAppointmentModel != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RatingAndReviewPopup.newInstance(ratingAppointmentModel.getClone())
                            .show(PatientBookingHistoryActivity.this);
                    ratingAppointmentModel = null;
                }
            }, 1000);
        }
    }

    @Override
    public void onErrorOccurred(Exception e) {
        SupportUtil.showToast(this, e.getMessage());
        if (mList != null && mList.size() < 1) {
            SupportUtil.showNoData(llNoData, View.VISIBLE);
        } else {
            SupportUtil.showNoData(llNoData, View.GONE);
        }
    }

    @Override
    public void onStartProgressBar() {
        SupportUtil.showNoDataProgress(llNoData);
    }

    @Override
    public void onStopProgressBar() {

    }

    private void cancelAppointmentDialog(AppointmentModel item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel this Appointment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                cancelAppointment(item);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cancelAppointment(AppointmentModel item) {
        SupportUtil.showDialog(PatientBookingHistoryActivity.this, getString(R.string.helper_popup_progress_message), true);
        appPresenter.cancelAppointment(AppApplication.getInstance().getLoginSDK().getUserId(), item.getId(), false, new Response.Callback<List<AppointmentModel>>() {
            @Override
            public void onSuccess(List<AppointmentModel> response) {
                SupportUtil.hideDialog();
                SupportUtil.showToast(PatientBookingHistoryActivity.this, "Appointment Canceled");
                onStart();
            }

            @Override
            public void onFailure(Exception e) {
                SupportUtil.hideDialog();
                SupportUtil.showToast(PatientBookingHistoryActivity.this, e.getMessage());
            }
        });
    }
}
