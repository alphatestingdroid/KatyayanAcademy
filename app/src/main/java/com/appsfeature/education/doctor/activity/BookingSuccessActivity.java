package com.appsfeature.education.doctor.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.activity.MainActivity;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.education.util.SupportUtil;


public class BookingSuccessActivity extends BaseActivity {

    private DoctorModel doctorModel;
    private AppointmentModel appointmentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);
        doctorModel = getExtraProperty().getDoctorModel();
        appointmentModel = getExtraProperty().getAppointmentModel();
        initUi();
    }

    private void initUi() {
        TextView doctorDetail = findViewById(R.id.tv_live_subject);
        TextView dateDetail = findViewById(R.id.tv_live_date);
        (findViewById(R.id.btn_open_live_class)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishPreviousActivity();
            }
        });

        doctorDetail.setText(SupportUtil.generateBoldSubTitle("Appointment Booked with ", doctorModel.getName()));
        String dateTime = DateTimeUtil.getDateInMobileViewFormatFromServer(appointmentModel.getAppointmentDate())
                + " " + appointmentModel.getAppointmentSlot();
        dateDetail.setText(SupportUtil.generateBoldSubTitle("on ", dateTime));
    }

    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void onStopProgressBar() {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishPreviousActivity();
    }

    private void finishPreviousActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
