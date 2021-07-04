package com.appsfeature.education.doctor.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appsfeature.education.AppApplication;
import com.appsfeature.education.R;
import com.appsfeature.education.activity.BaseActivity;
import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.PresenterModel;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.util.ClassUtil;
import com.appsfeature.education.util.DateTimeUtil;
import com.appsfeature.education.util.SupportUtil;
import com.helper.callback.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingSummaryActivity extends BaseActivity {


    private TextView name, designation, fees, date, membersText;
    private ImageView imgProfile;
    private DoctorModel doctorModel;
    private PatientModel patientModel;
    private EditText etDescText;
    private CheckBox termsCheckBox;
    private boolean isShowProgress = false;
    private boolean isDialogCancelable = true;
    private PatientModel mSelectedMember;
    private String mPatientName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        doctorModel = getExtraProperty().getDoctorModel();
        mPatientName = getExtraProperty().getParentName(); //for reBooking
        initUi();
        setUpToolBar("Booking details");
    }

    private void initUi() {
        name = findViewById(R.id.name);
        imgProfile = findViewById(R.id.img_profile);
        designation = findViewById(R.id.designation);
        fees = findViewById(R.id.fees);
        date = findViewById(R.id.date);
        membersText = findViewById(R.id.membersText);
        etDescText = findViewById(R.id.descText);
        etDescText.setVisibility(View.GONE);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        (findViewById(R.id.confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termsCheckBox.isChecked()) {
                    bookAppointment();
                } else {
                    SupportUtil.showToast(v.getContext(), "Please Select Terms And condition.");
                }
            }
        });

        (findViewById(R.id.select_member)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientModel != null) {
                    openFamilyMemberSelector();
                } else {
                    loadFamilyMember(true);
                }
            }
        });

        (findViewById(R.id.termsLink1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassUtil.openTermsAndConditions(BookingSummaryActivity.this);
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
//        String doctorFees = !TextUtils.isEmpty(doctorModel.getOldClinicFee()) ? doctorModel.getOldClinicFee() : doctorModel.getClinicFee();
        String doctorFees = doctorModel.getClinicFee();
        fees.setText("Fees Rs." + doctorFees);
        date.setText(getExtraProperty().getAppointmentTime() + "  " + DateTimeUtil.getDateInMobileViewFormat(getExtraProperty().getAppointmentDate()));
    }

    private void bookAppointment() {
        String patientId = AppApplication.getInstance().getLoginSDK().getUserId();
        String doctorId = doctorModel.getId();
        String disease = etDescText.getText().toString();
        String bookingDate = DateTimeUtil.getDateInServerFormat(getExtraProperty().getAppointmentDate());
        String bookingSlot = getExtraProperty().getAppointmentTime();
        String bookingType = TextUtils.isEmpty(mPatientName) ? "new" : "old";
        String patientName = mSelectedMember.getName();
        String patientGender = mSelectedMember.getGender();
        String patientRelation = mSelectedMember.getRelation();
        String patientDob = mSelectedMember.getDateOfBirth();

        if (getExtraProperty() != null) {
            this.isShowProgress = true;
            appPresenter.bookPatientAppointment(patientId, doctorId, bookingDate, bookingSlot
                    , bookingType, patientName, patientGender, patientRelation, patientDob, new Response.Callback<AppointmentModel>() {
                        @Override
                        public void onSuccess(AppointmentModel response) {
                            bookingSuccessful(response);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            onErrorOccurred(e);
                        }
                    });
        }
    }

    private void bookingSuccessful(AppointmentModel response) {
        SupportUtil.showToast(this, "Appointment booked successfully");
        ClassUtil.openBookingSuccessActivity(this, doctorModel, response);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFamilyMember(false);
    }

    private boolean isOpenSelectionDialog = false;

    private void loadFamilyMember(boolean isOpenSelectionDialog) {
        if (getExtraProperty() != null) {
            this.isShowProgress = isOpenSelectionDialog;
            this.isOpenSelectionDialog = isOpenSelectionDialog;
            appPresenter.getPatientProfile(AppApplication.getInstance().getLoginSDK().getUserId());
        }
    }

    @Override
    public void onUpdateUI(PresenterModel response) {
        if (response.getPatientModel() != null && response.getPatientModel().getFamilyMember() != null) {
            patientModel = response.getPatientModel();
            if (isOpenSelectionDialog) {
                openFamilyMemberSelector();
            }
        }
    }

    private void openFamilyMemberSelector() {
        if (patientModel != null && patientModel.getFamilyMember() != null && patientModel.getFamilyMember().size() > 0) {
            if (!TextUtils.isEmpty(mPatientName)) {
                List<PatientModel> sltMember = new ArrayList<>();
                for (PatientModel item : patientModel.getFamilyMember()) {
                    if (item.getName().contains(mPatientName)) {
                        sltMember.add(item);
                        break;
                    }
                }
                patientModel = new PatientModel();
                patientModel.setFamilyMember(sltMember);
            }
            if(patientModel.getFamilyMember().size() > 0) {
                ClassUtil.openFamilyMemberSelector(this, patientModel, new Response.OnClickListener<PatientModel>() {
                    @Override
                    public void onItemClicked(View view, PatientModel item) {
                        mSelectedMember = item;
                        membersText.setText(item.getName());
                    }
                });
            }else {
                SupportUtil.showToast(this, "Family member not found.");
            }
        }else {
            SupportUtil.showToast(this, "Family member not found.");
        }
    }

    @Override
    public void onErrorOccurred(Exception e) {
        SupportUtil.showToast(this, e.getMessage());
    }

    @Override
    public void onStartProgressBar() {
        if (isShowProgress) {
            SupportUtil.showDialog(this, getString(R.string.helper_popup_progress_message), isDialogCancelable);
        }
    }

    @Override
    public void onStopProgressBar() {
        SupportUtil.hideDialog();
    }
}
