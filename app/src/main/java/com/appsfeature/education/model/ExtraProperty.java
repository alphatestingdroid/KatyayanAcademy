package com.appsfeature.education.model;

import androidx.annotation.NonNull;

import com.appsfeature.education.doctor.DoctorModel;
import com.appsfeature.education.doctor.VideoModel;
import com.appsfeature.education.patient.PatientModel;
import com.helper.model.BaseCategoryProperty;

import java.io.Serializable;

public class ExtraProperty extends BaseCategoryProperty implements Cloneable, Serializable {

    private int courseId;
    private String doctorId;
    private String appointmentDate; //format yyyy-mm-dd
    private String appointmentTime;
    private String memberName;
    private boolean isDashboard = false;
    private DoctorModel doctorModel;
    private AppointmentModel appointmentModel;
    private PatientModel patientModel;
    private String videoId;
    private String apiKey;
    private VideoModel videoModel;


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public VideoModel getVideoModel() {
        return videoModel;
    }

    public void setVideoModel(VideoModel videoModel) {
        this.videoModel = videoModel;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public DoctorModel getDoctorModel() {
        return doctorModel;
    }

    public void setDoctorModel(DoctorModel doctorModel) {
        this.doctorModel = doctorModel;
    }

    public AppointmentModel getAppointmentModel() {
        return appointmentModel;
    }

    public void setAppointmentModel(AppointmentModel appointmentModel) {
        this.appointmentModel = appointmentModel;
    }

    public PatientModel getPatientModel() {
        return patientModel;
    }

    public void setPatientModel(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public boolean isDashboard() {
        return isDashboard;
    }

    public void setDashboard(boolean dashboard) {
        isDashboard = dashboard;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ExtraProperty getClone() {
        try {
            return (ExtraProperty) clone();
        } catch (CloneNotSupportedException e) {
            return new ExtraProperty();
        }
    }
}
