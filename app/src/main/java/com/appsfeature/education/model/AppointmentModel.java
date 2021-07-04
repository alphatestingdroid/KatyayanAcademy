package com.appsfeature.education.model;

import androidx.annotation.NonNull;

import com.appsfeature.education.doctor.DoctorModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppointmentModel implements Serializable , Cloneable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doctor_fee")
    @Expose
    private String doctorFee;
    @SerializedName("pay_amount")
    @Expose
    private String payAmount;
    @SerializedName("appointment_date")
    @Expose
    private String appointmentDate;
    @SerializedName("appointment_slot")
    @Expose
    private String appointmentSlot;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("patient_dob")
    @Expose
    private String patientDob;
    @SerializedName("patient_mobile")
    @Expose
    private String patientMobile;
    @SerializedName("patient_relation")
    @Expose
    private String patientRelation;
    @SerializedName("patient_gender")
    @Expose
    private String patientGender;
    @SerializedName("appointment_status")
    @Expose
    private String appointmentStatus;
    @SerializedName("booked_by_type")
    @Expose
    private String bookedByType;
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("rating_status")
    @Expose
    private String ratingStatus;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("rebooking")
    @Expose
    private String reBooking;
    @SerializedName("doctor_details")
    @Expose
    private List<DoctorModel> doctorDetails = null;

    private int itemType;

    public AppointmentModel(String appointmentDate, int itemType) {
        this.appointmentDate = appointmentDate;
        this.itemType = itemType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(String doctorFee) {
        this.doctorFee = doctorFee;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(String appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientDob() {
        return patientDob;
    }

    public void setPatientDob(String patientDob) {
        this.patientDob = patientDob;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public String getPatientRelation() {
        return patientRelation;
    }

    public void setPatientRelation(String patientRelation) {
        this.patientRelation = patientRelation;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getBookedByType() {
        return bookedByType;
    }

    public void setBookedByType(String bookedByType) {
        this.bookedByType = bookedByType;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public List<DoctorModel> getDoctorDetails() {
        return doctorDetails;
    }

    public void setDoctorDetails(List<DoctorModel> doctorDetails) {
        this.doctorDetails = doctorDetails;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getRatingStatus() {
        return ratingStatus;
    }

    public void setRatingStatus(String ratingStatus) {
        this.ratingStatus = ratingStatus;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReBooking() {
        return reBooking;
    }

    public void setReBooking(String reBooking) {
        this.reBooking = reBooking;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public AppointmentModel getClone() {
        try {
            return (AppointmentModel) clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
