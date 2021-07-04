package com.appsfeature.education.patient.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.listeners.AppointmentStatus;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PatientBookingDetailActivity extends BaseActivity {


    private TextView name, designation, date, fees;
    private TextView tvPatientName, tvPatientRelation, tvPatientDob;
    private ImageView imgProfile, icPatientGender;
    private AppointmentModel appointmentModel;
    private DoctorModel doctorModel;
    private Button cancelAppointmentButton;
    private AppointmentStatus appointmentStatus = AppointmentStatus.Booked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking_detail);

        appointmentModel = getExtraProperty().getAppointmentModel();
        if (appointmentModel.getDoctorDetails().size() > 0) {
            doctorModel = appointmentModel.getDoctorDetails().get(0);
        }
        initUi();
        setUpToolBar("Appointment Detail");
    }

    private void initUi() {
        name = findViewById(R.id.name);
        imgProfile = findViewById(R.id.img_profile);
        designation = findViewById(R.id.designation);
        fees = findViewById(R.id.fees);

        date = findViewById(R.id.date);

        tvPatientName = findViewById(R.id.tv_title);
        icPatientGender = findViewById(R.id.ic_gender);
        tvPatientRelation = findViewById(R.id.tv_relation);
        tvPatientDob = findViewById(R.id.tv_dob);
        cancelAppointmentButton = (findViewById(R.id.cancelAppointmentButton));

        cancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appointmentStatus == AppointmentStatus.Cancel) {
                    cancelAppointmentDialog();
                } else if (appointmentStatus == AppointmentStatus.ReBooking) {
                    DoctorModel mDoctorModel = doctorModel.getClone();
                    mDoctorModel.setClinicFee(doctorModel.getOldClinicFee());
                    ClassUtil.openReBookingActivity(PatientBookingDetailActivity.this, mDoctorModel, appointmentModel.getPatientName());
                }
            }
        });

        (findViewById(R.id.doctor_view)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassUtil.openDoctorProfileActivity(PatientBookingDetailActivity.this, doctorModel);
            }
        });

        (findViewById(R.id.viewAppointmentButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassUtil.openInvoiceViewActivity(PatientBookingDetailActivity.this, appointmentModel);
            }
        });

        if (doctorModel.getProfilePicture() != null) {
            Picasso.get().load(doctorModel.getProfilePicture())
                    .placeholder(R.drawable.ic_user_profile)
                    .error(R.drawable.ic_user_profile)
                    .into(imgProfile);
        } else {
            imgProfile.setImageResource(R.drawable.ic_user_profile);
        }

        name.setText(doctorModel.getName());
        designation.setText(doctorModel.getDesignation());
        fees.setText("Fees Rs." + appointmentModel.getDoctorFee());

        date.setText("Booking on " + DateTimeUtil.getDateInMobileViewFormatFromServer(appointmentModel.getAppointmentDate()) + " " + appointmentModel.getAppointmentSlot());

        tvPatientName.setText(appointmentModel.getPatientName());
        tvPatientRelation.setText("Relation : " + appointmentModel.getPatientRelation());
        tvPatientDob.setText("DOB : " + DateTimeUtil.getDateInMobileViewFormatFromDOB(appointmentModel.getPatientDob()));

        if (appointmentModel.getPatientGender().equalsIgnoreCase("male")) {
            icPatientGender.setImageResource(R.drawable.ic_male);
        } else if (appointmentModel.getPatientGender().equalsIgnoreCase("female")) {
            icPatientGender.setImageResource(R.drawable.ic_female);
        } else {
            icPatientGender.setImageResource(R.drawable.ic_trans);
        }

        if (appointmentModel.getStatus().equalsIgnoreCase("0")) {
            appointmentStatus = AppointmentStatus.Canceled;
            cancelAppointmentButton.setText("Appointment Canceled");
        } else {
            if (appointmentModel.getAppointmentStatus().equalsIgnoreCase("2")) {
                if ((!TextUtils.isEmpty(appointmentModel.getBookingType()) && !appointmentModel.getBookingType().equalsIgnoreCase("old"))
                        && appointmentModel.getReBooking().equalsIgnoreCase("available")) {
                    appointmentStatus = AppointmentStatus.ReBooking;
                    cancelAppointmentButton.setText("ReBooking");
                } else {
                    appointmentStatus = AppointmentStatus.Booked;
                    cancelAppointmentButton.setText("Booked");
                }
            } else if (appointmentModel.getAppointmentStatus().equalsIgnoreCase("0")
                    && isValidAppointmentByDate(appointmentModel.getAppointmentDate())) {
                appointmentStatus = AppointmentStatus.Cancel;
                cancelAppointmentButton.setText("Cancel");
            } else {
                appointmentStatus = AppointmentStatus.Booked;
                cancelAppointmentButton.setText("Booked");
            }
        }
        updateButtonColor(appointmentStatus);
    }

    private boolean isValidAppointmentByDate(String appointmentDate) {
        try {
            Date appDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(appointmentDate);
            Date currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(DateTimeUtil.getDateStampFormatted());
            if (appDate != null && currentDate != null) {
                long mAppDate = appDate.getTime();
                long mCurrentDate = currentDate.getTime();
                return mAppDate >= mCurrentDate;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private void updateButtonColor(AppointmentStatus appointmentStatus) {
        if (appointmentStatus == AppointmentStatus.Canceled) {
            cancelAppointmentButton.setBackgroundResource(R.color.themeBackgroundCardColor);
            cancelAppointmentButton.setTextColor(ContextCompat.getColor(this, R.color.colorButtonRed));
            cancelAppointmentButton.setClickable(false);
        } else if (appointmentStatus == AppointmentStatus.Booked) {
            cancelAppointmentButton.setBackgroundResource(R.color.themeBackgroundCardColor);
            cancelAppointmentButton.setTextColor(ContextCompat.getColor(this, R.color.colorButtonGreen));
            cancelAppointmentButton.setClickable(false);
        } else if (appointmentStatus == AppointmentStatus.Cancel) {
            cancelAppointmentButton.setBackgroundResource(R.drawable.bg_button_cancel);
            cancelAppointmentButton.setTextColor(Color.WHITE);
            cancelAppointmentButton.setClickable(true);
        } else if (appointmentStatus == AppointmentStatus.ReBooking) {
            cancelAppointmentButton.setBackgroundResource(R.drawable.bg_button_rebook);
            cancelAppointmentButton.setTextColor(Color.WHITE);
            cancelAppointmentButton.setClickable(true);
        }

    }

    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {
        SupportUtil.showToast(this, e.getMessage());
    }

    @Override
    public void onStartProgressBar() {
        SupportUtil.showDialog(this, getString(R.string.helper_popup_progress_message), true);
    }

    @Override
    public void onStopProgressBar() {
        SupportUtil.hideDialog();
    }


    private void cancelAppointmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel this Appointment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                cancelAppointment();
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

    private void cancelAppointment() {
        appPresenter.cancelAppointment(AppApplication.getInstance().getLoginSDK().getUserId(), appointmentModel.getId(), true, new Response.Callback<List<AppointmentModel>>() {
            @Override
            public void onSuccess(List<AppointmentModel> response) {
                SupportUtil.showToast(PatientBookingDetailActivity.this, "Appointment Canceled");
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                SupportUtil.showToast(PatientBookingDetailActivity.this, e.getMessage());
            }
        });
    }
}
