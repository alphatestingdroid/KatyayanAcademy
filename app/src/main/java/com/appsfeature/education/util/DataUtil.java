package com.appsfeature.education.util;

import android.text.TextUtils;

import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.model.AppointmentModel;
import com.appsfeature.login.model.Profile;
import com.appsfeature.login.util.LoginPrefUtil;

public class DataUtil {

    public static StringBuilder getDoctorAddress(DoctorModel doctorModel) {
        StringBuilder address = new StringBuilder();
        address.append("");
        if(!TextUtils.isEmpty(doctorModel.getName())){
            address.append(doctorModel.getName());
            address.append("\n");
        }
        if(!TextUtils.isEmpty(doctorModel.getClinicAddress())){
            address.append(doctorModel.getClinicAddress());
            address.append(",\n");
        }
        if(!TextUtils.isEmpty(doctorModel.getClinicCity())){
            address.append(doctorModel.getClinicCity());
            address.append(", ");
        }
        if(!TextUtils.isEmpty(doctorModel.getClinicState())){
            address.append(doctorModel.getClinicState());
        }
        return address;
    }


    public static StringBuilder getPatientDetail(AppointmentModel appointmentModel) {
        StringBuilder detail = new StringBuilder();
        detail.append("");
        if(!TextUtils.isEmpty(appointmentModel.getPatientName())){
            detail.append(appointmentModel.getPatientName());
            detail.append("\n");
        }
        if(!TextUtils.isEmpty(appointmentModel.getPatientGender())){
            detail.append(appointmentModel.getPatientGender());
            detail.append(" (");
            detail.append(appointmentModel.getPatientDob());
            detail.append(" )\n");
        }
        if(!TextUtils.isEmpty(appointmentModel.getPatientRelation())){
            detail.append("Relation : ");
            detail.append(appointmentModel.getPatientRelation());
        }
        return detail;
    }


    public static StringBuilder getPatientAddress(String patientName) {
        Profile profile = LoginPrefUtil.getProfileDetail();
        StringBuilder address = new StringBuilder();
        address.append(patientName);
        address.append("\n");
        if(!TextUtils.isEmpty(profile.getAddress())){
            address.append(profile.getAddress());
            address.append(",\n");
        }
        if(!TextUtils.isEmpty(profile.getCity())){
            address.append(profile.getCity());
            address.append(", ");
        }
        if(!TextUtils.isEmpty(profile.getState())){
            address.append(profile.getState());
        }
        return address;
    }
}
