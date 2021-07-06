package com.appsfeature.education.entity;

import com.appsfeature.education.doctor.DoctorModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SlotModel extends DoctorModel {

    @SerializedName("notification_status")
    @Expose
    private String notificationStatus;
    @SerializedName("booking_notification")
    @Expose
    private String bookingNotification;
    @SerializedName("appointment_date")
    @Expose
    private String appointmentDate;
    @SerializedName("appointment_day")
    @Expose
    private String appointmentDay;
    @SerializedName("appointment_slot")
    @Expose
    private List<AppointmentSlot> appointmentSlot = null;

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getBookingNotification() {
        return bookingNotification;
    }

    public void setBookingNotification(String bookingNotification) {
        this.bookingNotification = bookingNotification;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentDay() {
        return appointmentDay;
    }

    public void setAppointmentDay(String appointmentDay) {
        this.appointmentDay = appointmentDay;
    }

    public List<AppointmentSlot> getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(List<AppointmentSlot> appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

}
