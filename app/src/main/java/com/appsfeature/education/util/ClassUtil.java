package com.appsfeature.education.util;

import android.app.Activity;
import android.content.Intent;

import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.doctor.activity.BookingSuccessActivity;
import com.appsfeature.education.doctor.activity.BookingSummaryActivity;
import com.appsfeature.education.doctor.activity.DoctorSearchActivity;
import com.appsfeature.education.doctor.activity.DoctorSlotViewActivity;
import com.appsfeature.education.doctor.activity.InvoiceViewActivity;
import com.appsfeature.education.education.LiveClassActivity;
import com.appsfeature.education.education.VideoLectureActivity;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.education.model.ExtraProperty;
import com.appsfeature.education.patient.PatientModel;
import com.appsfeature.education.patient.activity.AddFamilyManagerActivity;
import com.appsfeature.education.patient.activity.FamilyMemberActivity;
import com.appsfeature.education.patient.activity.PatientBookingDetailActivity;
import com.appsfeature.education.patient.activity.PatientBookingHistoryActivity;
import com.appsfeature.education.patient.fragment.FamilyMemberSelector;
import com.appsfeature.login.LoginSDK;
import com.helper.callback.Response;

public class ClassUtil {

    public static final int REQUEST_CODE_PROFILE_UPDATE = 1001;

    public static void openReBookingActivity(Activity activity, DoctorModel doctorModel, String patientName) {
        openDoctorSlotViewActivity(activity, doctorModel, false, patientName);
    }

    public static void openDoctorSlotViewActivity(Activity activity, DoctorModel doctorModel) {
        openDoctorSlotViewActivity(activity, doctorModel, false, null);
    }

    public static void openDoctorSlotViewActivity(Activity activity, DoctorModel doctorModel, boolean isShowError) {
        openDoctorSlotViewActivity(activity, doctorModel, isShowError, null);
    }
    public static void openDoctorSlotViewActivity(Activity activity, DoctorModel doctorModel, boolean isShowError, String patientName) {
        if(LoginSDK.getInstance().isProfileCompleted()) {
            ExtraProperty extraProperty = new ExtraProperty();
            extraProperty.setDoctorModel(doctorModel);
            extraProperty.setParentName(patientName);
            activity.startActivity(new Intent(activity, DoctorSlotViewActivity.class)
                    .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
        }else {
            if(isShowError){
                SupportUtil.showToast(activity, "You need to update your profile first.");
            }else {
                LoginSDK.getInstance().openLoginPage(activity, true, true, REQUEST_CODE_PROFILE_UPDATE);
            }
        }
    }

    public static void openDoctorProfileActivity(Activity activity, DoctorModel doctorModel) {
//        ExtraProperty extraProperty = new ExtraProperty();
//        extraProperty.setDoctorModel(doctorModel);
//        activity.startActivity(new Intent(activity, DoctorProfileActivity.class)
//                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openBookingSummaryActivity(Activity activity, DoctorModel doctorModel, String time, String date, String patientName) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setDoctorModel(doctorModel);
        extraProperty.setAppointmentTime(time);
        extraProperty.setAppointmentDate(date);
        extraProperty.setParentName(patientName);
        activity.startActivity(new Intent(activity, BookingSummaryActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openBookingSuccessActivity(Activity activity, DoctorModel doctorModel, AppointmentModel appointmentModel) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setDoctorModel(doctorModel);
        extraProperty.setAppointmentModel(appointmentModel);
        activity.startActivity(new Intent(activity, BookingSuccessActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openDoctorSearchActivity(Activity activity) {
        activity.startActivity(new Intent(activity, DoctorSearchActivity.class));
    }

    public static void openFamilyMemberActivity(Activity activity) {
        if(LoginSDK.getInstance().isProfileCompleted()) {
            ExtraProperty extraProperty = new ExtraProperty();
            activity.startActivity(new Intent(activity, FamilyMemberActivity.class)
                    .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
        }else {
            SupportUtil.showToast(activity, "Please update your profile first");
        }
    }

    public static void openAddFamilyMemberActivity(Activity activity) {
        ExtraProperty extraProperty = new ExtraProperty();
        activity.startActivity(new Intent(activity, AddFamilyManagerActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openPatientBookingHistoryActivity(Activity activity, String memberName) {
        openPatientBookingHistoryActivity(activity, memberName, false);
    }
    public static void openPatientBookingHistoryActivity(Activity activity, String memberName, boolean isDashboard) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setMemberName(memberName);
        extraProperty.setDashboard(isDashboard);
        activity.startActivity(new Intent(activity, PatientBookingHistoryActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openPatientBookingDetailActivity(Activity activity, AppointmentModel appointmentModel) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setAppointmentModel(appointmentModel);
        activity.startActivity(new Intent(activity, PatientBookingDetailActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openInvoiceViewActivity(Activity activity, AppointmentModel appointmentModel) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setAppointmentModel(appointmentModel);
        activity.startActivity(new Intent(activity, InvoiceViewActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openLiveClassActivity(Activity activity) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setCourseId(LoginSDK.getInstance().getCourseId());
        activity.startActivity(new Intent(activity, LiveClassActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openVideoLectureActivity(Activity activity) {
        ExtraProperty extraProperty = new ExtraProperty();
        extraProperty.setCourseId(LoginSDK.getInstance().getCourseId());
        activity.startActivity(new Intent(activity, VideoLectureActivity.class)
                .putExtra(AppConstant.CATEGORY_PROPERTY, extraProperty));
    }

    public static void openTermsAndConditions(Activity activity) {

    }

    public static void openFamilyMemberSelector(Activity activity, PatientModel patientModel, Response.OnClickListener<PatientModel> mListener) {
        FamilyMemberSelector.newInstance(patientModel, mListener).show(activity);
    }
}
